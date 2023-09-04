package com.fzshuai.blog.mapper;

import com.fzshuai.blog.domain.Category;
import com.fzshuai.blog.domain.dto.CategoryDTO;
import com.fzshuai.blog.domain.vo.CategoryVo;
import com.fzshuai.common.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 文章分类Mapper接口
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public interface CategoryMapper extends BaseMapperPlus<CategoryMapper, Category, CategoryVo> {

    /**
     * 查询分类和对应文章数量
     *
     * @return 分类列表
     */
    List<CategoryDTO> listCategoryDTO();

    /**
     * 根据id查询分类名称
     */
    String selectNameById(Long id);


    /**
     * 根据名称查分类ID
     */
    Long selectIdByName(String name);
}
