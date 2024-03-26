package top.fzshuai.blog.domain.bo;

import top.fzshuai.common.core.validate.AddGroup;
import top.fzshuai.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;

import top.fzshuai.common.core.domain.BaseEntity;

/**
 * 文章标签业务对象 blog_tag
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TagBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = {EditGroup.class})
    private Long tagId;

    /**
     * 标签名
     */
    @NotBlank(message = "标签名不能为空", groups = {AddGroup.class, EditGroup.class})
    private String tagName;

}
