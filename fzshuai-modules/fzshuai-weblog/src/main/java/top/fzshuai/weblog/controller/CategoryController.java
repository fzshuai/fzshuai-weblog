package top.fzshuai.weblog.controller;

import java.util.List;
import java.util.Arrays;

import top.fzshuai.weblog.domain.dto.CategoryDto;
import top.fzshuai.weblog.domain.vo.PageResultVo;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;

import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import top.fzshuai.common.annotation.RepeatSubmit;
import top.fzshuai.common.annotation.Log;
import top.fzshuai.common.core.controller.BaseController;
import top.fzshuai.common.core.domain.PageQuery;
import top.fzshuai.common.core.domain.R;
import top.fzshuai.common.core.validate.AddGroup;
import top.fzshuai.common.core.validate.EditGroup;
import top.fzshuai.common.enums.BusinessType;
import top.fzshuai.common.utils.poi.ExcelUtil;
import top.fzshuai.weblog.domain.vo.CategoryVo;
import top.fzshuai.weblog.domain.bo.CategoryBo;
import top.fzshuai.weblog.service.ICategoryService;
import top.fzshuai.common.core.page.TableDataInfo;

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
     * @return 分类列表
     */
    @GetMapping("/categories")
    public R<PageResultVo<CategoryDto>> listCategories() {
        return R.ok(categoryService.selectCategoryList());
    }

    /**
     * 查询文章分类列表
     */
    @SaCheckPermission("blog:category:list")
    @GetMapping("/list")
    public TableDataInfo<CategoryVo> list(CategoryBo bo, PageQuery pageQuery) {
        return categoryService.selectCategoryList(bo, pageQuery);
    }

    /**
     * 导出文章分类列表
     */
    @SaCheckPermission("blog:category:export")
    @Log(title = "文章分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CategoryBo bo, HttpServletResponse response) {
        List<CategoryVo> list = categoryService.selectCategoryList(bo);
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
        return R.ok(categoryService.selectCategoryById(categoryId));
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
