package top.fzshuai.common.core.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 登录用户身份权限
 *
 * @author fzshuai
 * @date 2024/05/04 13:00
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class BlogLoginUser implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private String ipaddr;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户名
     */
    private String username;

}
