package top.fzshuai.blog.consumer;


import com.alibaba.fastjson.JSON;
import top.fzshuai.blog.constant.MQConstant;
import top.fzshuai.blog.domain.dto.EmailDTO;
import top.fzshuai.common.utils.email.MailUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 通知
 *
 * @author fzshuai
 * @date 2023-05-03
 **/
@Slf4j
@Component
@RabbitListener(queues = MQConstant.EMAIL_QUEUE)
public class EmailConsumer {

    @RabbitHandler
    public void process(byte[] data) {
        EmailDTO emailDTO = JSON.parseObject(new String(data), EmailDTO.class);
        log.info(emailDTO.toString());
        MailUtils.sendText(emailDTO.getEmail(), emailDTO.getSubject(), emailDTO.getContent());
    }

}
