package top.fzshuai.blog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 照片dto
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoDTO {

    /**
     * 相册id
     */
    @NotNull(message = "相册id不能为空")
    private Long albumId;

    /**
     * 照片url列表
     */
    private List<String> photoUrlList;

    /**
     * 照片名称列表
     */
    private List<String> photoNameList;
}
