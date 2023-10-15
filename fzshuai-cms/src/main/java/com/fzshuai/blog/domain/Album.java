package com.fzshuai.blog.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fzshuai.common.core.domain.BaseEntity;

/**
 * 相册对象 blog_photo_album
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("blog_album")
public class Album extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "album_id")
    private Long albumId;

    /**
     * 相册名
     */
    private String albumName;

    /**
     * 相册描述
     */
    private String albumDesc;

    /**
     * 相册封面
     */
    private String albumCover;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 状态值 1公开 2私密
     */
    private Integer status;
}
