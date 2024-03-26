package top.fzshuai.blog.controller;

import java.util.List;
import java.util.Arrays;

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
import top.fzshuai.blog.domain.vo.PageVo;
import top.fzshuai.blog.domain.bo.PageBo;
import top.fzshuai.blog.service.IBlogPageService;
import top.fzshuai.common.core.page.TableDataInfo;

/**
 * 博客页面
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/blog/page")
public class PageController extends BaseController {

    private final IBlogPageService iBlogPageService;

    /**
     * 查询页面列表
     */
    @SaCheckPermission("blog:page:list")
    @GetMapping("/list")
    public TableDataInfo<PageVo> list(PageBo bo, PageQuery pageQuery) {
        return iBlogPageService.selectPageList(bo, pageQuery);
    }

    /**
     * 导出页面列表
     */
    @SaCheckPermission("blog:page:export")
    @Log(title = "页面", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(PageBo bo, HttpServletResponse response) {
        List<PageVo> list = iBlogPageService.selectPageList(bo);
        ExcelUtil.exportExcel(list, "页面", PageVo.class, response);
    }

    /**
     * 获取页面详细信息
     *
     * @param pageId 主键
     */
    @SaCheckPermission("blog:page:query")
    @GetMapping("/{pageId}")
    public R<PageVo> getInfo(@NotNull(message = "主键不能为空")
                             @PathVariable Long pageId) {
        return R.ok(iBlogPageService.selectPageById(pageId));
    }

    /**
     * 新增页面
     */
    @SaCheckPermission("blog:page:add")
    @Log(title = "页面", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody PageBo bo) {
        return toAjax(iBlogPageService.insertByBo(bo));
    }

    /**
     * 修改页面
     */
    @SaCheckPermission("blog:page:edit")
    @Log(title = "页面", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody PageBo bo) {
        return toAjax(iBlogPageService.updateByBo(bo));
    }

    /**
     * 删除页面
     *
     * @param pageIds 主键串
     */
    @SaCheckPermission("blog:page:remove")
    @Log(title = "页面", businessType = BusinessType.DELETE)
    @DeleteMapping("/{pageIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] pageIds) {
        return toAjax(iBlogPageService.deleteWithValidByIds(Arrays.asList(pageIds), true));
    }
}
