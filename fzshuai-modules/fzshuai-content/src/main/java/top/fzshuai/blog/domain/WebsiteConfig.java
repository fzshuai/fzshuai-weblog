package top.fzshuai.blog.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import top.fzshuai.common.core.domain.BaseEntity;

/**
 * 网站配置对象 blog_website_config
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("blog_website_config")
public class WebsiteConfig extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "website_config_id")
    private Long websiteConfigId;
    /**
     * 配置信息
     */
    private String config;

}
