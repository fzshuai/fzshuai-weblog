package com.fzshuai.blog.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.fzshuai.common.core.domain.BaseEntity;

/**
 * 说说对象 blog_talk
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("blog_talk")
public class Talk extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "talk_id")
    private Long talkId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 说说内容
     */
    private String content;
    /**
     * 图片
     */
    private String images;
    /**
     * 是否置顶
     */
    private Integer isTop;
    /**
     * 状态 1.公开 2.私密
     */
    private Integer status;

}
