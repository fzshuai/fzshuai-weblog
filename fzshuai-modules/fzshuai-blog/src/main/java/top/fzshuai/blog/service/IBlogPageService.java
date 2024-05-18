package top.fzshuai.blog.service;

import top.fzshuai.blog.domain.vo.BlogPageVo;
import top.fzshuai.blog.domain.bo.BlogPageBo;
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
    BlogPageVo queryPageById(Long pageId);

    /**
     * 查询博客页面列表
     */
    TableDataInfo<BlogPageVo> queryPageList(BlogPageBo bo, PageQuery pageQuery);

    /**
     * 查询博客页面列表
     */
    List<BlogPageVo> queryPageList(BlogPageBo bo);

    /**
     * 新增博客页面
     */
    Boolean insertByBo(BlogPageBo bo);

    /**
     * 修改博客页面
     */
    Boolean updateByBo(BlogPageBo bo);

    /**
     * 校验并批量删除博客页面信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
