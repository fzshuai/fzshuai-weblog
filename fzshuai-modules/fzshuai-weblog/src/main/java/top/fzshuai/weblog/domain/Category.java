package top.fzshuai.weblog.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import top.fzshuai.common.core.domain.BaseEntity;

/**
 * 文章分类对象 blog_category
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@TableName("blog_category")
public class Category extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "category_id")
    private Long categoryId;

    /**
     * 分类名
     */
    private String categoryName;

}
