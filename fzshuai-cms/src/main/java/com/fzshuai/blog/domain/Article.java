package com.fzshuai.blog.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.fzshuai.common.core.domain.BaseEntity;

/**
 * 文章对象 blog_article
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("blog_article")
public class Article extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "article_id")
    private Long articleId;

    /**
     * 作者id
     */
    private Long userId;

    /**
     * 文章分类
     */
    private Long categoryId;

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
     * 文章类型 1原创 2转载 3翻译
     */
    private Integer type;

    /**
     * 原文链接
     */
    private String originalUrl;

    /**
     * 是否置顶 0否 1是
     */
    private Integer isTop;

    /**
     * 是否删除  0否 1是
     */
    private Integer isDelete;

    /**
     * 状态值 1公开 2私密 3评论可见
     */
    private Integer status;

    /**
     * 浏览量
     */
    private Integer viewCount;
}
