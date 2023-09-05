package com.fzshuai.web.controller.system;

import cn.dev33.satoken.annotation.SaIgnore;
import com.alibaba.fastjson.JSON;
import com.fzshuai.blog.domain.dto.EmailDTO;
import com.fzshuai.common.exception.base.BaseException;
import com.fzshuai.common.core.controller.BaseController;
import com.fzshuai.common.core.domain.R;
import com.fzshuai.common.core.domain.model.RegisterBody;
import com.fzshuai.common.utils.redis.RedisUtils;
import com.fzshuai.system.domain.vo.UserVO;
import com.fzshuai.system.service.ISysConfigService;
import com.fzshuai.system.service.SysRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Duration;

import static com.fzshuai.blog.constant.MQConstant.*;
import static com.fzshuai.common.constant.RedisConstant.*;
import static com.fzshuai.blog.utils.CommonUtil.*;

/**
 * 注册验证
 *
 * @author Lion Li
 */
@Validated
@RequiredArgsConstructor
@RestController
public class SysRegisterController extends BaseController {

    private final SysRegisterService registerService;
    private final ISysConfigService configService;
    private final RabbitTemplate rabbitTemplate;

    /**
     * 用户注册
     */
    @SaIgnore
    @PostMapping("/register")
    public R<Void> register(@Validated @RequestBody RegisterBody user) {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser")))) {
            return R.fail("当前系统没有开启注册功能！");
        }
        registerService.register(user);
        return R.ok();
    }


    /**
     * 前台用户注册邮箱验证码检测
     */
    @SaIgnore
    @GetMapping("/users/code")
    public R<?> sendCode(String email) {
        // 校验邮箱是否合法
        if (!checkEmail(email)) {
            throw new BaseException("请输入正确邮箱");
        }
        // 生成六位随机验证码发送
        String code = getRandomCode();
        // 发送验证码
        EmailDTO emailDTO = EmailDTO.builder()
                .email(email)
                .subject("验证码")
                .content("您的验证码为 " + code + " 有效期15分钟，请不要告诉他人哦！")
                .build();
        rabbitTemplate.convertAndSend(EMAIL_EXCHANGE, "", new Message(JSON.toJSONBytes(emailDTO), new MessageProperties()));
        // 将验证码存入redis，设置过期时间为15分钟
        RedisUtils.setCacheObject(USER_CODE_KEY + email, code, Duration.ofMillis(CODE_EXPIRE_TIME));
        return R.ok();
    }

    /**
     * 前台用户注册
     *
     * @param user 用户信息
     * @return {@link R<>}
     */
    @SaIgnore
    @PostMapping("/blog/register")
    public R<?> register(@Valid @RequestBody UserVO user) {
        registerService.blogRegister(user);
        return R.ok();
    }
}
