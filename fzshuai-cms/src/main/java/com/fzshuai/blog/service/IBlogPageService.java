package com.fzshuai.blog.service;

import com.fzshuai.blog.domain.vo.PageVO;
import com.fzshuai.blog.domain.bo.PageBO;
import com.fzshuai.common.core.page.TableDataInfo;
import com.fzshuai.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 博客页面Service接口
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public interface IBlogPageService {

    /**
     * 查询博客页面
     */
    PageVO selectPageById(Long pageId);

    /**
     * 查询博客页面列表
     */
    TableDataInfo<PageVO> selectPageList(PageBO bo, PageQuery pageQuery);

    /**
     * 查询博客页面列表
     */
    List<PageVO> selectPageList(PageBO bo);

    /**
     * 新增博客页面
     */
    Boolean insertByBo(PageBO bo);

    /**
     * 修改博客页面
     */
    Boolean updateByBo(PageBO bo);

    /**
     * 校验并批量删除博客页面信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
