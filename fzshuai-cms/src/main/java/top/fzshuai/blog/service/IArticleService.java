package top.fzshuai.blog.service;

import top.fzshuai.blog.domain.bo.ArticleBO;
import top.fzshuai.common.core.domain.PageQuery;
import top.fzshuai.common.core.page.TableDataInfo;
import top.fzshuai.blog.domain.vo.*;

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
    void likeArticle(Long articleId);

    /**
     * 查询文章归档
     *
     * @return 文章归档
     */
    PageResultVO<ArchiveVO> selectArticleArchiveList();

    /**
     * 查看博客前端首页文章
     *
     * @return 首页文章
     */
    List<ArticleHomeVO> selectArticleHomeList();

    /**
     * 博客前端根据id查看文章
     *
     * @param articleId 文章id
     * @return 文章信息
     */
    ArticleDetailVO selectArticleDetailById(Long articleId);

    /**
     * 搜索文章
     *
     * @param condition 条件
     * @return 文章列表
     */
    List<ArticleSearchVO> searchArticle(ConditionVO condition);

    /**
     * 根据条件查询文章列表
     *
     * @param condition 条件
     * @return 文章列表
     */
    ArticlePreviewListVO selectArticlePreviewList(ConditionVO condition);

    /**
     * 查询文章
     *
     * @param articleId 文章id
     * @return 文章
     */
    ArticleVO selectArticleById(Long articleId);

    /**
     * 查询文章列表
     *
     * @param bo        文章对象
     * @param pageQuery 分页对象
     * @return 文章列表
     */
    TableDataInfo<ArticleVO> selectArticlePageList(ArticleBO bo, PageQuery pageQuery);

    /**
     * 查询文章列表
     *
     * @param bo 文章对象
     * @return 文章列表
     */
    List<ArticleVO> selectArticleList(ArticleBO bo);

    /**
     * 新增文章
     *
     * @param bo 文章
     * @return 是否成功
     */
    Boolean insertByBo(ArticleBO bo);

    /**
     * 修改文章
     *
     * @param bo 文章
     * @return 是否成功
     */
    Boolean updateByBo(ArticleBO bo);

    /**
     * 校验并批量删除文章信息
     *
     * @param ids     文章id
     * @param isValid 是否校验
     * @return 是否成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
