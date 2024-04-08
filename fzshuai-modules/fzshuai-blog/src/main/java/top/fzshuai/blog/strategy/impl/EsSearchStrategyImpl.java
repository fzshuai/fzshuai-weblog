package top.fzshuai.blog.strategy.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import top.fzshuai.blog.domain.vo.ArticleSearchVo;
import top.fzshuai.blog.strategy.SearchStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static top.fzshuai.blog.enums.PhotoAlbumStatusEnum.PUBLIC;
import static top.fzshuai.common.constant.BlogConstant.*;

/**
 * es搜索策略实现
 *
 * @author fzshuai
 * @date 2022/05/05 16:54
 * @since 1.0
 */
@Log4j2
@RequiredArgsConstructor
@Service("esSearchStrategy")
public class EsSearchStrategyImpl implements SearchStrategy {

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public List<ArticleSearchVo> searchArticle(String keywords) {
        if (StringUtils.isBlank(keywords)) {
            return new ArrayList<>();
        }
        return search(buildQuery(keywords));
    }

    /**
     * 搜索文章构造
     *
     * @param keywords 关键字
     * @return es条件构造器
     */
    private NativeSearchQueryBuilder buildQuery(String keywords) {
        // 条件构造器
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 根据关键词搜索文章标题或内容
        boolQueryBuilder.must(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("articleTitle", keywords))
                .should(QueryBuilders.matchQuery("articleContent", keywords)))
            .must(QueryBuilders.termQuery("isDelete", FALSE))
            .must(QueryBuilders.termQuery("status", PUBLIC.getStatus()));
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        return nativeSearchQueryBuilder;
    }

    /**
     * 文章搜索结果高亮
     *
     * @param nativeSearchQueryBuilder es条件构造器
     * @return 搜索结果
     */
    private List<ArticleSearchVo> search(NativeSearchQueryBuilder nativeSearchQueryBuilder) {
        // 添加文章标题高亮
        HighlightBuilder.Field titleField = new HighlightBuilder.Field("articleTitle");
        titleField.preTags(PRE_TAG);
        titleField.postTags(POST_TAG);
        // 添加文章内容高亮
        HighlightBuilder.Field contentField = new HighlightBuilder.Field("articleContent");
        contentField.preTags(PRE_TAG);
        contentField.postTags(POST_TAG);
        contentField.fragmentSize(200);
        nativeSearchQueryBuilder.withHighlightFields(titleField, contentField);
        // 搜索
        try {
            SearchHits<ArticleSearchVo> search = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), ArticleSearchVo.class);
            return search.getSearchHits().stream().map(hit -> {
                ArticleSearchVo article = hit.getContent();
                // 获取文章标题高亮数据
                List<String> titleHighLightList = hit.getHighlightFields().get("articleTitle");
                if (CollectionUtils.isNotEmpty(titleHighLightList)) {
                    // 替换标题数据
                    article.setArticleTitle(titleHighLightList.get(0));
                }
                // 获取文章内容高亮数据
                List<String> contentHighLightList = hit.getHighlightFields().get("articleContent");
                if (CollectionUtils.isNotEmpty(contentHighLightList)) {
                    // 替换内容数据
                    article.setArticleContent(contentHighLightList.get(contentHighLightList.size() - 1));
                }
                return article;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ArrayList<>();
    }

}
