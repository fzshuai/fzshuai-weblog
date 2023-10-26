package com.fzshuai.blog.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 照片视图对象 blog_photo
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@ExcelIgnoreUnannotated
public class PhotoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long photoId;

    /**
     * 相册id
     */
    @ExcelProperty(value = "相册id")
    private Long albumId;

    /**
     * 照片名
     */
    @ExcelProperty(value = "照片名")
    private String photoName;

    /**
     * 照片描述
     */
    @ExcelProperty(value = "照片描述")
    private String photoDesc;

    /**
     * 照片地址
     */
    @ExcelProperty(value = "照片地址")
    private String photoSrc;

    /**
     * 是否删除
     */
    @ExcelProperty(value = "是否删除")
    private Integer isDelete;
}
