package com.fzshuai.blog.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 留言视图对象 blog_message
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@ExcelIgnoreUnannotated
public class MessageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long messageId;

    /**
     * 昵称
     */
    @ExcelProperty(value = "昵称")
    private String nickname;

    /**
     * 头像
     */
    @ExcelProperty(value = "头像")
    private String avatar;

    /**
     * 留言内容
     */
    @ExcelProperty(value = "留言内容")
    private String messageContent;

    /**
     * 用户ip
     */
    @ExcelProperty(value = "用户ip")
    private String ipAddress;

    /**
     * 用户地址
     */
    @ExcelProperty(value = "用户地址")
    private String ipSource;

    /**
     * 弹幕速度
     */
    @ExcelProperty(value = "弹幕速度")
    private Integer time;

    /**
     * 是否审核
     */
    @ExcelProperty(value = "是否审核")
    private Integer isReview;
}
