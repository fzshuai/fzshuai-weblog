package top.fzshuai.system.service;

import cn.dev33.satoken.secure.BCrypt;
import top.fzshuai.common.constant.CacheConstants;
import top.fzshuai.common.constant.Constants;
import top.fzshuai.common.core.domain.entity.SysUser;
import top.fzshuai.common.core.domain.event.LogininforEvent;
import top.fzshuai.common.core.domain.model.RegisterBody;
import top.fzshuai.common.enums.UserType;
import top.fzshuai.common.exception.base.BaseException;
import top.fzshuai.common.exception.user.CaptchaException;
import top.fzshuai.common.exception.user.CaptchaExpireException;
import top.fzshuai.common.exception.user.UserException;
import top.fzshuai.common.utils.MessageUtils;
import top.fzshuai.common.utils.ServletUtils;
import top.fzshuai.common.utils.StringUtils;
import top.fzshuai.common.utils.redis.RedisUtils;
import top.fzshuai.common.utils.spring.SpringUtils;
import top.fzshuai.system.domain.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static top.fzshuai.common.constant.BlogConstant.USER_CODE_KEY;
import static top.fzshuai.common.constant.Constants.AVATAR;
import static top.fzshuai.common.constant.Constants.TYPE;

/**
 * 注册校验方法
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@Service
public class SysRegisterService {

    private final ISysUserService userService;
    private final ISysConfigService configService;

    /**
     * 注册
     */
    public void register(RegisterBody registerBody) {
        String username = registerBody.getUsername();
        String password = registerBody.getPassword();
        // 校验用户类型是否存在
        String userType = UserType.getUserType(registerBody.getUserType()).getUserType();

        boolean captchaEnabled = configService.selectCaptchaEnabled();
        // 验证码开关
        if (captchaEnabled) {
            validateCaptcha(username, registerBody.getCode(), registerBody.getUuid());
        }
        SysUser sysUser = new SysUser();
        sysUser.setUserName(username);
        sysUser.setNickName(username);
        sysUser.setPassword(BCrypt.hashpw(password));
        sysUser.setUserType(userType);

        if (!userService.checkUserNameUnique(sysUser)) {
            throw new UserException("user.register.save.error", username);
        }
        boolean regFlag = userService.registerUser(sysUser);
        if (!regFlag) {
            throw new UserException("user.register.error");
        }
        recordLogininfor(username, Constants.REGISTER, MessageUtils.message("user.register.success"));
    }

    /**
     * 博客前台用户注册
     */
    public void blogRegister(UserVo user) {
        // 校验账号是否合法（邮箱是否正确）
        if (checkUser(user)) {
            throw new BaseException("用户名已被注册！");
        }
        SysUser sysUser = new SysUser();
        sysUser.setUserName(user.getUsername());
        sysUser.setNickName(user.getUsername());
        sysUser.setPassword(BCrypt.hashpw(user.getPassword()));
        sysUser.setUserType(TYPE);
        sysUser.setAvatar(AVATAR);
        sysUser.setEmail(user.getEmail());
        boolean regFlag = userService.registerUser(sysUser);
        if (!regFlag) {
            throw new UserException("user.register.error");
        }
        recordLogininfor(user.getUsername(), Constants.REGISTER, MessageUtils.message("user.register.success"));
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     */
    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.defaultString(uuid, "");
        String captcha = RedisUtils.getCacheObject(verifyKey);
        RedisUtils.deleteObject(verifyKey);
        if (captcha == null) {
            recordLogininfor(username, Constants.REGISTER, MessageUtils.message("user.jcaptcha.expire"));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            recordLogininfor(username, Constants.REGISTER, MessageUtils.message("user.jcaptcha.error"));
            throw new CaptchaException();
        }
    }

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息内容
     * @return
     */
    private void recordLogininfor(String username, String status, String message) {
        LogininforEvent logininforEvent = new LogininforEvent();
        logininforEvent.setUsername(username);
        logininforEvent.setStatus(status);
        logininforEvent.setMessage(message);
        logininforEvent.setRequest(ServletUtils.getRequest());
        SpringUtils.context().publishEvent(logininforEvent);
    }

    /**
     * 校验用户数据是否合法
     *
     * @param user 用户数据
     * @return 结果
     */
    private Boolean checkUser(UserVo user) {
        // 验证码检验
        if (!user.getCode().equals(RedisUtils.getCacheObject(USER_CODE_KEY + user.getEmail()))) {
            throw new BaseException("验证码错误！");
        }
        // 查询用户名是否存在
        SysUser sysUser = userService.selectUserByUserName(user.getUsername());
        return Objects.nonNull(sysUser);
    }
}
