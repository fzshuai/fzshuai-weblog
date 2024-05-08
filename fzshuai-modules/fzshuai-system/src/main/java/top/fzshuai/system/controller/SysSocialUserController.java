package top.fzshuai.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.fzshuai.common.annotation.Log;
import top.fzshuai.common.annotation.RepeatSubmit;
import top.fzshuai.common.config.properties.SocialLoginConfigProperties;
import top.fzshuai.common.config.properties.SocialProperties;
import top.fzshuai.common.core.controller.BaseController;
import top.fzshuai.common.core.domain.R;
import top.fzshuai.common.core.domain.model.BlogLoginUser;
import top.fzshuai.common.core.domain.model.SocialLoginBody;
import top.fzshuai.common.core.validate.AddGroup;
import top.fzshuai.common.enums.BusinessType;
import top.fzshuai.common.helper.LoginHelper;
import top.fzshuai.common.utils.social.SocialUtils;
import top.fzshuai.system.domain.bo.SysSocialUserBo;
import top.fzshuai.system.domain.vo.SysSocialUserVo;
import top.fzshuai.system.service.ISysSocialUserService;
import top.fzshuai.system.service.SysLoginService;

import java.util.List;

/**
 * 社交用户信息
 *
 * @author fzshuai
 * @date 2024/03/28 20:52
 * @since 1.0
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/social")
public class SysSocialUserController extends BaseController {

    private final SocialProperties socialProperties;
    private final SysLoginService loginService;
    private final ISysSocialUserService socialService;

    /**
     * 新增社交用户
     */
    @SaCheckPermission("system:user:add")
    @Log(title = "社会化关系", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysSocialUserBo bo) {
        return toAjax(socialService.insertByBo(bo));
    }

    /**
     * 查询社交用户列表
     */
    @SaCheckPermission("system:user:list")
    @GetMapping("/list")
    public R<List<SysSocialUserVo>> list() {
        return R.ok(socialService.queryListByUserId(LoginHelper.getUserId()));
    }

    /**
     * 认证授权
     *
     * @param source 登录来源
     * @return 结果
     */
    @SaIgnore
    @GetMapping("/binding/{source}")
    public R<String> authBinding(@PathVariable("source") String source) {
        SocialLoginConfigProperties obj = socialProperties.getType().get(source);
        if (ObjectUtil.isNull(obj)) {
            return R.fail(source + "平台账号暂不支持");
        }
        AuthRequest authRequest = SocialUtils.getAuthRequest(source, socialProperties);
        String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
        return R.ok(authorizeUrl);
    }

    /**
     * 第三方登录回调业务处理
     *
     * @param loginBody 请求体
     * @return 回调地址
     */
    @SaIgnore
    @GetMapping("/callback")
    public R<String> socialCallback(@RequestBody SocialLoginBody loginBody) {
        // 获取第三方登录信息
        AuthResponse<AuthUser> response = SocialUtils.loginAuth(loginBody.getSource(), loginBody.getCode(), loginBody.getState(), socialProperties);
        // 判断授权响应是否成功
        if (!response.ok()) {
            return R.fail(response.getMsg());
        }
        return R.ok(loginService.socialLogin(response));
    }

    /**
     * 第三方登录回调业务处理
     *
     * @param loginBody 请求体
     * @return 回调地址
     */
    @SaIgnore
    @PostMapping("/blog/callback")
    public R<BlogLoginUser> blogSocialCallback(@RequestBody SocialLoginBody loginBody) {
        // 获取第三方登录信息
        AuthResponse<AuthUser> response = SocialUtils.loginAuth(loginBody.getSource(), loginBody.getCode(), loginBody.getState(), socialProperties);
        // 判断授权响应是否成功
        if (!response.ok()) {
            return R.fail(response.getMsg());
        }
        return R.ok(loginService.blogSocialLogin(response));
    }

    /**
     * 取消授权
     *
     * @param socialId 主键
     */
    @DeleteMapping(value = "/unlock/{socialId}")
    public R<Void> unlockSocial(@PathVariable Long socialId) {
        Boolean rows = socialService.deleteWithValidById(socialId);
        return rows ? R.ok() : R.fail("取消授权失败");
    }

}
