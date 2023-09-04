package com.fzshuai.blog.controller;

import java.util.List;
import java.util.Arrays;

import com.fzshuai.blog.domain.dto.CategoryDTO;
import com.fzshuai.blog.domain.vo.PageResult;
import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.fzshuai.common.annotation.RepeatSubmit;
import com.fzshuai.common.annotation.Log;
import com.fzshuai.common.core.controller.BaseController;
import com.fzshuai.common.core.domain.PageQuery;
import com.fzshuai.common.core.domain.R;
import com.fzshuai.common.core.validate.AddGroup;
import com.fzshuai.common.core.validate.EditGroup;
import com.fzshuai.common.enums.BusinessType;
import com.fzshuai.common.utils.poi.ExcelUtil;
import com.fzshuai.blog.domain.vo.CategoryVo;
import com.fzshuai.blog.domain.bo.CategoryBo;
import com.fzshuai.blog.service.ICategoryService;
import com.fzshuai.common.core.page.TableDataInfo;

/**
 * 文章分类
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/blog/category")
public class CategoryController extends BaseController {

    private final ICategoryService categoryService;

    /**
     * 查看分类列表
     *
     * @return {@link R<CategoryDTO>} 分类列表
     */
    @GetMapping("/categories")
    public R<PageResult<CategoryDTO>> listCategories() {
        return R.ok(categoryService.listCategories());
    }

    /**
     * 查询文章分类列表
     */
    @SaCheckPermission("blog:category:list")
    @GetMapping("/list")
    public TableDataInfo<CategoryVo> list(CategoryBo bo, PageQuery pageQuery) {
        return categoryService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出文章分类列表
     */
    @SaCheckPermission("blog:category:export")
    @Log(title = "文章分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CategoryBo bo, HttpServletResponse response) {
        List<CategoryVo> list = categoryService.queryList(bo);
        ExcelUtil.exportExcel(list, "文章分类", CategoryVo.class, response);
    }

    /**
     * 获取文章分类详细信息
     *
     * @param categoryId 主键
     */
    @SaCheckPermission("blog:category:query")
    @GetMapping("/{categoryId}")
    public R<CategoryVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long categoryId) {
        return R.ok(categoryService.queryById(categoryId));
    }

    /**
     * 新增文章分类
     */
    // @SaCheckPermission("blog:category:add")
    @Log(title = "文章分类", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CategoryBo bo) {
        return toAjax(categoryService.insertByBo(bo));
    }

    /**
     * 修改文章分类
     */
    @SaCheckPermission("blog:category:edit")
    @Log(title = "文章分类", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CategoryBo bo) {
        return toAjax(categoryService.updateByBo(bo));
    }

    /**
     * 删除文章分类
     *
     * @param categoryIds 主键串
     */
    @SaCheckPermission("blog:category:remove")
    @Log(title = "文章分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{categoryIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] categoryIds) {
        return toAjax(categoryService.deleteWithValidByIds(Arrays.asList(categoryIds), true));
    }
}
