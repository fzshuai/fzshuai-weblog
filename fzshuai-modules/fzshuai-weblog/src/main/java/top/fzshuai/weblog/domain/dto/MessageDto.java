package top.fzshuai.weblog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 留言列表
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    /**
     * 主键id
     */
    private Long messageId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 留言内容
     */
    private String messageContent;

    /**
     * 弹幕速度
     */
    private Integer time;

}
