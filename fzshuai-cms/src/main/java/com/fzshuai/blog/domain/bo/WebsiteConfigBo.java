package com.fzshuai.blog.domain.bo;

import com.fzshuai.common.core.validate.AddGroup;
import com.fzshuai.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.fzshuai.common.core.domain.BaseEntity;

/**
 * 网站配置业务对象 blog_website_config
 *
 * @author fzshuai
 * @date 2023-05-03
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class WebsiteConfigBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long websiteConfigId;

    /**
     * 配置信息
     */
    @NotBlank(message = "配置信息不能为空", groups = { AddGroup.class, EditGroup.class })
    private String config;
}
