package top.fzshuai.common.core.domain.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import top.fzshuai.common.constant.UserConstants;

import javax.validation.constraints.NotBlank;

/**
 * 博客用户登录对象
 *
 * @author fzshuai
 * @date 2024/04/18 21:44
 * @since 1.0
 */
@Data
public class BlogLoginBody {

    /**
     * 用户名
     */
    @NotBlank(message = "{user.username.not.blank}")
    @Length(min = UserConstants.USERNAME_MIN_LENGTH, max = UserConstants.USERNAME_MAX_LENGTH, message = "{user.username.length.valid}")
    private String username;

    /**
     * 用户密码
     */
    @NotBlank(message = "{user.password.not.blank}")
    @Length(min = UserConstants.PASSWORD_MIN_LENGTH, max = UserConstants.PASSWORD_MAX_LENGTH, message = "{user.password.length.valid}")
    private String password;

}
