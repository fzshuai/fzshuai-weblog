package top.fzshuai.blog.domain.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.fzshuai.common.core.domain.BaseEntity;
import top.fzshuai.common.core.validate.AddGroup;
import top.fzshuai.common.core.validate.EditGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 页面业务对象 blog_page
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BlogPageBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = {EditGroup.class})
    private Long pageId;

    /**
     * 页面名
     */
    @NotBlank(message = "页面名不能为空", groups = {AddGroup.class, EditGroup.class})
    private String pageName;

    /**
     * 页面标签
     */
    @NotBlank(message = "页面标签不能为空", groups = {AddGroup.class, EditGroup.class})
    private String pageLabel;

    /**
     * 页面封面
     */
    @NotBlank(message = "页面封面不能为空", groups = { AddGroup.class, EditGroup.class })
    private String pageCover;

}
