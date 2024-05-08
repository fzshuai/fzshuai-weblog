package top.fzshuai.common.core.domain.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author fzshuai
 * @date 2024/05/08 18:21
 * @since 1.0
 */
@Data
public class SocialLoginBody {

    /**
     * 第三方登录平台
     */
    @NotBlank(message = "{social.source.not.blank}")
    private String source;

    /**
     * 第三方登录 code
     */
    @NotBlank(message = "{social.code.not.blank}")
    private String code;

    /**
     * 第三方登录 state
     */
    @NotBlank(message = "{social.state.not.blank}")
    private String state;

}
