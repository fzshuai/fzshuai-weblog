package com.fzshuai.blog.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fzshuai.common.annotation.ExcelDictFormat;
import com.fzshuai.common.convert.ExcelDictConvert;
import lombok.Data;

import java.util.List;


/**
 * 说说视图对象 blog_talk
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@ExcelIgnoreUnannotated
public class TalkVo {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long talkId;

    /**
     * 用户id
     */
    @ExcelProperty(value = "用户id")
    private Long userId;

    /**
     * 作者头像
     */
    private String avatar;

    /**
     * 作者昵称
     */
    private String nickName;

    /**
     * 说说内容
     */
    @ExcelProperty(value = "说说内容")
    private String content;

    /**
     * 图片
     */
    @ExcelProperty(value = "图片")
    private String images;

    /**
     * 图片列表
     */
    private List<String> imgList;

    /**
     * 是否置顶
     */
    @ExcelProperty(value = "是否置顶", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_yes_no")
    private Integer isTop;
}
