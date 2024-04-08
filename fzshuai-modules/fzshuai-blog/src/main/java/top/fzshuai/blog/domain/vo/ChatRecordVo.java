package top.fzshuai.blog.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 聊天记录视图对象 blog_chat_record
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@ExcelIgnoreUnannotated
public class ChatRecordVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long chatRecordId;

    /**
     * 用户id
     */
    @ExcelProperty(value = "用户id")
    private Long userId;

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
     * 聊天内容
     */
    @ExcelProperty(value = "聊天内容")
    private String content;

    /**
     * ip地址
     */
    @ExcelProperty(value = "ip地址")
    private String ipAddress;

    /**
     * ip来源
     */
    @ExcelProperty(value = "ip来源")
    private String ipSource;

    /**
     * 类型
     */
    @ExcelProperty(value = "类型")
    private Integer type;

}
