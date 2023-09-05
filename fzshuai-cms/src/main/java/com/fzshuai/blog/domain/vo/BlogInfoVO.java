package com.fzshuai.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 博客信息
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogInfoVO {

    /**
     * 关于我内容
     */
    private String aboutContent;
}
