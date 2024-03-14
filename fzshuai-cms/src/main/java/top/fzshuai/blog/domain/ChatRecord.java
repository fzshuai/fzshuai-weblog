package top.fzshuai.blog.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import top.fzshuai.common.core.domain.BaseEntity;

/**
 * 聊天记录对象 blog_chat_record
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("blog_chat_record")
public class ChatRecord extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "chat_record_id")
    private Long chatRecordId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 聊天内容
     */
    private String content;
    /**
     * ip地址
     */
    private String ipAddress;
    /**
     * ip来源
     */
    private String ipSource;
    /**
     * 类型
     */
    private Long type;

}
