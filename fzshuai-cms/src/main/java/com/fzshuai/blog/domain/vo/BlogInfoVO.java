package com.fzshuai.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 博客信息
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
public class BlogInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 关于我内容
     */
    private String aboutContent;
}
