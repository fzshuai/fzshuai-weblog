package top.fzshuai.blog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 相册状态枚举
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Getter
@AllArgsConstructor
public enum PhotoAlbumStatusEnum {

    /**
     * 公开
     */
    PUBLIC(1, "公开"),

    /**
     * 私密
     */
    SECRET(2, "私密");

    /**
     * 状态
     */
    private final Integer status;

    /**
     * 描述
     */
    private final String desc;
}
