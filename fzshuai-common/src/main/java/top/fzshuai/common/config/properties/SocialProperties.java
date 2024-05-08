package top.fzshuai.common.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 社交配置属性
 *
 * @author fzshuai
 * @date 2024/03/28 19:40
 * @since 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "justauth")
public class SocialProperties {

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 授权类型
     */
    private Map<String, SocialLoginConfigProperties> type;

    /**
     * 授权过期时间
     */
    private Long timeout;

}
