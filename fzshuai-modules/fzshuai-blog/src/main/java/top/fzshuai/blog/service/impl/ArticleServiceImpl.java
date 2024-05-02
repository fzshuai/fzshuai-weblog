package top.fzshuai.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.fzshuai.blog.domain.*;
import top.fzshuai.blog.domain.bo.ArticleBo;
import top.fzshuai.blog.domain.dto.*;
import top.fzshuai.blog.domain.vo.*;
import top.fzshuai.blog.mapper.*;
import top.fzshuai.blog.service.IArticleService;
import top.fzshuai.blog.strategy.context.SearchStrategyContext;
import top.fzshuai.common.core.domain.PageQuery;
import top.fzshuai.common.core.page.TableDataInfo;
import top.fzshuai.common.exception.ServiceException;
import top.fzshuai.common.helper.LoginHelper;
import top.fzshuai.common.utils.BeanCopyUtils;
import top.fzshuai.common.utils.StringUtils;
import top.fzshuai.common.utils.blog.BlogPageUtils;
import top.fzshuai.common.utils.redis.RedisUtils;
import top.fzshuai.system.mapper.SysUserMapper;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static top.fzshuai.blog.enums.ArticleStatusEnum.DRAFT;
import static top.fzshuai.blog.enums.ArticleStatusEnum.PUBLIC;
import static top.fzshuai.common.constant.BlogConstant.*;

/**
 * 文章Service业务层处理
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements IArticleService {

    private final ArticleMapper baseMapper;
    private final ArticleTagMapper articleTagMapper;
    private final CategoryMapper categoryMapper;
    private final TagMapper tagMapper;
    private final CommentMapper commentMapper;
    private final SysUserMapper sysUserMapper;
    private final SearchStrategyContext searchStrategyContext;

    @Override
    public void likeArticle(Long articleId) {
        // 判断是否点赞
        String articleLikeKey = ARTICLE_USER_LIKE + LoginHelper.getLoginUser().getLoginId();
        if (RedisUtils.isExistsCacheSetObject(articleLikeKey, articleId)) {
            // 点过赞则删除文章id
            RedisUtils.delCacheSetObject(articleLikeKey, articleId);
            // 文章点赞量-1
            RedisUtils.decrCacheMapValue(ARTICLE_LIKE_COUNT, articleId.toString(), 1L);
        } else {
            // 未点赞则增加文章id
            RedisUtils.setCacheSet(articleLikeKey, articleId);
            // 文章点赞量+1
            RedisUtils.incrCacheMapValue(ARTICLE_LIKE_COUNT, articleId.toString(), 1L);
        }
    }

    @Override
    public PageResultVo<ArchiveVo> queryArticleArchiveList() {
        Page<Article> page = new Page<>(BlogPageUtils.getCurrent(), BlogPageUtils.getSize());
        // 获取分页数据
        Page<Article> articlePage = baseMapper.selectPage(page, new LambdaQueryWrapper<Article>()
            .select(Article::getArticleId, Article::getArticleTitle, Article::getCreateTime)
            .orderByDesc(Article::getCreateTime)
            .eq(Article::getIsDelete, FALSE)
            .eq(Article::getStatus, PUBLIC.getStatus()));
        List<ArchiveVo> archiveVoList = BeanCopyUtils.copyList(articlePage.getRecords(), ArchiveVo.class);
        return new PageResultVo<>(archiveVoList, (int) articlePage.getTotal());
    }

    @Override
    public List<ArticleHomeVo> queryArticleHomeList() {
        List<ArticleHomeDto> articleHomeDtos = baseMapper.selectArticleHomeList(BlogPageUtils.getLimitCurrent(), BlogPageUtils.getSize());
        return BeanCopyUtils.copyList(articleHomeDtos, ArticleHomeVo.class);
    }

    @Override
    public ArticleDetailVo queryArticleDetailById(Long articleId) {
        // 查询推荐文章
        CompletableFuture<List<ArticleRecommendDto>> recommendArticleList = CompletableFuture
            .supplyAsync(() -> baseMapper.selectRecommendArticleList(articleId));
        // 查询最新文章
        CompletableFuture<List<ArticleRecommendDto>> newestArticleList = CompletableFuture
            .supplyAsync(() -> {
                List<Article> articleList = baseMapper.selectList(new LambdaQueryWrapper<Article>()
                    .select(Article::getArticleId, Article::getArticleTitle, Article::getArticleCover, Article::getCreateTime)
                    .eq(Article::getIsDelete, FALSE)
                    .eq(Article::getStatus, PUBLIC.getStatus())
                    .orderByDesc(Article::getArticleId)
                    .last("limit 5"));
                return BeanCopyUtils.copyList(articleList, ArticleRecommendDto.class);
            });
        // 查询id对应文章
        ArticleDetailDto article = baseMapper.selectArticleDetailById(articleId);
        if (Objects.isNull(article)) {
            throw new ServiceException("文章不存在");
        }
        // 更新文章浏览量
        updateArticleViewsCount(articleId);
        // 查询上一篇下一篇文章
        Article lastArticle = baseMapper.selectOne(new LambdaQueryWrapper<Article>()
            .select(Article::getArticleId, Article::getArticleTitle, Article::getArticleCover)
            .eq(Article::getIsDelete, FALSE)
            .eq(Article::getStatus, PUBLIC.getStatus())
            .lt(Article::getArticleId, articleId)
            .orderByDesc(Article::getArticleId)
            .last("limit 1"));
        Article nextArticle = baseMapper.selectOne(new LambdaQueryWrapper<Article>()
            .select(Article::getArticleId, Article::getArticleTitle, Article::getArticleCover)
            .eq(Article::getIsDelete, FALSE)
            .eq(Article::getStatus, PUBLIC.getStatus())
            .gt(Article::getArticleId, articleId)
            .orderByAsc(Article::getArticleId)
            .last("limit 1"));
        article.setLastArticle(BeanCopyUtils.copy(lastArticle, ArticlePaginationDto.class));
        article.setNextArticle(BeanCopyUtils.copy(nextArticle, ArticlePaginationDto.class));
        // 封装点赞量
        Double score = RedisUtils.getCacheSortedSetScore(ARTICLE_VIEWS_COUNT, articleId);
        if (Objects.nonNull(score)) {
            article.setViewsCount(score.intValue());
        }
        // 封装点赞量
        article.setLikeCount(RedisUtils.getCacheMapValue(ARTICLE_LIKE_COUNT, articleId.toString()));
        // 封装文章信息
        try {
            article.setRecommendArticleList(recommendArticleList.get());
            article.setNewestArticleList(newestArticleList.get());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return BeanUtil.toBean(article, ArticleDetailVo.class);
    }

    /**
     * 更新文章浏览量
     *
     * @param articleId 文章id
     */
    public void updateArticleViewsCount(Long articleId) {
        // 浏览量+1
        RedisUtils.incrCacheSortedSetScore(ARTICLE_VIEWS_COUNT, articleId, 1D);
    }

    @Override
    public List<ArticleSearchVo> searchArticle(ConditionVo condition) {
        return searchStrategyContext.executeSearchStrategy(condition.getKeywords());
    }

    @Override
    public ArticlePreviewListVo queryArticlePreviewList(ConditionVo condition) {
        // 查询文章
        List<ArticlePreviewDto> articlePreviewDtoList = baseMapper.selectArticlePreviewList(BlogPageUtils.getLimitCurrent(), BlogPageUtils.getSize(), condition);
        // 搜索条件对应名(标签或分类名)
        String name;
        if (Objects.nonNull(condition.getCategoryId())) {
            name = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
                    .select(Category::getCategoryName)
                    .eq(Category::getCategoryId, condition.getCategoryId()))
                .getCategoryName();
        } else {
            name = tagMapper.selectVoOne(new LambdaQueryWrapper<Tag>()
                    .select(Tag::getTagName)
                    .eq(Tag::getTagId, condition.getTagId()))
                .getTagName();
        }
        return ArticlePreviewListVo.builder()
            .articlePreviewDtoList(articlePreviewDtoList)
            .name(name)
            .build();
    }

    /**
     * 保存文章分类
     *
     * @param articleBo 文章信息
     * @return 文章分类
     */
    private Category saveArticleCategory(ArticleBo articleBo) {
        // 判断分类是否存在
        Category category = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
            .eq(Category::getCategoryName, articleBo.getCategoryName()));
        if (Objects.isNull(category) && !articleBo.getStatus().equals(DRAFT.getStatus())) {
            category = Category.builder()
                .categoryName(articleBo.getCategoryName())
                .build();
            categoryMapper.insert(category);
        }
        return category;
    }

    /**
     * 保存文章标签
     *
     * @param articleId   文章Id
     * @param tagNameList 标签名称集合
     */
    private void insertArticleTag(Long articleId, List<String> tagNameList) {
        if (CollectionUtils.isNotEmpty(tagNameList)) {
            // 查询已存在的标签
            List<TagVo> existTagVoList = tagMapper.selectVoList(new LambdaQueryWrapper<Tag>()
                .in(Tag::getTagName, tagNameList));

            List<String> existTagNameList = existTagVoList.stream()
                .map(TagVo::getTagName)
                .collect(Collectors.toList());
            List<Long> existTagIdList = existTagVoList.stream()
                .map(TagVo::getTagId)
                .collect(Collectors.toList());
            // 对比新增不存在的标签
            tagNameList.removeAll(existTagNameList);
            if (CollectionUtils.isNotEmpty(tagNameList)) {
                List<Tag> tagList = tagNameList.stream()
                    .map(item -> Tag.builder()
                        .tagName(item)
                        .build())
                    .collect(Collectors.toList());
                tagMapper.insertBatch(tagList);
                List<Long> tagIdList = tagList.stream()
                    .map(Tag::getTagId)
                    .collect(Collectors.toList());
                existTagIdList.addAll(tagIdList);
            }
            // 提取标签id绑定文章
            List<ArticleTag> articleTagList = existTagIdList.stream()
                .map(item -> ArticleTag.builder()
                    .articleId(articleId)
                    .tagId(item)
                    .build())
                .collect(Collectors.toList());
            articleTagMapper.insertBatch(articleTagList);
        }
    }

    @Override
    public ArticleVo queryArticleById(Long articleId) {
        ArticleVo articleVO = baseMapper.selectVoById(articleId);
        articleVO.setCategoryName(categoryMapper.selectCategoryNameById(articleVO.getCategoryId()));
        articleVO.setTagNameList(articleTagMapper.selectArticleTagNameList(articleId));
        return articleVO;
    }

    @Override
    public TableDataInfo<ArticleVo> queryArticlePageList(ArticleBo articleBo, PageQuery pageQuery) {
        LambdaQueryWrapper<Article> lqw = buildQueryWrapper(articleBo);
        Page<ArticleVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        result.getRecords().forEach(articleVO -> {
            articleVO.setUserName(sysUserMapper.selectVoById(articleVO.getUserId()).getUserName());
            articleVO.setCategoryName(categoryMapper.selectCategoryNameById(articleVO.getCategoryId()));
            articleVO.setTagNameList(articleTagMapper.selectArticleTagNameList(articleVO.getArticleId()));
        });
        return TableDataInfo.build(result);
    }

    @Override
    public List<ArticleVo> queryArticleList(ArticleBo bo) {
        LambdaQueryWrapper<Article> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Article> buildQueryWrapper(ArticleBo bo) {
        LambdaQueryWrapper<Article> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, Article::getUserId, bo.getUserId());
        lqw.eq(bo.getCategoryId() != null, Article::getCategoryId, bo.getCategoryId());
        lqw.eq(StringUtils.isNotBlank(bo.getArticleCover()), Article::getArticleCover, bo.getArticleCover());
        lqw.eq(StringUtils.isNotBlank(bo.getArticleTitle()), Article::getArticleTitle, bo.getArticleTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getArticleContent()), Article::getArticleContent, bo.getArticleContent());
        lqw.eq(bo.getType() != null, Article::getType, bo.getType());
        lqw.eq(StringUtils.isNotBlank(bo.getOriginalUrl()), Article::getOriginalUrl, bo.getOriginalUrl());
        lqw.eq(bo.getIsTop() != null, Article::getIsTop, bo.getIsTop());
        lqw.eq(bo.getIsDelete() != null, Article::getIsDelete, bo.getIsDelete());
        lqw.eq(bo.getStatus() != null, Article::getStatus, bo.getStatus());
        lqw.orderByDesc(Article::getCreateTime);
        return lqw;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertByBo(ArticleBo bo) {
        bo.setUserId(LoginHelper.getUserId());
        // 没有传值默认分配
        if (StringUtils.isEmpty(bo.getCategoryName())) {
            bo.setCategoryName(DEFAULTCATORTAG);
        }
        // 设置文章分类
        // categoryOrTag.AddCateOrTag(bo);
        Category category = saveArticleCategory(bo);
        bo.setCategoryId(category.getCategoryId());
        Article add = BeanUtil.toBean(bo, Article.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setArticleId(add.getArticleId());
        }
        // 给文章默认标签
        if (bo.getTagNameList().isEmpty()) {
            bo.setTagNameList(new ArrayList<>(Arrays.asList(DEFAULTCATORTAG)));
        }

        // 新增文章与标签管理
        insertArticleTag(bo.getArticleId(), bo.getTagNameList());

        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateByBo(ArticleBo bo) {
        Category category = saveArticleCategory(bo);
        bo.setCategoryId(category.getCategoryId());
        articleTagMapper.delete(new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getArticleId, bo.getArticleId()));
        insertArticleTag(bo.getArticleId(), bo.getTagNameList());
        Article update = BeanUtil.toBean(bo, Article.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Article entity) {
        // TODO 做一些数据校验,如唯一约束
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> articleIds, Boolean isValid) {
        if (isValid) {
            // TODO 做一些业务上的校验,判断是否需要校验
        }
        // 删除文章与标签关联
        articleTagMapper.delete(new LambdaQueryWrapper<ArticleTag>().in(ArticleTag::getArticleId, articleIds));
        // 删除文章对应评论
        commentMapper.delete(new LambdaQueryWrapper<Comment>().in(Comment::getTopicId, articleIds));
        return baseMapper.deleteBatchIds(articleIds) > 0;
    }

}
