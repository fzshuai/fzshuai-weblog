package top.fzshuai.blog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.fzshuai.common.annotation.Translation;
import top.fzshuai.common.constant.TransConstant;

/**
 * 文章上下篇
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticlePaginationDto {

    /**
     * id
     */
    private Long articleId;

    /**
     * 文章缩略图
     */
    private String articleCover;

    /**
     * 文章缩略图url
     */
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "articleCover")
    private String articleCoverUrl;

    /**
     * 标题
     */
    private String articleTitle;

}
