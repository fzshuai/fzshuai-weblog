package com.fzshuai.blog.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fzshuai.common.annotation.Translation;
import com.fzshuai.common.constant.TransConstant;
import lombok.Data;

import java.util.List;


/**
 * 文章视图对象 blog_article
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@ExcelIgnoreUnannotated
public class ArticleVo {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long articleId;

    /**
     * 作者id
     */
    @ExcelProperty(value = "作者id")
    private Long userId;

    /**
     * 作者名字
     */
    private String userName;

    /**
     * 文章分类
     */
    @ExcelProperty(value = "文章分类")
    private Long categoryId;

    /**
     * 文章分类名称
     */
    @TableField(exist = false)
    private String categoryName;

    /**
     * 文章标签
     */
    @TableField(exist = false)
    private List<String> tagNameList;

    /**
     * 文章缩略图
     */
    @ExcelProperty(value = "文章缩略图")
    private String articleCover;

    /**
     * 文章缩略图url
     */
    @ExcelProperty(value = "文章缩略图地址")
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "articleCover")
    private String articleCoverUrl;

    /**
     * 标题
     */
    @ExcelProperty(value = "标题")
    private String articleTitle;

    /**
     * 内容
     */
    @ExcelProperty(value = "内容")
    private String articleContent;

    /**
     * 文章类型 1原创 2转载 3翻译
     */
    @ExcelProperty(value = "文章类型 1原创 2转载 3翻译")
    private Integer type;

    /**
     * 原文链接
     */
    @ExcelProperty(value = "原文链接")
    private String originalUrl;

    /**
     * 是否置顶 0否 1是
     */
    @ExcelProperty(value = "是否置顶 0否 1是")
    private Integer isTop;

    /**
     * 是否删除  0否 1是
     */
    @ExcelProperty(value = "是否删除  0否 1是")
    private Integer isDelete;

    /**
     * 状态值 1公开 2私密 3评论可见
     */
    @ExcelProperty(value = "状态值 1公开 2私密 3评论可见")
    private Integer status;
}
