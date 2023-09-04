package com.fzshuai.blog.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.fzshuai.common.core.domain.BaseEntity;

/**
 * 文章标签对象 blog_tag
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@TableName("blog_tag")
public class Tag extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "tag_id")
    private Long tagId;
    /**
     * 标签名
     */
    private String tagName;

}
