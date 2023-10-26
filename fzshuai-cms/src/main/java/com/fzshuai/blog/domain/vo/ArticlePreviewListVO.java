package com.fzshuai.blog.domain.vo;

import com.fzshuai.blog.domain.dto.ArticlePreviewDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * 文章预览列表
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePreviewListVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章列表
     */
    private List<ArticlePreviewDTO> articlePreviewDTOList;

    /**
     * 条件名
     */
    private String name;
}
