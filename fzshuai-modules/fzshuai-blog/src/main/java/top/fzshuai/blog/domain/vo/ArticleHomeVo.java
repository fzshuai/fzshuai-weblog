package top.fzshuai.blog.domain.vo;

import lombok.Data;
import top.fzshuai.blog.domain.dto.TagDto;
import top.fzshuai.common.annotation.Translation;
import top.fzshuai.common.constant.TransConstant;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 首页文章
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
public class ArticleHomeVo implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 内容
     */
    private String articleContent;

    /**
     * 发表时间
     */
    private LocalDateTime createTime;

    /**
     * 是否置顶
     */
    private Integer isTop;

    /**
     * 文章类型
     */
    private Integer type;

    /**
     * 文章分类id
     */
    private Long categoryId;

    /**
     * 文章分类名
     */
    private String categoryName;

    /**
     * 文章标签
     */
    private List<TagDto> tagDtoList;

}
