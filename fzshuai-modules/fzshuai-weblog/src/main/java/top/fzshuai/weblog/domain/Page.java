package top.fzshuai.weblog.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import top.fzshuai.common.core.domain.BaseEntity;

/**
 * 页面对象 blog_page
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("blog_page")
public class Page extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "page_id")
    private Long pageId;
    /**
     * 页面名
     */
    private String pageName;
    /**
     * 页面标签
     */
    private String pageLabel;
    /**
     * 页面封面
     */
    private String pageCover;

}
