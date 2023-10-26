package com.fzshuai.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 归档文章
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
public class ArchiveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long articleId;

    /**
     * 标题
     */
    private String articleTitle;

    /**
     * 发表时间
     */
    private LocalDateTime createTime;
}
