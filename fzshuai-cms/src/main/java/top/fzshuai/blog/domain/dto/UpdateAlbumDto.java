package top.fzshuai.blog.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 更新相册封装类
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
public class UpdateAlbumDto {

    /**
     * 选中的相片id
     */
    private Long[] ids;

    /**
     * 更新后的相册id
     */
    @NotNull(message = "相册id不能为空")
    private Long albumId;

}
