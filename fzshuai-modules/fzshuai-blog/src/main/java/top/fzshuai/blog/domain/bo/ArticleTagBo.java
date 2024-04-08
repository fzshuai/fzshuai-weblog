package top.fzshuai.blog.domain.bo;

import top.fzshuai.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;

import top.fzshuai.common.core.domain.BaseEntity;

/**
 * 文章和文章标签关联业务对象 blog_article_tag
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ArticleTagBo extends BaseEntity {

    /**
     * 文章id
     */
    @NotNull(message = "文章id不能为空", groups = {EditGroup.class})
    private Long articleId;

    /**
     * 标签id
     */
    @NotNull(message = "标签id不能为空", groups = {EditGroup.class})
    private Long tagId;

}
