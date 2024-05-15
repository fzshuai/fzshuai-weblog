package top.fzshuai.blog.domain.vo;

import top.fzshuai.blog.domain.dto.ArticlePaginationDto;
import top.fzshuai.blog.domain.dto.ArticleRecommendDto;
import top.fzshuai.blog.domain.dto.TagDto;
import lombok.Data;
import top.fzshuai.common.annotation.Translation;
import top.fzshuai.common.constant.TransConstant;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 文章
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
public class ArticleDetailVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long articleId;

    /**
     * 文章缩略图
     */
    private String articleCover;

    /**
     * 文章缩略图url
     */
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "articleCover")
    private String articleCoverUrl;

    /**
     * 标题
     */
    private String articleTitle;

    /**
     * 内容
     */
    private String articleContent;

    /**
     * 点赞量
     */
    private Integer likeCount;

    /**
     * 浏览量
     */
    private Integer viewsCount;

    /**
     * 文章类型
     */
    private Integer type;

    /**
     * 原文链接
     */
    private String originalUrl;

    /**
     * 发表时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 文章分类id
     */
    private Long categoryId;

    /**
     * 文章分类名
     */
    private String categoryName;

    /**
     * 文章标签
     */
    private List<TagDto> tagDtoList;

    /**
     * 上一篇文章
     */
    private ArticlePaginationDto lastArticle;

    /**
     * 下一篇文章
     */
    private ArticlePaginationDto nextArticle;

    /**
     * 推荐文章列表
     */
    private List<ArticleRecommendDto> recommendArticleList;

    /**
     * 最新文章列表
     */
    private List<ArticleRecommendDto> newestArticleList;

}
