package top.fzshuai.weblog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 回复数量
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyCountDto {

    /**
     * 评论id
     */
    private Long commentId;

    /**
     * 回复数量
     */
    private Integer replyCount;

}
