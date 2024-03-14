package top.fzshuai.blog.domain.bo;

import top.fzshuai.common.core.validate.AddGroup;
import top.fzshuai.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import top.fzshuai.common.core.domain.BaseEntity;

/**
 * 友人链接业务对象 blog_friend_link
 *
 * @author fzshuai
 * @date 2023-05-03
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class FriendLinkBO extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long friendLinkId;

    /**
     * 链接名
     */
    @NotBlank(message = "链接名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String linkName;

    /**
     * 链接头像
     */
    @NotBlank(message = "链接头像不能为空", groups = { AddGroup.class, EditGroup.class })
    private String linkAvatar;

    /**
     * 链接地址
     */
    @NotBlank(message = "链接地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String linkAddress;

    /**
     * 链接介绍
     */
    @NotBlank(message = "链接介绍不能为空", groups = { AddGroup.class, EditGroup.class })
    private String linkIntro;


}
