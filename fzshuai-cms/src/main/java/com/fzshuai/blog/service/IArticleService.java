package com.fzshuai.blog.service;

import com.fzshuai.blog.domain.dto.*;
import com.fzshuai.blog.domain.vo.ArticleVo;
import com.fzshuai.blog.domain.bo.ArticleBo;
import com.fzshuai.blog.domain.vo.ConditionVo;
import com.fzshuai.blog.domain.vo.PageResult;
import com.fzshuai.common.core.page.TableDataInfo;
import com.fzshuai.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 文章Service接口
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public interface IArticleService {

    /**
     * 点赞文章
     *
     * @param articleId 文章id
     */
    void saveArticleLike(Long articleId);

    /**
     * 查询文章归档
     *
     * @return 文章归档
     */
    PageResult<ArchiveDTO> listArchives();

    /**
     * 查看博客前端首页文章
     *
     * @return 首页文章
     */
    List<ArticleHomeDTO> listArticles();

    /**
     * 博客前端根据id查看文章
     *
     * @param articleId 文章id
     * @return {@link ArticleDTO} 文章信息
     */
    ArticleDTO getArticleById(Long articleId);

    /**
     * 搜索文章
     *
     * @param condition 条件
     * @return 文章列表
     */
    List<ArticleSearchDTO> listArticlesBySearch(ConditionVo condition);

    /**
     * 根据条件查询文章列表
     *
     * @param condition 条件
     * @return 文章列表
     */
    ArticlePreviewListDTO listArticlesByCondition(ConditionVo condition);

    /**
     * 查询文章
     */
    ArticleVo queryById(Long articleId);

    /**
     * 查询文章列表
     */
    TableDataInfo<ArticleVo> queryPageList(ArticleBo bo, PageQuery pageQuery);

    /**
     * 查询文章列表
     */
    List<ArticleVo> queryList(ArticleBo bo);

    /**
     * 新增文章
     */
    Boolean insertByBo(ArticleBo bo);

    /**
     * 修改文章
     */
    Boolean updateByBo(ArticleBo bo);

    /**
     * 校验并批量删除文章信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
