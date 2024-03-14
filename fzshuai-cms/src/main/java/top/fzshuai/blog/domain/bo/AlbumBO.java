package top.fzshuai.blog.domain.bo;

import top.fzshuai.common.core.validate.AddGroup;
import top.fzshuai.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import top.fzshuai.common.core.domain.BaseEntity;

/**
 * 相册业务对象 blog_photo_album
 *
 * @author fzshuai
 * @date 2023-05-03
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class AlbumBO extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long albumId;

    /**
     * 相册名
     */
    @NotBlank(message = "相册名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String albumName;

    /**
     * 相册描述
     */
    @NotBlank(message = "相册描述不能为空", groups = { AddGroup.class, EditGroup.class })
    private String albumDesc;

    /**
     * 相册封面
     */
    @NotBlank(message = "相册封面不能为空", groups = { AddGroup.class, EditGroup.class })
    private String albumCover;

    /**
     * 是否删除
     */
    @NotNull(message = "是否删除不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer isDelete;

    /**
     * 状态值 1公开 2私密
     */
    @NotNull(message = "状态值 1公开 2私密不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer status;


}
