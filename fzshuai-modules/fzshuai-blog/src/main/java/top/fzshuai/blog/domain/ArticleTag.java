package top.fzshuai.blog.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import top.fzshuai.common.core.domain.BaseEntity;

/**
 * 文章和文章标签关联对象 blog_article_tag
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@TableName("blog_article_tag")
public class ArticleTag extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 文章id
     */
    @TableId(value = "article_id")
    private Long articleId;

    /**
     * 标签id
     */
    private Long tagId;

}
