package top.fzshuai.weblog.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 访问量视图对象 blog_unique_view
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@ExcelIgnoreUnannotated
public class UniqueViewVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long uniqueViewId;

    /**
     * 访问量
     */
    @ExcelProperty(value = "访问量")
    private Integer viewsCount;

}
