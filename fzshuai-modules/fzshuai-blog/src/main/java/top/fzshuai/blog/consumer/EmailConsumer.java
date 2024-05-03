package top.fzshuai.blog.consumer;

import com.alibaba.fastjson.JSON;
import top.fzshuai.common.constant.MQConstant;
import top.fzshuai.system.domain.bo.EmailBo;
import top.fzshuai.common.utils.email.MailUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 邮件通知
 *
 * @author fzshuai
 * @date 2023/05/03 20:26
 * @since 1.0
 */
@Slf4j
@Component
@RabbitListener(queues = MQConstant.EMAIL_QUEUE)
public class EmailConsumer {

    @RabbitHandler
    public void process(byte[] data) {
        EmailBo emailBo = JSON.parseObject(new String(data), EmailBo.class);
        log.info(emailBo.toString());
        MailUtils.sendText(emailBo.getEmail(), emailBo.getSubject(), emailBo.getContent());
    }

}
