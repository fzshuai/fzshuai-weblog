package top.fzshuai.system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 博客用户信息
 *
 * @author fzshuai
 * @date 2024/04/18 22:01
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogUserVo {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户邮件
     */
    private String email;

    /**
     * 用户ip
     */
    private String ipAddress;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户名
     */
    private String username;

}
