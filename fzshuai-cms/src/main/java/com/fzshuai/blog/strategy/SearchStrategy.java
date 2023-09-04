package com.fzshuai.blog.strategy;


import com.fzshuai.blog.domain.dto.ArticleSearchDTO;

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
    List<ArticleSearchDTO> searchArticle(String keywords);

}