package top.fzshuai.framework.config;

import me.zhyd.oauth.cache.AuthStateCache;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import top.fzshuai.common.config.properties.SocialProperties;
import top.fzshuai.common.utils.social.AuthRedisStateCache;

/**
 * 配置属性
 *
 * @author fzshuai
 * @date 2024/04/11 20:49
 * @since 1.0
 */
@AutoConfiguration
@EnableConfigurationProperties(SocialProperties.class)
public class SocialConfig {

    @Bean
    public AuthStateCache authStateCache(SocialProperties socialProperties) {
        return new AuthRedisStateCache(socialProperties);
    }

}
