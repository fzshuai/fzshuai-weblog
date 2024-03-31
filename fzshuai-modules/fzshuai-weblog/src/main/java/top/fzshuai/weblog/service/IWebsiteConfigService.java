package top.fzshuai.weblog.service;

import top.fzshuai.weblog.domain.vo.WebsiteConfigVo;

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
     * @return {@link WebsiteConfigVo} 网站配置
     */
    WebsiteConfigVo selectWebsiteConfig();

    /**
     * 后台获取网站配置
     *
     * @return {@link WebsiteConfigVo} 网站配置
     */
    WebsiteConfigVo selectAdminWebsiteConfig();

    /**
     * 保存或更新网站配置
     *
     * @param websiteConfigVo 网站配置
     */
    void updateWebsiteConfig(WebsiteConfigVo websiteConfigVo);

}
