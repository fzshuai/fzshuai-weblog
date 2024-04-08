package top.fzshuai.blog.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import top.fzshuai.common.core.domain.BaseEntity;

/**
 * 访问量对象 blog_unique_view
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("blog_unique_view")
public class UniqueView extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "unique_view_id")
    private Long uniqueViewId;
    /**
     * 访问量
     */
    private Long viewsCount;

}
