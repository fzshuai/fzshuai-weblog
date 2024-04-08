package top.fzshuai.blog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 评论数量dto
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentCountDto {

    /**
     * id
     */
    private Long commentId;

    /**
     * 评论数量
     */
    private Integer commentCount;

}
