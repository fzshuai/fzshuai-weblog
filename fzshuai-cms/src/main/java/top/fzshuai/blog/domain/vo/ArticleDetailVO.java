package top.fzshuai.blog.domain.vo;

import top.fzshuai.blog.domain.dto.ArticlePaginationDTO;
import top.fzshuai.blog.domain.dto.ArticleRecommendDTO;
import top.fzshuai.blog.domain.dto.TagDTO;
import lombok.Data;

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
public class ArticleDetailVO implements Serializable {

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
    private List<TagDTO> tagDTOList;

    /**
     * 上一篇文章
     */
    private ArticlePaginationDTO lastArticle;

    /**
     * 下一篇文章
     */
    private ArticlePaginationDTO nextArticle;

    /**
     * 推荐文章列表
     */
    private List<ArticleRecommendDTO> recommendArticleList;

    /**
     * 最新文章列表
     */
    private List<ArticleRecommendDTO> newestArticleList;

}
