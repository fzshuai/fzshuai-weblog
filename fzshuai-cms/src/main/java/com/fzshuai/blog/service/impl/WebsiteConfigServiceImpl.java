package com.fzshuai.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.fzshuai.common.utils.redis.RedisUtils;
import com.fzshuai.system.mapper.SysOssMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.fzshuai.blog.domain.vo.WebsiteConfigVO;
import com.fzshuai.blog.domain.WebsiteConfig;
import com.fzshuai.blog.mapper.WebsiteConfigMapper;
import com.fzshuai.blog.service.IWebsiteConfigService;

import java.util.Objects;

import static com.fzshuai.common.constant.BlogConstant.DEFAULT_CONFIG_ID;
import static com.fzshuai.common.constant.RedisConstant.WEBSITE_CONFIG;

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
    public WebsiteConfigVO selectWebsiteConfig() {
        WebsiteConfigVO websiteConfigVo;
        // 获取缓存数据
        Object websiteConfig = RedisUtils.getCacheObject(WEBSITE_CONFIG);
        if (Objects.nonNull(websiteConfig)) {
            websiteConfigVo = JSON.parseObject(websiteConfig.toString(), WebsiteConfigVO.class);
        } else {
            // 从数据库中加载
            String config = baseMapper.selectById(DEFAULT_CONFIG_ID).getConfig();
            websiteConfigVo = JSON.parseObject(config, WebsiteConfigVO.class);
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
    public WebsiteConfigVO selectAdminWebsiteConfig() {
        WebsiteConfigVO websiteConfigVo;
        String config = baseMapper.selectById(DEFAULT_CONFIG_ID).getConfig();
        websiteConfigVo = JSON.parseObject(config, WebsiteConfigVO.class);
        return websiteConfigVo;
    }

    @Override
    public void updateWebsiteConfig(WebsiteConfigVO websiteConfigVo) {
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
