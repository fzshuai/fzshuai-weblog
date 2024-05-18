package top.fzshuai.blog.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import top.fzshuai.blog.domain.Article;
import top.fzshuai.blog.domain.bo.BlogPageBo;
import top.fzshuai.blog.domain.dto.ArticleRankDto;
import top.fzshuai.blog.domain.vo.BlogHomeInfoVo;
import top.fzshuai.blog.domain.vo.BlogInfoVo;
import top.fzshuai.blog.domain.vo.BlogPageVo;
import top.fzshuai.blog.domain.vo.WebsiteConfigVo;
import top.fzshuai.blog.mapper.ArticleMapper;
import top.fzshuai.blog.mapper.CategoryMapper;
import top.fzshuai.blog.mapper.TagMapper;
import top.fzshuai.blog.service.IBlogInfoService;
import top.fzshuai.blog.service.IBlogPageService;
import top.fzshuai.blog.service.IWebsiteConfigService;
import top.fzshuai.common.utils.ServletUtils;
import top.fzshuai.common.utils.StringUtils;
import top.fzshuai.common.utils.ip.AddressUtils;
import top.fzshuai.common.utils.redis.RedisUtils;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static top.fzshuai.blog.enums.ArticleStatusEnum.PUBLIC;
import static top.fzshuai.common.constant.BlogConstant.*;

/**
 * 博客信息服务
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@RequiredArgsConstructor
@Service
public class BlogInfoServiceImpl implements IBlogInfoService {

    private final ArticleMapper articleMapper;
    private final CategoryMapper categoryMapper;
    private final TagMapper tagMapper;
    private final HttpServletRequest request;
    private final IBlogPageService blogPageService;
    private final IWebsiteConfigService websiteConfigService;

    @Override
    public BlogHomeInfoVo queryBlogHomeInfo() {
        // 查询文章数量
        Long articleCount = articleMapper.selectCount(new LambdaQueryWrapper<Article>()
            .eq(Article::getStatus, PUBLIC.getStatus())
            .eq(Article::getIsDelete, FALSE));
        // 查询分类数量
        Long categoryCount = categoryMapper.selectCount(null);
        // 查询标签数量
        Long tagCount = tagMapper.selectCount(null);
        // 查询访问量
        Object count = RedisUtils.getCacheObject(BLOG_VIEWS_COUNT);
        String viewsCount = Optional.ofNullable(count).orElse(0).toString();
        // 查询网站配置
        WebsiteConfigVo websiteConfig = websiteConfigService.queryWebsiteConfig();
        // 查询页面图片
        List<BlogPageVo> blogPageVoList = blogPageService.queryPageList(new BlogPageBo());
        // 封装数据
        return BlogHomeInfoVo.builder()
            .articleCount(articleCount.intValue())
            .categoryCount(categoryCount.intValue())
            .tagCount(tagCount.intValue())
            .viewsCount(viewsCount)
            .websiteConfig(websiteConfig)
            .blogPageList(blogPageVoList)
            .build();
    }

    @Override
    public String queryAbout() {
        Object value = RedisUtils.getCacheObject(ABOUT);
        return Objects.nonNull(value) ? value.toString() : "";
    }

    @Override
    public void updateAbout(BlogInfoVo blogInfoVo) {
        RedisUtils.setCacheObject(ABOUT, blogInfoVo.getAboutContent());
    }

    @Override
    public void reportVisitor() {
        // 获取ip
        String ipAddress = ServletUtils.getClientIP(request);
        // 获取访问设备
        UserAgent userAgent = AddressUtils.getUserAgent(request);
        Browser browser = userAgent.getBrowser();
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        // 生成唯一用户标识
        String uuid = ipAddress + browser.getName() + operatingSystem.getName();
        String md5 = DigestUtils.md5DigestAsHex(uuid.getBytes());
        // 判断是否访问
        if (!RedisUtils.getCacheSet(UNIQUE_VISITOR).contains(md5)) {
            // 统计游客地域分布
            String ipSource = AddressUtils.getRealAddressByIP(ipAddress);
            if (StringUtils.isNotBlank(ipSource)) {
                ipSource = ipSource.substring(0, 2)
                    .replaceAll(PROVINCE, "")
                    .replaceAll(CITY, "");
                RedisUtils.incrCacheMapValue(VISITOR_AREA, ipSource, 1L);
            } else {
                RedisUtils.incrCacheMapValue(VISITOR_AREA, UNKNOWN, 1L);
            }
            // 访问量+1
            RedisUtils.incrAtomicValue(BLOG_VIEWS_COUNT);
            // 保存唯一标识
            RedisUtils.setCacheSet(UNIQUE_VISITOR, md5);
        }
    }

    /**
     * 查询文章排行
     *
     * @param articleMap 文章信息
     * @return 文章排行
     */
    private List<ArticleRankDto> listArticleRank(Map<Object, Double> articleMap) {
        // 提取文章id
        List<Integer> articleIdList = new ArrayList<>();
        articleMap.forEach((key, value) -> articleIdList.add((Integer) key));
        // 查询文章信息
        return articleMapper.selectList(new LambdaQueryWrapper<Article>()
                .select(Article::getArticleId, Article::getArticleTitle)
                .in(Article::getArticleId, articleIdList))
            .stream().map(article -> ArticleRankDto.builder()
                .articleTitle(article.getArticleTitle())
                .viewsCount(articleMap.get(article.getArticleId()).intValue())
                .build())
            .sorted(Comparator.comparingInt(ArticleRankDto::getViewsCount).reversed())
            .collect(Collectors.toList());
    }

}
