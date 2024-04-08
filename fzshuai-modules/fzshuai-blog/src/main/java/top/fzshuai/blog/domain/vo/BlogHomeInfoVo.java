package top.fzshuai.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author ftz-lover
 * @version 1.0
 * @date 2023/1/12 15:45
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogHomeInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章数量
     */
    private Integer articleCount;

    /**
     * 分类数量
     */
    private Integer categoryCount;

    /**
     * 标签数量
     */
    private Integer tagCount;

    /**
     * 访问量
     */
    private String viewsCount;

    /**
     * 网站配置
     */
    private WebsiteConfigVo websiteConfig;

    /**
     * 页面列表
     */
    private List<PageVo> pageList;

}
