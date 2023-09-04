package com.fzshuai.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzshuai.blog.domain.*;
import com.fzshuai.blog.domain.bo.ArticleBo;
import com.fzshuai.blog.domain.dto.*;
import com.fzshuai.blog.domain.vo.ArticleVo;
import com.fzshuai.blog.domain.vo.ConditionVo;
import com.fzshuai.blog.domain.vo.PageResult;
import com.fzshuai.blog.domain.vo.TagVo;
import com.fzshuai.blog.mapper.*;
import com.fzshuai.blog.service.IArticleService;
import com.fzshuai.blog.service.IArticleTagService;
import com.fzshuai.blog.strategy.context.SearchStrategyContext;
import com.fzshuai.common.core.domain.PageQuery;
import com.fzshuai.common.core.page.TableDataInfo;
import com.fzshuai.common.exception.ServiceException;
import com.fzshuai.common.helper.LoginHelper;
import com.fzshuai.common.utils.BeanCopyUtils;
import com.fzshuai.common.utils.StringUtils;
import com.fzshuai.common.utils.blog.BlogPageUtils;
import com.fzshuai.common.utils.redis.RedisUtils;
import com.fzshuai.system.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.fzshuai.common.constant.BlogConstant.*;
import static com.fzshuai.common.constant.RedisConstant.*;
import static com.fzshuai.blog.enums.ArticleStatusEnum.DRAFT;
import static com.fzshuai.blog.enums.ArticleStatusEnum.PUBLIC;

/**
 * 文章Service业务层处理
 *
 * @author fzshuai
 * @date 2023-05-03
 */
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
    private final HttpSession session;
    private final IArticleTagService articleTagService;

    @Override
    public void saveArticleLike(Long articleId) {
        // 判断是否点赞
        String articleLikeKey = ARTICLE_USER_LIKE + LoginHelper.getLoginUser().getLoginId();
        if (RedisUtils.isExistsSetObject(articleLikeKey, articleId)) {
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
    public PageResult<ArchiveDTO> listArchives() {
        Page<Article> page = new Page<>(BlogPageUtils.getCurrent(), BlogPageUtils.getSize());
        // 获取分页数据
        Page<Article> articlePage = baseMapper.selectPage(page, new LambdaQueryWrapper<Article>()
            .select(Article::getArticleId, Article::getArticleTitle, Article::getCreateTime)
            .orderByDesc(Article::getCreateTime)
            .eq(Article::getIsDelete, FALSE)
            .eq(Article::getStatus, PUBLIC.getStatus()));
        List<ArchiveDTO> archiveDTOList = BeanCopyUtils.copyList(articlePage.getRecords(), ArchiveDTO.class);
        return new PageResult<>(archiveDTOList, (int) articlePage.getTotal());
    }

    @Override
    public List<ArticleHomeDTO> listArticles() {
        return baseMapper.listArticles(BlogPageUtils.getLimitCurrent(), BlogPageUtils.getSize());
    }

    @Override
    public ArticleDTO getArticleById(Long articleId) {
        // 查询推荐文章
        CompletableFuture<List<ArticleRecommendDTO>> recommendArticleList = CompletableFuture
            .supplyAsync(() -> baseMapper.listRecommendArticles(articleId));
        // 查询最新文章
        CompletableFuture<List<ArticleRecommendDTO>> newestArticleList = CompletableFuture
            .supplyAsync(() -> {
                List<Article> articleList = baseMapper.selectList(new LambdaQueryWrapper<Article>()
                    .select(Article::getArticleId, Article::getArticleTitle, Article::getArticleCover, Article::getCreateTime)
                    .eq(Article::getIsDelete, FALSE)
                    .eq(Article::getStatus, PUBLIC.getStatus())
                    .orderByDesc(Article::getArticleId)
                    .last("limit 5"));
                return BeanCopyUtils.copyList(articleList, ArticleRecommendDTO.class);
            });
        // 查询id对应文章
        ArticleDTO article = baseMapper.getArticleById(articleId);
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
        article.setLastArticle(BeanCopyUtils.copy(lastArticle, ArticlePaginationDTO.class));
        article.setNextArticle(BeanCopyUtils.copy(nextArticle, ArticlePaginationDTO.class));
        // 封装点赞量和浏览量
        Double score = RedisUtils.getCacheSortedSetScore(ARTICLE_VIEWS_COUNT, articleId);
        if (Objects.nonNull(score)) {
            article.setViewsCount(score.intValue());
        }
        article.setLikeCount(RedisUtils.getCacheMapValue(ARTICLE_LIKE_COUNT, articleId.toString()));
        // 封装文章信息
        try {
            article.setRecommendArticleList(recommendArticleList.get());
            article.setNewestArticleList(newestArticleList.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return article;
    }

    /**
     * 更新文章浏览量
     *
     * @param articleId 文章id
     */
    public void updateArticleViewsCount(Long articleId) {
        // 判断是否第一次访问，增加浏览量
        Set<Long> articleSet = (Set<Long>) Optional.ofNullable(session.getAttribute(ARTICLE_SET)).orElse(new HashSet<>());
        if (!articleSet.contains(articleId)) {
            articleSet.add(articleId);
            session.setAttribute(ARTICLE_SET, articleSet);
            // 浏览量+1
            RedisUtils.incrCacheSortedSetScore(ARTICLE_VIEWS_COUNT, articleId, 1D);
        }
    }

    @Override
    public List<ArticleSearchDTO> listArticlesBySearch(ConditionVo condition) {
        return searchStrategyContext.executeSearchStrategy(condition.getKeywords());
    }

    @Override
    public ArticlePreviewListDTO listArticlesByCondition(ConditionVo condition) {
        // 查询文章
        List<ArticlePreviewDTO> articlePreviewDTOList = baseMapper.listArticlesByCondition(BlogPageUtils.getLimitCurrent(), BlogPageUtils.getSize(), condition);
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
        return ArticlePreviewListDTO.builder()
            .articlePreviewDTOList(articlePreviewDTOList)
            .name(name)
            .build();
    }

    /**
     * 保存文章分类
     *
     * @param articleBo 文章信息
     * @return {@link Category} 文章分类
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
        // 编辑文章则删除文章所有标签
        // if (Objects.nonNull(articleVO.getArticleId())) {
        //     articleTagMapper.delete(new LambdaQueryWrapper<ArticleTag>()
        //         .eq(ArticleTag::getArticleId, articleVO.getArticleId()));
        // }
        // 添加文章标签
        // List<String> tagNameList = articleVO.getTagNameList();
        if (CollectionUtils.isNotEmpty(tagNameList)) {
            // 查询已存在的标签
            List<TagVo> existTagVoList = tagMapper.selectVoList(new LambdaQueryWrapper<Tag>()
                .in(Tag::getTagName, tagNameList));

            // List<Tag> existTagList = tagService.list(new LambdaQueryWrapper<Tag>()
            //     .in(Tag::getTagName, tagNameList));
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
            articleTagService.saveBatch(articleTagList);
        }
    }

    @Override
    public ArticleVo queryById(Long articleId) {
        return baseMapper.selectVoById(articleId);
    }

    @Override
    public TableDataInfo<ArticleVo> queryPageList(ArticleBo articleBo, PageQuery pageQuery) {
        LambdaQueryWrapper<Article> lqw = buildQueryWrapper(articleBo);
        Page<ArticleVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        result.getRecords().forEach(articleVo -> {
            articleVo.setUserName(sysUserMapper.selectVoById(articleVo.getUserId()).getUserName());
            articleVo.setCategoryName(categoryMapper.selectNameById(articleVo.getCategoryId()));
            articleVo.setTagNameList(tagMapper.selectTagNamesByArticleId(articleVo.getArticleId()));
        });
        return TableDataInfo.build(result);
    }

    @Override
    public List<ArticleVo> queryList(ArticleBo bo) {
        LambdaQueryWrapper<Article> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Article> buildQueryWrapper(ArticleBo bo) {
        Map<String, Object> params = bo.getParams();
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
            bo.setTagNameList(Arrays.asList(DEFAULTCATORTAG));
        }
        // for (String s : bo.getTagNameList()) {
        //     // 如果是新增的标签则先将标签新增
        //     if (baseMapper.queryTagIdByName(s) == null) {
        //         Tag tag = Tag.builder().tagName(s).build();
        //         tagMapper.insert(tag);
        //     }
        //     //判断文章是否已经有这个标签
        //     List<String> tags = baseMapper.queryArticleTags(bo.getArticleId());
        //     if (tags.contains(s)) {
        //         continue;
        //     }
        //     baseMapper.insertArticleTag(bo.getArticleId(), baseMapper.queryTagIdByName(s));
        // }

        // 新增文章与标签管理
        insertArticleTag(bo.getArticleId(), bo.getTagNameList());

        return flag;
    }

    // public void insertArticleTag(Long articleId, List<Long> tagIds) {
    //     if (ArrayUtil.isNotEmpty(tagIds)) {
    //         // 新增用户与角色管理
    //         List<ArticleTag> list = StreamUtils.toList(Arrays.asList(tagIds), tagId -> {
    //             ArticleTag ur = new ArticleTag();
    //             ur.setArticleId(articleId);
    //             ur.setTagId(tagId);
    //             return ur;
    //         });
    //         articleTagMapper.insertBatch(list);
    //     }
    // }

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
