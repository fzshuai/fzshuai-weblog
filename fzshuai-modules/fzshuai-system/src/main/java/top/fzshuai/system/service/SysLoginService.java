package top.fzshuai.system.service;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.fzshuai.common.constant.CacheConstants;
import top.fzshuai.common.constant.Constants;
import top.fzshuai.common.core.domain.dto.RoleDto;
import top.fzshuai.common.core.domain.entity.SysUser;
import top.fzshuai.common.core.domain.event.LogininforEvent;
import top.fzshuai.common.core.domain.model.BlogLoginUser;
import top.fzshuai.common.core.domain.model.LoginUser;
import top.fzshuai.common.core.domain.model.XcxLoginUser;
import top.fzshuai.common.enums.DeviceType;
import top.fzshuai.common.enums.LoginType;
import top.fzshuai.common.enums.UserStatus;
import top.fzshuai.common.exception.ServiceException;
import top.fzshuai.common.exception.user.CaptchaException;
import top.fzshuai.common.exception.user.CaptchaExpireException;
import top.fzshuai.common.exception.user.UserException;
import top.fzshuai.common.helper.LoginHelper;
import top.fzshuai.common.utils.DateUtils;
import top.fzshuai.common.utils.MessageUtils;
import top.fzshuai.common.utils.ServletUtils;
import top.fzshuai.common.utils.StringUtils;
import top.fzshuai.common.utils.redis.RedisUtils;
import top.fzshuai.common.utils.spring.SpringUtils;
import top.fzshuai.system.domain.bo.SysSocialUserBo;
import top.fzshuai.system.domain.vo.SysSocialUserVo;
import top.fzshuai.system.mapper.SysUserMapper;

import java.time.Duration;
import java.util.List;
import java.util.function.Supplier;

/**
 * 登录校验方法
 *
 * @author Lion Li fzshuai
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class SysLoginService {

    private final SysUserMapper userMapper;
    private final ISysConfigService configService;
    private final ISysSocialUserService socialUserService;
    private final ISysUserService userService;
    private final SysPermissionService permissionService;

    @Value("${user.password.maxRetryCount}")
    private Integer maxRetryCount;

    @Value("${user.password.lockTime}")
    private Integer lockTime;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid) {
        boolean captchaEnabled = configService.queryCaptchaEnabled();
        // 验证码开关
        if (captchaEnabled) {
            validateCaptcha(username, code, uuid);
        }
        // 框架登录不限制从什么表查询 只要最终构建出 LoginUser 即可
        SysUser user = loadUserByUsername(username);
        checkLogin(LoginType.PASSWORD, username, () -> !BCrypt.checkpw(password, user.getPassword()));
        // 此处可根据登录用户的数据不同 自行创建 loginUser 属性不够用继承扩展就行了
        LoginUser loginUser = buildLoginUser(user);
        // 生成token
        LoginHelper.loginByDevice(loginUser, DeviceType.PC);

        recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
        recordLoginInfo(user.getUserId(), username);
        return StpUtil.getTokenValue();
    }

    public BlogLoginUser blogLogin(String username, String password) {
        SysUser user = loadUserByUsername(username);
        checkLogin(LoginType.PASSWORD, username, () -> !BCrypt.checkpw(password, user.getPassword()));
        // 构建博客用户登录对象
        BlogLoginUser blogUser = new BlogLoginUser();
        blogUser.setUserId(user.getUserId());
        blogUser.setEmail(user.getEmail());
        blogUser.setAvatar(user.getAvatar());
        blogUser.setNickname(user.getNickName());
        blogUser.setIpaddr(user.getLoginIp());
        blogUser.setUsername(user.getUserName());

        recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
        recordLoginInfo(user.getUserId(), username);

        return blogUser;

    }

    public String smsLogin(String phonenumber, String smsCode) {
        // 通过手机号查找用户
        SysUser user = loadUserByPhoneNumber(phonenumber);

        checkLogin(LoginType.SMS, user.getUserName(), () -> !validateSmsCode(phonenumber, smsCode));
        // 此处可根据登录用户的数据不同 自行创建 loginUser 属性不够用继承扩展就行了
        LoginUser loginUser = buildLoginUser(user);
        // 生成token
        LoginHelper.loginByDevice(loginUser, DeviceType.APP);

        recordLogininfor(user.getUserName(), Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
        recordLoginInfo(user.getUserId(), user.getUserName());
        return StpUtil.getTokenValue();
    }

    public String emailLogin(String email, String emailCode) {
        // 通过手邮箱查找用户
        SysUser user = loadUserByEmail(email);

        checkLogin(LoginType.EMAIL, user.getUserName(), () -> !validateEmailCode(email, emailCode));
        // 此处可根据登录用户的数据不同 自行创建 loginUser 属性不够用继承扩展就行了
        LoginUser loginUser = buildLoginUser(user);
        // 生成token
        LoginHelper.loginByDevice(loginUser, DeviceType.APP);

        recordLogininfor(user.getUserName(), Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
        recordLoginInfo(user.getUserId(), user.getUserName());
        return StpUtil.getTokenValue();
    }

    public String xcxLogin(String xcxCode) {
        // xcxCode 为 小程序调用 wx.login 授权后获取
        // todo 以下自行实现
        // 校验 appid + appsrcret + xcxCode 调用登录凭证校验接口 获取 session_key 与 openid
        String openid = "";

        // 框架登录不限制从什么表查询 只要最终构建出 LoginUser 即可
        SysUser user = loadUserByOpenid(openid);

        // 此处可根据登录用户的数据不同 自行创建 loginUser 属性不够用继承扩展就行了
        XcxLoginUser loginUser = new XcxLoginUser();
        loginUser.setUserId(user.getUserId());
        loginUser.setUsername(user.getUserName());
        loginUser.setUserType(user.getUserType());
        loginUser.setOpenid(openid);
        // 生成token
        LoginHelper.loginByDevice(loginUser, DeviceType.XCX);

        recordLogininfor(user.getUserName(), Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
        recordLoginInfo(user.getUserId(), user.getUserName());
        return StpUtil.getTokenValue();
    }

    /**
     * 社交用户登录
     *
     * @param source   登录来源
     * @param authUser 授权响应实体
     * @return 统一响应实体
     */
    public String socialLogin(String source, AuthResponse<AuthUser> authUser) {
        // 判断授权响应是否成功
        if (!authUser.ok()) {
            return "对不起，授权信息验证不通过，请退出重试！";
        }
        AuthUser authUserData = authUser.getData();
        SysSocialUserVo user = socialUserService.queryByAuthId(authUserData.getSource() + authUserData.getUuid());
        if (ObjectUtil.isNotNull(user)) {
            // 执行登录和记录登录信息操作
            return loginAndRecord(user.getUserName());
        } else {
            // 判断是否已登录
            if (!StpUtil.isLogin()) {
                return "授权失败，请先登录才能绑定";
            }
            // 注册第三方社交用户
            socialRegister(authUserData);
            SysUser sysUser = loadUserByUsername(LoginHelper.getUsername());
            // 执行登录和记录登录信息操作
            return loginAndRecord(sysUser.getUserName());
        }
    }

    /**
     * 社交用户登录
     *
     * @param authUser 授权响应实体
     * @return 统一响应实体
     */
    public BlogLoginUser blogSocialLogin(AuthResponse<AuthUser> authUser) {
        // 判断授权响应是否成功
        if (!authUser.ok()) {
            throw new ServiceException("对不起，授权信息验证不通过，请退出重试！");
        }
        AuthUser authUserData = authUser.getData();
        SysSocialUserVo user = socialUserService.queryByAuthId(authUserData.getSource() + authUserData.getUuid());
        if (ObjectUtil.isNotNull(user)) {
            // 执行登录和记录登录信息操作
            loginAndRecord(user.getUserName());
            BlogLoginUser blogLoginUser = new BlogLoginUser();
            blogLoginUser.setUserId(user.getUserId());
            blogLoginUser.setUsername(user.getUserName());
            blogLoginUser.setNickname(user.getNickName());
            blogLoginUser.setAvatar(user.getAvatar());
            blogLoginUser.setEmail(user.getEmail());
            return blogLoginUser;
        } else {
            // 注册第三方社交用户
            socialRegister(authUserData);
            // 执行登录和记录登录信息操作
            loginAndRecord(authUserData.getUsername());
            BlogLoginUser blogLoginUser = new BlogLoginUser();
            blogLoginUser.setUsername(authUserData.getUsername());
            blogLoginUser.setNickname(authUserData.getNickname());
            blogLoginUser.setAvatar(authUserData.getAvatar());
            blogLoginUser.setEmail(authUserData.getEmail());
            return blogLoginUser;
        }
    }

    /**
     * 注册第三方社交用户
     *
     * @param authUserData 授权响应实体
     */
    public void socialRegister(AuthUser authUserData) {
        SysSocialUserBo bo = BeanUtil.toBean(authUserData, SysSocialUserBo.class);
        BeanUtil.copyProperties(authUserData.getToken(), bo);
        bo.setUserId(LoginHelper.getUserId());
        bo.setAuthId(authUserData.getSource() + authUserData.getUuid());
        bo.setOpenId(authUserData.getUuid());
        bo.setUserName(authUserData.getUsername());
        bo.setNickName(authUserData.getNickname());
        bo.setAvatar(authUserData.getAvatar());
        socialUserService.insertByBo(bo);
    }

    /**
     * 执行登录和记录登录信息操作
     *
     * @param userName 用户名
     * @return 统一响应实体
     */
    private String loginAndRecord(String userName) {
        SysUser dbUser = loadUserByUsername(userName);
        LoginHelper.loginByDevice(buildLoginUser(dbUser), DeviceType.SOCIAL);
        recordLogininfor(userName, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
        recordLoginInfo(dbUser.getUserId(), dbUser.getUserName());
        return StpUtil.getTokenValue();
    }

    /**
     * 退出登录
     */
    public void logout() {
        try {
            LoginUser loginUser = LoginHelper.getLoginUser();
            recordLogininfor(loginUser.getUsername(), Constants.LOGOUT, MessageUtils.message("user.logout.success"));
        } catch (NotLoginException ignored) {
        } finally {
            try {
                StpUtil.logout();
            } catch (NotLoginException ignored) {
            }
        }
    }

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息内容
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
     * 校验短信验证码
     */
    private boolean validateSmsCode(String phonenumber, String smsCode) {
        String code = RedisUtils.getCacheObject(CacheConstants.CAPTCHA_CODE_KEY + phonenumber);
        if (StringUtils.isBlank(code)) {
            recordLogininfor(phonenumber, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"));
            throw new CaptchaExpireException();
        }
        return code.equals(smsCode);
    }

    /**
     * 校验邮箱验证码
     */
    private boolean validateEmailCode(String email, String emailCode) {
        String code = RedisUtils.getCacheObject(CacheConstants.CAPTCHA_CODE_KEY + email);
        if (StringUtils.isBlank(code)) {
            recordLogininfor(email, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"));
            throw new CaptchaExpireException();
        }
        return code.equals(emailCode);
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
            recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error"));
            throw new CaptchaException();
        }
    }

    private SysUser loadUserByUsername(String username) {
        SysUser user = userMapper.selectOne(new LambdaQueryWrapper<SysUser>()
            .select(SysUser::getUserName, SysUser::getStatus)
            .eq(SysUser::getUserName, username));
        if (ObjectUtil.isNull(user)) {
            log.info("登录用户：{} 不存在.", username);
            throw new UserException("user.not.exists", username);
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new UserException("user.blocked", username);
        }
        return userMapper.selectUserByUserName(username);
    }

    private SysUser loadUserByPhoneNumber(String phoneNumber) {
        SysUser user = userMapper.selectOne(new LambdaQueryWrapper<SysUser>()
            .select(SysUser::getPhoneNumber, SysUser::getStatus)
            .eq(SysUser::getPhoneNumber, phoneNumber));
        if (ObjectUtil.isNull(user)) {
            log.info("登录用户：{} 不存在.", phoneNumber);
            throw new UserException("user.not.exists", phoneNumber);
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", phoneNumber);
            throw new UserException("user.blocked", phoneNumber);
        }
        return userMapper.selectUserByPhoneNumber(phoneNumber);
    }

    private SysUser loadUserByEmail(String email) {
        SysUser user = userMapper.selectOne(new LambdaQueryWrapper<SysUser>()
            .select(SysUser::getPhoneNumber, SysUser::getStatus)
            .eq(SysUser::getEmail, email));
        if (ObjectUtil.isNull(user)) {
            log.info("登录用户：{} 不存在.", email);
            throw new UserException("user.not.exists", email);
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", email);
            throw new UserException("user.blocked", email);
        }
        return userMapper.selectUserByEmail(email);
    }

    private SysUser loadUserByOpenid(String openid) {
        // 使用 openid 查询绑定用户 如未绑定用户 则根据业务自行处理 例如 创建默认用户
        // todo 自行实现 userService.selectUserByOpenid(openid);
        SysUser user = new SysUser();
        if (ObjectUtil.isNull(user)) {
            log.info("登录用户：{} 不存在.", openid);
            // todo 用户不存在 业务逻辑自行实现
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", openid);
            // todo 用户已被停用 业务逻辑自行实现
        }
        return user;
    }

    /**
     * 构建登录用户
     */
    private LoginUser buildLoginUser(SysUser user) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(user.getUserId());
        loginUser.setDeptId(user.getDeptId());
        loginUser.setUsername(user.getUserName());
        loginUser.setUserType(user.getUserType());
        loginUser.setMenuPermission(permissionService.queryMenuPermission(user));
        loginUser.setRolePermission(permissionService.queryRolePermission(user));
        loginUser.setDeptName(ObjectUtil.isNull(user.getDept()) ? "" : user.getDept().getDeptName());
        List<RoleDto> roles = BeanUtil.copyToList(user.getRoles(), RoleDto.class);
        loginUser.setRoles(roles);
        return loginUser;
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId, String username) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(ServletUtils.getClientIP());
        sysUser.setLoginDate(DateUtils.getNowDate());
        sysUser.setUpdateBy(username);
        userMapper.updateById(sysUser);
    }

    /**
     * 登录校验
     */
    private void checkLogin(LoginType loginType, String username, Supplier<Boolean> supplier) {
        String errorKey = CacheConstants.PWD_ERR_CNT_KEY + username;
        String loginFail = Constants.LOGIN_FAIL;

        // 获取用户登录错误次数，默认为0 (可自定义限制策略 例如: key + username + ip)
        int errorNumber = ObjectUtil.defaultIfNull(RedisUtils.getCacheObject(errorKey), 0);
        // 锁定时间内登录 则踢出
        if (errorNumber >= maxRetryCount) {
            recordLogininfor(username, loginFail, MessageUtils.message(loginType.getRetryLimitExceed(), maxRetryCount, lockTime));
            throw new UserException(loginType.getRetryLimitExceed(), maxRetryCount, lockTime);
        }

        if (supplier.get()) {
            // 错误次数递增
            errorNumber++;
            RedisUtils.setCacheObject(errorKey, errorNumber, Duration.ofMinutes(lockTime));
            // 达到规定错误次数 则锁定登录
            if (errorNumber >= maxRetryCount) {
                recordLogininfor(username, loginFail, MessageUtils.message(loginType.getRetryLimitExceed(), maxRetryCount, lockTime));
                throw new UserException(loginType.getRetryLimitExceed(), maxRetryCount, lockTime);
            } else {
                // 未达到规定错误次数
                recordLogininfor(username, loginFail, MessageUtils.message(loginType.getRetryLimitCount(), errorNumber));
                throw new UserException(loginType.getRetryLimitCount(), errorNumber);
            }
        }

        // 登录成功 清空错误次数
        RedisUtils.deleteObject(errorKey);
    }

}
