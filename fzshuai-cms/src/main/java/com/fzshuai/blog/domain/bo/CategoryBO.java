package com.fzshuai.blog.domain.bo;

import com.fzshuai.common.core.validate.AddGroup;
import com.fzshuai.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import com.fzshuai.common.core.domain.BaseEntity;

/**
 * 文章分类业务对象 blog_category
 *
 * @author fzshuai
 * @date 2023-05-03
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryBO extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long categoryId;

    /**
     * 分类名
     */
    @NotBlank(message = "分类名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String categoryName;


}
