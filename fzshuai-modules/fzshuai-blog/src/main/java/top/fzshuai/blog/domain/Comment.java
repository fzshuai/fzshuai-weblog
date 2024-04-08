package top.fzshuai.blog.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import top.fzshuai.common.core.domain.BaseEntity;

/**
 * 文章评论对象 blog_comment
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@TableName("blog_comment")
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "comment_id")
    private Long commentId;

    /**
     * 评论用户Id
     */
    private Long userId;

    /**
     * 评论主题id
     */
    private Long topicId;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 回复用户id
     */
    private Long replyUserId;

    /**
     * 父评论id
     */
    private Long parentId;

    /**
     * 评论类型 1.文章 2.友链 3.说说
     */
    private Integer type;

    /**
     * 是否删除  0否 1是
     */
    private Integer isDelete;

    /**
     * 评论ip
     */
    private String ipAddress;

    /**
     * 真实地址
     */
    private String ipSource;

    /**
     * 评论状态
     */
    private Integer state;

}
