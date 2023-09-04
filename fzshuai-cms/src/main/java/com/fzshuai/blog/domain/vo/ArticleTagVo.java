package com.fzshuai.blog.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fzshuai.common.annotation.ExcelDictFormat;
import com.fzshuai.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 文章和文章标签关联视图对象 blog_article_tag
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@ExcelIgnoreUnannotated
public class ArticleTagVo {

    private static final long serialVersionUID = 1L;

    /**
     * 文章id
     */
    @ExcelProperty(value = "文章id")
    private Long articleId;

    /**
     * 标签id
     */
    @ExcelProperty(value = "标签id")
    private Long tagId;
}