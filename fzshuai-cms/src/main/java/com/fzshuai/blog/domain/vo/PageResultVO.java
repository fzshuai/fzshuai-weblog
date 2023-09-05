package com.fzshuai.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页对象
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResultVO<T> {

    /**
     * 分页列表
     */
    private List<T> recordList;

    /**
     * 总数
     */
    private Integer count;
}
