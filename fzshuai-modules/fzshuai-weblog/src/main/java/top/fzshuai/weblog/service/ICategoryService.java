package top.fzshuai.weblog.service;

import top.fzshuai.weblog.domain.dto.CategoryDto;
import top.fzshuai.weblog.domain.vo.CategoryVo;
import top.fzshuai.weblog.domain.bo.CategoryBo;
import top.fzshuai.weblog.domain.vo.PageResultVo;
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
    PageResultVo<CategoryDto> selectCategoryList();

    /**
     * 查询文章分类
     */
    CategoryVo selectCategoryById(Long categoryId);

    /**
     * 查询文章分类列表
     */
    TableDataInfo<CategoryVo> selectCategoryList(CategoryBo bo, PageQuery pageQuery);

    /**
     * 查询文章分类列表
     */
    List<CategoryVo> selectCategoryList(CategoryBo bo);

    /**
     * 新增文章分类
     */
    Boolean insertByBo(CategoryBo bo);

    /**
     * 修改文章分类
     */
    Boolean updateByBo(CategoryBo bo);

    /**
     * 校验并批量删除文章分类信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
