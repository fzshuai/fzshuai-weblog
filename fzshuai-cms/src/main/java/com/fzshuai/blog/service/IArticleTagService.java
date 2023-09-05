package com.fzshuai.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzshuai.blog.domain.ArticleTag;
import com.fzshuai.blog.domain.vo.ArticleTagVO;
import com.fzshuai.blog.domain.bo.ArticleTagBO;
import com.fzshuai.common.core.page.TableDataInfo;
import com.fzshuai.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 文章和文章标签关联Service接口
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public interface IArticleTagService extends IService<ArticleTag> {

    /**
     * 查询文章和文章标签关联
     */
    ArticleTagVO queryById(Long articleId);

    /**
     * 查询文章和文章标签关联列表
     */
    TableDataInfo<ArticleTagVO> queryPageList(ArticleTagBO bo, PageQuery pageQuery);

    /**
     * 查询文章和文章标签关联列表
     */
    List<ArticleTagVO> queryList(ArticleTagBO bo);

    /**
     * 新增文章和文章标签关联
     */
    Boolean insertByBo(ArticleTagBO bo);

    /**
     * 修改文章和文章标签关联
     */
    Boolean updateByBo(ArticleTagBO bo);

    /**
     * 校验并批量删除文章和文章标签关联信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
