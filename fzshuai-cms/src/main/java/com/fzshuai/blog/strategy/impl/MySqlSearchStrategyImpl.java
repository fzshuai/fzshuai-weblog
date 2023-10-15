package com.fzshuai.blog.strategy.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import com.fzshuai.blog.domain.Article;
import com.fzshuai.blog.domain.vo.ArticleSearchVO;
import com.fzshuai.blog.mapper.ArticleMapper;
import com.fzshuai.blog.strategy.SearchStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.fzshuai.common.constant.BlogConstant.*;
import static com.fzshuai.blog.enums.ArticleStatusEnum.PUBLIC;

/**
 * mysql搜索策略
 *
 * @author fzshuai
 * @date 2022/05/05 16:54
 * @since 1.0
 */
@RequiredArgsConstructor
@Service("mySQLSearchStrategy")
public class MySqlSearchStrategyImpl implements SearchStrategy {

    private final ArticleMapper articleMapper;

    @Override
    public List<ArticleSearchVO> searchArticle(String keywords) {
        // 判空
        if (StringUtils.isBlank(keywords)) {
            return new ArrayList<>();
        }
        // 搜索文章
        List<Article> articleList = articleMapper.selectList(new LambdaQueryWrapper<Article>()
                .eq(Article::getIsDelete, FALSE)
                .eq(Article::getStatus, PUBLIC.getStatus())
                .and(article -> article.like(Article::getArticleTitle, keywords)
                        .or()
                        .like(Article::getArticleContent, keywords)));
        // 高亮处理
        return articleList.stream().map(article -> {
            // 获取关键词第一次出现的位置
            String articleContent = article.getArticleContent();
            int index = article.getArticleContent().indexOf(keywords);
            if (index != -1) {
                // 获取关键词前面的文字
                int preIndex = index > 25 ? index - 25 : 0;
                String preText = article.getArticleContent().substring(preIndex, index);
                // 获取关键词到后面的文字
                int last = index + keywords.length();
                int postLength = article.getArticleContent().length() - last;
                int postIndex = postLength > 175 ? last + 175 : last + postLength;
                String postText = article.getArticleContent().substring(index, postIndex);
                // 文章内容高亮
                articleContent = (preText + postText).replaceAll(keywords, PRE_TAG + keywords + POST_TAG);
            }
            // 文章标题高亮
            String articleTitle = article.getArticleTitle().replaceAll(keywords, PRE_TAG + keywords + POST_TAG);
            return ArticleSearchVO.builder()
                    .articleId(article.getArticleId())
                    .articleTitle(articleTitle)
                    .articleContent(articleContent)
                    .build();
        }).collect(Collectors.toList());
    }

}
