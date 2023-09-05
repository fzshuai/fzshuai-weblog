package com.fzshuai.blog.domain.bo;

import com.fzshuai.common.core.domain.BaseEntity;
import com.fzshuai.common.core.validate.AddGroup;
import com.fzshuai.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 说说业务对象 blog_talk
 *
 * @author fzshuai
 * @date 2023-05-03
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class TalkBO extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long talkId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 说说内容
     */
    @NotBlank(message = "说说内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String content;

    /**
     * 图片
     */
    private String images;

    /**
     * 是否置顶
     */
    private Integer isTop;

    /**
     * 图片集合
     */
    private List<String> ImgList;
}
