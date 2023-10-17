package com.fzshuai.blog.controller;

import com.fzshuai.blog.domain.vo.WebsiteConfigVO;
import com.fzshuai.blog.service.IWebsiteConfigService;
import com.fzshuai.common.core.controller.BaseController;
import com.fzshuai.common.core.domain.R;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 网站配置
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/blog/website")
public class WebsiteConfigController extends BaseController {

    private final IWebsiteConfigService websiteConfigService;

    /**
     * 前台获取网站配置
     */
    @GetMapping("/config")
    public R<WebsiteConfigVO> getWebsiteConfig() {
        return R.ok(websiteConfigService.selectWebsiteConfig());
    }

    /**
     * 后台获取网站配置
     */
    @GetMapping("/admin/config")
    public R<WebsiteConfigVO> getAdminWebsiteConfig() {
        return R.ok(websiteConfigService.selectAdminWebsiteConfig());
    }

    /**
     * 更新网站配置
     *
     * @param websiteConfigVO 网站配置信息
     * @return {@link R}
     */
    @PutMapping("/config")
    public R<?> updateWebsiteConfig(@Valid @RequestBody WebsiteConfigVO websiteConfigVO) {
        websiteConfigService.updateWebsiteConfig(websiteConfigVO);
        return R.ok();
    }
}
