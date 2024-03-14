package top.fzshuai.blog.strategy.context;


import top.fzshuai.blog.domain.vo.ArticleSearchVO;
import top.fzshuai.blog.strategy.config.SearchStrategyConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 搜索策略上下文
 *
 * @author fzshuai
 * @date 2022/05/05 16:54
 * @since 1.0
 */
@Service
public class SearchStrategyContext extends SearchStrategyConfig {

    /**
     * 搜索模式
     */
    @Value("${search.mode}")
    private String searchMode;

    /**
     * 执行搜索策略
     *
     * @param keywords 关键字
     * @return 文章列表
     */
    public List<ArticleSearchVO> executeSearchStrategy(String keywords) {
        return searchStrategyMap.get(searchMode).searchArticle(keywords);
    }
}
