package top.fzshuai.weblog.strategy.config;

import top.fzshuai.weblog.enums.SearchModeEnum;
import top.fzshuai.weblog.strategy.SearchStrategy;
import top.fzshuai.weblog.strategy.impl.EsSearchStrategyImpl;
import top.fzshuai.weblog.strategy.impl.MySqlSearchStrategyImpl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 搜索策略配置
 *
 * @author fzshuai
 * @date 2023/09/03 18:10
 * @since 1.0
 */
public class SearchStrategyConfig {

    protected static Map<String, SearchStrategy> searchStrategyMap = new ConcurrentHashMap<>();

    @Resource
    private MySqlSearchStrategyImpl mySqlSearchStrategy;

    @Resource
    private EsSearchStrategyImpl esSearchStrategy;

    @PostConstruct
    public void init() {
        searchStrategyMap.put(SearchModeEnum.MYSQL.getMode(), mySqlSearchStrategy);
        searchStrategyMap.put(SearchModeEnum.ELASTICSEARCH.getMode(), esSearchStrategy);
    }

}
