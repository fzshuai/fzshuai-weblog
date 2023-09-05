package com.fzshuai.blog.service;

import com.fzshuai.blog.domain.vo.WebsiteConfigVO;

/**
 * 网站配置Service接口
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public interface IWebsiteConfigService {

    /**
     * 前端获取网站配置
     *
     * @return {@link WebsiteConfigVO} 网站配置
     */
    WebsiteConfigVO getWebsiteConfig();

    /**
     * 后台获取网站配置
     *
     * @return {@link WebsiteConfigVO} 网站配置
     */
    WebsiteConfigVO getAdminWebsiteConfig();

    /**
     * 保存或更新网站配置
     *
     * @param websiteConfigVo 网站配置
     */
    void updateWebsiteConfig(WebsiteConfigVO websiteConfigVo);
}
