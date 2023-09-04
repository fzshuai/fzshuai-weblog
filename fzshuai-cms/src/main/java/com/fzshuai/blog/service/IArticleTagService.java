package com.fzshuai.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzshuai.blog.domain.ArticleTag;
import com.fzshuai.blog.domain.vo.ArticleTagVo;
import com.fzshuai.blog.domain.bo.ArticleTagBo;
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
    ArticleTagVo queryById(Long articleId);

    /**
     * 查询文章和文章标签关联列表
     */
    TableDataInfo<ArticleTagVo> queryPageList(ArticleTagBo bo, PageQuery pageQuery);

    /**
     * 查询文章和文章标签关联列表
     */
    List<ArticleTagVo> queryList(ArticleTagBo bo);

    /**
     * 新增文章和文章标签关联
     */
    Boolean insertByBo(ArticleTagBo bo);

    /**
     * 修改文章和文章标签关联
     */
    Boolean updateByBo(ArticleTagBo bo);

    /**
     * 校验并批量删除文章和文章标签关联信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
