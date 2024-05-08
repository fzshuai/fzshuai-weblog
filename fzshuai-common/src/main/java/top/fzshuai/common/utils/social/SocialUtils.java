package top.fzshuai.common.utils.social;

import cn.hutool.core.util.ObjectUtil;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.exception.AuthException;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.*;
import top.fzshuai.common.config.properties.SocialLoginConfigProperties;
import top.fzshuai.common.config.properties.SocialProperties;

/**
 * @author fzshuai
 * @date 2024/04/11 20:47
 * @since 1.0
 */
public class SocialUtils {

    public static AuthResponse<AuthUser> loginAuth(String source, String code, String state, SocialProperties socialProperties) throws AuthException {
        AuthRequest authRequest = getAuthRequest(source, socialProperties);
        AuthCallback callback = new AuthCallback();
        callback.setCode(code);
        callback.setState(state);
        return authRequest.login(callback);
    }

    public static AuthRequest getAuthRequest(String source, SocialProperties socialProperties) throws AuthException {
        SocialLoginConfigProperties obj = socialProperties.getType().get(source);
        if (ObjectUtil.isNull(obj)) {
            throw new AuthException("不支持的第三方登录类型");
        }
        String clientId = obj.getClientId();
        String clientSecret = obj.getClientSecret();
        String redirectUri = obj.getRedirectUri();
        AuthRequest authRequest = null;
        switch (source.toLowerCase()) {
            case "dingtalk":
                authRequest = new AuthDingTalkRequest(AuthConfig.builder().
                    clientId(clientId).
                    clientSecret(clientSecret)
                    .redirectUri(redirectUri).
                    build());
                break;
            case "baidu":
                authRequest = new AuthBaiduRequest(AuthConfig.builder().
                    clientId(clientId).
                    clientSecret(clientSecret)
                    .redirectUri(redirectUri).
                    build());
                break;
            case "github":
                authRequest = new AuthGithubRequest(AuthConfig.builder().clientId(clientId).clientSecret(clientSecret)
                    .redirectUri(redirectUri).build());
                break;
            case "gitee":
                authRequest = new AuthGiteeRequest(AuthConfig.builder().clientId(clientId).clientSecret(clientSecret)
                    .redirectUri(redirectUri).build());
                break;
            case "weibo":
                authRequest = new AuthWeiboRequest(AuthConfig.builder().clientId(clientId).clientSecret(clientSecret)
                    .redirectUri(redirectUri).build());
                break;
            case "coding":
                authRequest = new AuthCodingRequest(AuthConfig.builder().clientId(clientId).clientSecret(clientSecret)
                    .redirectUri(redirectUri).build());
                break;
            case "oschina":
                authRequest = new AuthOschinaRequest(AuthConfig.builder().clientId(clientId).clientSecret(clientSecret)
                    .redirectUri(redirectUri).build());
                break;
            case "alipay":
                authRequest = new AuthAlipayRequest(AuthConfig.builder().clientId(clientId).clientSecret(clientSecret)
                    .alipayPublicKey("").redirectUri(redirectUri).build());
                break;
            case "qq":
                authRequest = new AuthQqRequest(AuthConfig.builder().clientId(clientId).clientSecret(clientSecret)
                    .redirectUri(redirectUri).build());
                break;
            case "wechat_open":
                authRequest = new AuthWeChatOpenRequest(AuthConfig.builder().clientId(clientId)
                    .clientSecret(clientSecret).redirectUri(redirectUri).build());
                break;
            case "csdn":
                authRequest = new AuthCsdnRequest(AuthConfig.builder().clientId(clientId).clientSecret(clientSecret)
                    .redirectUri(redirectUri).build());
                break;
            case "taobao":
                authRequest = new AuthTaobaoRequest(AuthConfig.builder().clientId(clientId).clientSecret(clientSecret)
                    .redirectUri(redirectUri).build());
                break;
            case "douyin":
                authRequest = new AuthDouyinRequest(AuthConfig.builder().clientId(clientId).clientSecret(clientSecret)
                    .redirectUri(redirectUri).build());
                break;
            case "linkedin":
                authRequest = new AuthLinkedinRequest(AuthConfig.builder().clientId(clientId).clientSecret(clientSecret)
                    .redirectUri(redirectUri).build());
                break;
            case "microsoft":
                authRequest = new AuthMicrosoftRequest(AuthConfig.builder().clientId(clientId)
                    .clientSecret(clientSecret).redirectUri(redirectUri).build());
                break;
            case "mi":
                authRequest = new AuthMiRequest(AuthConfig.builder().clientId(clientId).clientSecret(clientSecret)
                    .redirectUri(redirectUri).build());
                break;
            case "toutiao":
                authRequest = new AuthToutiaoRequest(AuthConfig.builder().clientId(clientId).clientSecret(clientSecret)
                    .redirectUri(redirectUri).build());
                break;
            case "teambition":
                authRequest = new AuthTeambitionRequest(AuthConfig.builder().clientId(clientId)
                    .clientSecret(clientSecret).redirectUri(redirectUri).build());
                break;
            case "pinterest":
                authRequest = new AuthPinterestRequest(AuthConfig.builder().clientId(clientId)
                    .clientSecret(clientSecret).redirectUri(redirectUri).build());
                break;
            case "renren":
                authRequest = new AuthRenrenRequest(AuthConfig.builder().clientId(clientId).clientSecret(clientSecret)
                    .redirectUri(redirectUri).build());
                break;
            case "stack_overflow":
                authRequest = new AuthStackOverflowRequest(AuthConfig.builder().clientId(clientId)
                    .clientSecret(clientSecret).redirectUri(redirectUri).stackOverflowKey("").build());
                break;
            case "huawei":
                authRequest = new AuthHuaweiRequest(AuthConfig.builder().clientId(clientId).clientSecret(clientSecret)
                    .redirectUri(redirectUri).build());
                break;
            case "wechat_enterprise":
                authRequest = new AuthWeChatEnterpriseQrcodeRequest(AuthConfig.builder().clientId(clientId)
                    .clientSecret(clientSecret).redirectUri(redirectUri).agentId("").build());
                break;
            case "kujiale":
                authRequest = new AuthKujialeRequest(AuthConfig.builder().clientId(clientId).clientSecret(clientSecret)
                    .redirectUri(redirectUri).build());
                break;
            case "gitlab":
                authRequest = new AuthGitlabRequest(AuthConfig.builder().clientId(clientId).clientSecret(clientSecret)
                    .redirectUri(redirectUri).build());
                break;
            case "meituan":
                authRequest = new AuthMeituanRequest(AuthConfig.builder().clientId(clientId).clientSecret(clientSecret)
                    .redirectUri(redirectUri).build());
                break;
            case "eleme":
                authRequest = new AuthElemeRequest(AuthConfig.builder().clientId(clientId).clientSecret(clientSecret)
                    .redirectUri(redirectUri).build());
                break;
            case "wechat_mp":
                authRequest = new AuthWeChatMpRequest(AuthConfig.builder().clientId(clientId).clientSecret(clientSecret)
                    .redirectUri(redirectUri).build());
                break;
            case "aliyun":
                authRequest = new AuthAliyunRequest(AuthConfig.builder().clientId(clientId).clientSecret(clientSecret)
                    .redirectUri(redirectUri).build());
                break;
            default: {
            }
        }
        if (null == authRequest) {
            throw new AuthException("未获取到有效的Auth配置");
        }
        return authRequest;
    }

}
