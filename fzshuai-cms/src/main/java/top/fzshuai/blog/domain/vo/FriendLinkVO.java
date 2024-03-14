package top.fzshuai.blog.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 友人链接视图对象 blog_friend_link
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@ExcelIgnoreUnannotated
public class FriendLinkVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long friendLinkId;

    /**
     * 链接名
     */
    @ExcelProperty(value = "链接名")
    private String linkName;

    /**
     * 链接头像
     */
    @ExcelProperty(value = "链接头像")
    private String linkAvatar;

    /**
     * 链接地址
     */
    @ExcelProperty(value = "链接地址")
    private String linkAddress;

    /**
     * 链接介绍
     */
    @ExcelProperty(value = "链接介绍")
    private String linkIntro;
}
