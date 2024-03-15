package top.fzshuai.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

/**
 * 声音签证官
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
public class VoiceVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息类型
     */
    private Integer type;

    /**
     * 文件
     */
    private MultipartFile file;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 聊天内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 用户登录ip
     */
    private String ipAddress;

    /**
     * ip来源
     */
    private String ipSource;

}
