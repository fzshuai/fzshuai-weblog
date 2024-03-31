package top.fzshuai.weblog.domain.bo;

import top.fzshuai.common.core.validate.AddGroup;
import top.fzshuai.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;

import top.fzshuai.common.core.domain.BaseEntity;

/**
 * 聊天记录业务对象 blog_chat_record
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ChatRecordBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = {EditGroup.class})
    private Long chatRecordId;

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空", groups = {AddGroup.class, EditGroup.class})
    private Long userId;

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空", groups = {AddGroup.class, EditGroup.class})
    private String nickname;

    /**
     * 头像
     */
    @NotBlank(message = "头像不能为空", groups = {AddGroup.class, EditGroup.class})
    private String avatar;

    /**
     * 聊天内容
     */
    @NotBlank(message = "聊天内容不能为空", groups = {AddGroup.class, EditGroup.class})
    private String content;

    /**
     * ip地址
     */
    @NotBlank(message = "ip地址不能为空", groups = {AddGroup.class, EditGroup.class})
    private String ipAddress;

    /**
     * ip来源
     */
    @NotBlank(message = "ip来源不能为空", groups = {AddGroup.class, EditGroup.class})
    private String ipSource;

    /**
     * 类型
     */
    @NotNull(message = "类型不能为空", groups = {AddGroup.class, EditGroup.class})
    private Integer type;

}
