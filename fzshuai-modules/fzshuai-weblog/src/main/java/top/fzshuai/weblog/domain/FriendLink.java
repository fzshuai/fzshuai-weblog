package top.fzshuai.weblog.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import top.fzshuai.common.core.domain.BaseEntity;

/**
 * 友人链接对象 blog_friend_link
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("blog_friend_link")
public class FriendLink extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "friend_link_id")
    private Long friendLinkId;
    /**
     * 链接名
     */
    private String linkName;
    /**
     * 链接头像
     */
    private String linkAvatar;
    /**
     * 链接地址
     */
    private String linkAddress;
    /**
     * 链接介绍
     */
    private String linkIntro;

}
