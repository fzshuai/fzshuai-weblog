package top.fzshuai.blog.service;

import top.fzshuai.blog.domain.dto.CategoryDTO;
import top.fzshuai.blog.domain.vo.CategoryVO;
import top.fzshuai.blog.domain.bo.CategoryBO;
import top.fzshuai.blog.domain.vo.PageResultVO;
import top.fzshuai.common.core.page.TableDataInfo;
import top.fzshuai.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 文章分类Service接口
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public interface ICategoryService {

    /**
     * 查询前台分类列表
     *
     * @return 分类列表
     */
    PageResultVO<CategoryDTO> selectCategoryList();

    /**
     * 查询文章分类
     */
    CategoryVO selectCategoryById(Long categoryId);

    /**
     * 查询文章分类列表
     */
    TableDataInfo<CategoryVO> selectCategoryList(CategoryBO bo, PageQuery pageQuery);

    /**
     * 查询文章分类列表
     */
    List<CategoryVO> selectCategoryList(CategoryBO bo);

    /**
     * 新增文章分类
     */
    Boolean insertByBo(CategoryBO bo);

    /**
     * 修改文章分类
     */
    Boolean updateByBo(CategoryBO bo);

    /**
     * 校验并批量删除文章分类信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
