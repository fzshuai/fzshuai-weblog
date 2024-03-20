package top.fzshuai.web.controller.system;

import cn.dev33.satoken.annotation.SaIgnore;
import com.alibaba.fastjson.JSON;
import top.fzshuai.blog.domain.dto.EmailDto;
import top.fzshuai.common.exception.base.BaseException;
import top.fzshuai.common.core.controller.BaseController;
import top.fzshuai.common.core.domain.R;
import top.fzshuai.common.core.domain.model.RegisterBody;
import top.fzshuai.common.utils.redis.RedisUtils;
import top.fzshuai.system.domain.vo.UserVo;
import top.fzshuai.system.service.ISysConfigService;
import top.fzshuai.system.service.SysRegisterService;
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

import static top.fzshuai.blog.constant.MQConstant.EMAIL_EXCHANGE;
import static top.fzshuai.blog.utils.CommonUtil.checkEmail;
import static top.fzshuai.blog.utils.CommonUtil.getRandomCode;
import static top.fzshuai.common.constant.RedisConstant.CODE_EXPIRE_TIME;
import static top.fzshuai.common.constant.RedisConstant.USER_CODE_KEY;

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
        EmailDto emailDTO = EmailDto.builder()
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
    public R<?> register(@Valid @RequestBody UserVo user) {
        registerService.blogRegister(user);
        return R.ok();
    }
}
