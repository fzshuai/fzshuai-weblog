package top.fzshuai.blog.service.impl;

import com.alibaba.fastjson.JSON;
import top.fzshuai.common.utils.redis.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.fzshuai.blog.domain.vo.WebsiteConfigVo;
import top.fzshuai.blog.domain.WebsiteConfig;
import top.fzshuai.blog.mapper.WebsiteConfigMapper;
import top.fzshuai.blog.service.IWebsiteConfigService;

import java.util.Objects;

import static top.fzshuai.common.constant.BlogConstant.DEFAULT_CONFIG_ID;
import static top.fzshuai.common.constant.RedisConstant.WEBSITE_CONFIG;

/**
 * 网站配置Service业务层处理
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@RequiredArgsConstructor
@Service
public class WebsiteConfigServiceImpl implements IWebsiteConfigService {

    private final WebsiteConfigMapper baseMapper;

    /**
     * 前台获取
     *
     * @return
     */
    @Override
    public WebsiteConfigVo selectWebsiteConfig() {
        WebsiteConfigVo websiteConfigVo;
        // 获取缓存数据
        Object websiteConfig = RedisUtils.getCacheObject(WEBSITE_CONFIG);
        if (Objects.nonNull(websiteConfig)) {
            websiteConfigVo = JSON.parseObject(websiteConfig.toString(), WebsiteConfigVo.class);
        } else {
            // 从数据库中加载
            String config = baseMapper.selectById(DEFAULT_CONFIG_ID).getConfig();
            websiteConfigVo = JSON.parseObject(config, WebsiteConfigVo.class);
            RedisUtils.setCacheObject(WEBSITE_CONFIG, config);
        }
        return websiteConfigVo;
    }

    /**
     * 后台获取
     *
     * @return
     */
    @Override
    public WebsiteConfigVo selectAdminWebsiteConfig() {
        WebsiteConfigVo websiteConfigVo;
        String config = baseMapper.selectById(DEFAULT_CONFIG_ID).getConfig();
        websiteConfigVo = JSON.parseObject(config, WebsiteConfigVo.class);
        return websiteConfigVo;
    }

    @Override
    public void updateWebsiteConfig(WebsiteConfigVo websiteConfigVo) {
        // 修改网站配置
        WebsiteConfig websiteConfig = WebsiteConfig.builder()
            .websiteConfigId(1L)
            .config(JSON.toJSONString(websiteConfigVo))
            .build();
        baseMapper.updateById(websiteConfig);
        // 删除缓存
        RedisUtils.deleteObject(WEBSITE_CONFIG);
    }

}
