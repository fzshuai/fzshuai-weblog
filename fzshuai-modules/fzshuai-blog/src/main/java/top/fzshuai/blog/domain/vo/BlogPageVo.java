package top.fzshuai.blog.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import top.fzshuai.common.annotation.Translation;
import top.fzshuai.common.constant.TransConstant;
import lombok.Data;

import java.io.Serializable;


/**
 * 页面视图对象 blog_page
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@ExcelIgnoreUnannotated
public class BlogPageVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long pageId;

    /**
     * 页面名
     */
    @ExcelProperty(value = "页面名")
    private String pageName;

    /**
     * 页面标签
     */
    @ExcelProperty(value = "页面标签")
    private String pageLabel;

    /**
     * 页面封面Url对应Id
     */
    @ExcelProperty(value = "页面封面")
    private String pageCover;

    /**
     * 页面封面Url
     */
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "pageCover")
    private String pageCoverUrl;

}
