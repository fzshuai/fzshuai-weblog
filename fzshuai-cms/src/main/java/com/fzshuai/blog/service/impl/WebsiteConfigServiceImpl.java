package com.fzshuai.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.fzshuai.common.utils.redis.RedisUtils;
import com.fzshuai.system.mapper.SysOssMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.fzshuai.blog.domain.vo.WebsiteConfigVo;
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
    private final SysOssMapper ossMapper;

    /**
     * 前台获取
     *
     * @return
     */
    @Override
    public WebsiteConfigVo getWebsiteConfig() {
        WebsiteConfigVo websiteConfigVo;
        // 获取缓存数据
        Object websiteConfig = RedisUtils.getCacheObject(WEBSITE_CONFIG);
        if (Objects.nonNull(websiteConfig)) {
            websiteConfigVo = JSON.parseObject(websiteConfig.toString(), WebsiteConfigVo.class);
            // 将图片改为url地址
            websiteConfigVo.setWebsiteAvatar(ossMapper.selectVoById(Long.parseLong(websiteConfigVo.getWebsiteAvatar())).getUrl());
            websiteConfigVo.setAlipayQRCode(ossMapper.selectVoById(Long.parseLong(websiteConfigVo.getAlipayQRCode())).getUrl());
            websiteConfigVo.setTouristAvatar(ossMapper.selectVoById(Long.parseLong(websiteConfigVo.getTouristAvatar())).getUrl());
            websiteConfigVo.setUserAvatar(ossMapper.selectVoById(Long.parseLong(websiteConfigVo.getUserAvatar())).getUrl());
            websiteConfigVo.setWeiXinQRCode(ossMapper.selectVoById(Long.parseLong(websiteConfigVo.getWeiXinQRCode())).getUrl());
        } else {
            // 从数据库中加载
            String config = baseMapper.selectById(DEFAULT_CONFIG_ID).getConfig();
            websiteConfigVo = JSON.parseObject(config, WebsiteConfigVo.class);
            // 将图片改为url地址
            websiteConfigVo.setWebsiteAvatar(ossMapper.selectVoById(Long.parseLong(websiteConfigVo.getWebsiteAvatar())).getUrl());
            websiteConfigVo.setAlipayQRCode(ossMapper.selectVoById(Long.parseLong(websiteConfigVo.getAlipayQRCode())).getUrl());
            websiteConfigVo.setTouristAvatar(ossMapper.selectVoById(Long.parseLong(websiteConfigVo.getTouristAvatar())).getUrl());
            websiteConfigVo.setUserAvatar(ossMapper.selectVoById(Long.parseLong(websiteConfigVo.getUserAvatar())).getUrl());
            websiteConfigVo.setWeiXinQRCode(ossMapper.selectVoById(Long.parseLong(websiteConfigVo.getWeiXinQRCode())).getUrl());
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
    public WebsiteConfigVo getAdminWebsiteConfig() {
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
