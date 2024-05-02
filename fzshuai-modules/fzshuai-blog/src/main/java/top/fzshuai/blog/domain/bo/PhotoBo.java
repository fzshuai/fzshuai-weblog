package top.fzshuai.blog.domain.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.fzshuai.common.core.domain.BaseEntity;
import top.fzshuai.common.core.validate.AddGroup;
import top.fzshuai.common.core.validate.EditGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 照片业务对象 blog_photo
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PhotoBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = {EditGroup.class})
    private Long photoId;

    /**
     * 相册id
     */
    @NotNull(message = "相册id不能为空", groups = {AddGroup.class, EditGroup.class})
    private Long albumId;

    /**
     * 照片名
     */
    @NotBlank(message = "照片名不能为空", groups = {AddGroup.class, EditGroup.class})
    private String photoName;

    /**
     * 照片描述
     */
    @NotBlank(message = "照片描述不能为空", groups = {AddGroup.class, EditGroup.class})
    private String photoDesc;

    /**
     * 照片地址
     */
    @NotBlank(message = "照片地址不能为空", groups = {AddGroup.class, EditGroup.class})
    private String photoSrc;

    /**
     * 是否删除
     */
    private Integer isDelete;

}
