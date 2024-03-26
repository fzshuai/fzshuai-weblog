package top.fzshuai.blog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 撤回消息dto
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecallMessageDto {

    /**
     * 消息id
     */
    private Long messageId;

    /**
     * 是否为语音
     */
    private Boolean isVoice;

}
