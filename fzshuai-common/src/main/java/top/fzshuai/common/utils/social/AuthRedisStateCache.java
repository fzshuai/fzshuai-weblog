package top.fzshuai.common.utils.social;

import lombok.AllArgsConstructor;
import me.zhyd.oauth.cache.AuthStateCache;
import top.fzshuai.common.config.properties.SocialProperties;
import top.fzshuai.common.utils.redis.RedisUtils;

import java.time.Duration;

/**
 * 授权状态缓存
 *
 * @author fzshuai
 * @date 2024/04/11 20:47
 * @since 1.0
 */
@AllArgsConstructor
public class AuthRedisStateCache implements AuthStateCache {

    private final SocialProperties socialProperties;

    /**
     * 存入缓存
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
    @Override
    public void cache(String key, String value) {
        RedisUtils.setCacheObject(key, value, Duration.ofMillis(socialProperties.getTimeout()));
    }

    /**
     * 存入缓存
     *
     * @param key     缓存的键值
     * @param value   缓存的值
     * @param timeout 指定缓存过期时间(毫秒)
     */
    @Override
    public void cache(String key, String value, long timeout) {
        RedisUtils.setCacheObject(key, value, Duration.ofMillis(timeout));
    }

    /**
     * 获取缓存内容
     *
     * @param key 缓存的键值
     * @return 缓存的值
     */
    @Override
    public String get(String key) {
        return RedisUtils.getCacheObject(key);
    }

    /**
     * 是否存在 key
     *
     * @param key 缓存的键值
     * @return true=包含；false=不包含
     */
    @Override
    public boolean containsKey(String key) {
        return RedisUtils.hasKey(key);
    }

}
