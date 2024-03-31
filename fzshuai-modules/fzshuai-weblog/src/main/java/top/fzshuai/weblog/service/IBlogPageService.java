package top.fzshuai.weblog.service;

import top.fzshuai.weblog.domain.vo.PageVo;
import top.fzshuai.weblog.domain.bo.PageBo;
import top.fzshuai.common.core.page.TableDataInfo;
import top.fzshuai.common.core.domain.PageQuery;

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
    PageVo selectPageById(Long pageId);

    /**
     * 查询博客页面列表
     */
    TableDataInfo<PageVo> selectPageList(PageBo bo, PageQuery pageQuery);

    /**
     * 查询博客页面列表
     */
    List<PageVo> selectPageList(PageBo bo);

    /**
     * 新增博客页面
     */
    Boolean insertByBo(PageBo bo);

    /**
     * 修改博客页面
     */
    Boolean updateByBo(PageBo bo);

    /**
     * 校验并批量删除博客页面信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
