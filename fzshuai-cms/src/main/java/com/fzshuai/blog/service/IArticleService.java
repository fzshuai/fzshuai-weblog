package com.fzshuai.blog.service;

import com.fzshuai.blog.domain.dto.*;
import com.fzshuai.blog.domain.vo.ArticleVO;
import com.fzshuai.blog.domain.bo.ArticleBO;
import com.fzshuai.blog.domain.vo.ConditionVO;
import com.fzshuai.blog.domain.vo.PageResultVO;
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
    PageResultVO<ArchiveDTO> listArchives();

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
    List<ArticleSearchDTO> listArticlesBySearch(ConditionVO condition);

    /**
     * 根据条件查询文章列表
     *
     * @param condition 条件
     * @return 文章列表
     */
    ArticlePreviewListDTO listArticlesByCondition(ConditionVO condition);

    /**
     * 查询文章
     */
    ArticleVO queryById(Long articleId);

    /**
     * 查询文章列表
     */
    TableDataInfo<ArticleVO> queryPageList(ArticleBO bo, PageQuery pageQuery);

    /**
     * 查询文章列表
     */
    List<ArticleVO> queryList(ArticleBO bo);

    /**
     * 新增文章
     */
    Boolean insertByBo(ArticleBO bo);

    /**
     * 修改文章
     */
    Boolean updateByBo(ArticleBO bo);

    /**
     * 校验并批量删除文章信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
