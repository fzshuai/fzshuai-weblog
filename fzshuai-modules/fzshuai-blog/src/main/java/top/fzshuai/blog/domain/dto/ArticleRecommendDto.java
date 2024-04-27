package top.fzshuai.blog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.fzshuai.common.annotation.Translation;
import top.fzshuai.common.constant.TransConstant;

import java.time.LocalDateTime;

/**
 * 推荐文章
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleRecommendDto {

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

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
