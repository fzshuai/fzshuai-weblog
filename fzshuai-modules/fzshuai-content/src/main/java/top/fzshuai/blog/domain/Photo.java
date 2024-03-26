package top.fzshuai.blog.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import top.fzshuai.common.core.domain.BaseEntity;

/**
 * 照片对象 blog_photo
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("blog_photo")
public class Photo extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "photo_id")
    private Long photoId;
    /**
     * 相册id
     */
    private Long albumId;
    /**
     * 照片名
     */
    private String photoName;
    /**
     * 照片描述
     */
    private String photoDesc;
    /**
     * 照片地址
     */
    private String photoSrc;
    /**
     * 是否删除
     */
    private Integer isDelete;

}
