package top.fzshuai.blog.strategy;

import top.fzshuai.blog.domain.vo.ArticleSearchVO;
import java.util.List;

/**
 * 搜索策略
 *
 * @author fzshuai
 * @date 2022/05/05 16:54
 * @since 1.0
 */
public interface SearchStrategy {

    /**
     * 搜索文章
     *
     * @param keywords 关键字
     * @return 文章列表
     */
    List<ArticleSearchVO> searchArticle(String keywords);

}
