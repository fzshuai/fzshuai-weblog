package com.fzshuai.blog.domain.bo;

import com.fzshuai.common.core.validate.AddGroup;
import com.fzshuai.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.fzshuai.common.core.domain.BaseEntity;

/**
 * 留言业务对象 blog_message
 *
 * @author fzshuai
 * @date 2023-05-03
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class MessageBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long messageId;

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String nickname;

    /**
     * 头像
     */
    @NotBlank(message = "头像不能为空", groups = { AddGroup.class, EditGroup.class })
    private String avatar;

    /**
     * 留言内容
     */
    @NotBlank(message = "留言内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String messageContent;

    /**
     * 用户ip
     */
    @NotBlank(message = "用户ip不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ipAddress;

    /**
     * 用户地址
     */
    @NotBlank(message = "用户地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ipSource;

    /**
     * 弹幕速度
     */
    @NotNull(message = "弹幕速度不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer time;

    /**
     * 是否审核
     */
    @NotNull(message = "是否审核不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long isReview;


}
