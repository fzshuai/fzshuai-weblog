package top.fzshuai.blog.domain.bo;

import top.fzshuai.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;

import java.util.List;

import top.fzshuai.common.core.domain.BaseEntity;

/**
 * 文章业务对象 blog_article
 *
 * @author fzshuai
 * @date 2023-05-03
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ArticleBO extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = {EditGroup.class})
    private Long articleId;

    /**
     * 作者id
     */
    // @NotNull(message = "作者id不能为空", groups = {AddGroup.class, EditGroup.class})
    private Long userId;

    /**
     * 文章分类
     */
    // @NotNull(message = "文章分类不能为空", groups = {AddGroup.class, EditGroup.class})
    private Long categoryId;

    /**
     * 文章分类名称
     */
    private String categoryName;

    /**
     * 文章标签
     */
    private List<String> tagNameList;

    /**
     * 文章缩略图
     */
    // @NotBlank(message = "文章缩略图不能为空", groups = {AddGroup.class, EditGroup.class})
    private String articleCover;

    /**
     * 标题
     */
    // @NotBlank(message = "标题不能为空", groups = {AddGroup.class, EditGroup.class})
    private String articleTitle;

    /**
     * 内容
     */
    // @NotBlank(message = "内容不能为空", groups = {AddGroup.class, EditGroup.class})
    private String articleContent;

    /**
     * 文章类型 1原创 2转载 3翻译
     */
    // @NotNull(message = "文章类型 1原创 2转载 3翻译不能为空", groups = {AddGroup.class, EditGroup.class})
    private Integer type;

    /**
     * 原文链接
     */
    // @NotBlank(message = "原文链接不能为空", groups = {AddGroup.class, EditGroup.class})
    private String originalUrl;

    /**
     * 是否置顶 0否 1是
     */
    // @NotNull(message = "是否置顶 0否 1是不能为空", groups = {AddGroup.class, EditGroup.class})
    private Integer isTop;

    /**
     * 是否删除  0否 1是
     */
    // @NotNull(message = "是否删除  0否 1是不能为空", groups = {AddGroup.class, EditGroup.class})
    private Integer isDelete;

    /**
     * 状态值 1公开 2私密 3评论可见
     */
    // @NotNull(message = "状态值 1公开 2私密 3评论可见不能为空", groups = {AddGroup.class, EditGroup.class})
    private Integer status;
}
