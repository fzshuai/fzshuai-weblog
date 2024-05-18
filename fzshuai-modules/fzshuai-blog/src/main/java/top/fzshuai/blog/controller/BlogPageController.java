package top.fzshuai.blog.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.fzshuai.blog.domain.bo.BlogPageBo;
import top.fzshuai.blog.domain.vo.BlogPageVo;
import top.fzshuai.blog.service.IBlogPageService;
import top.fzshuai.common.annotation.Log;
import top.fzshuai.common.annotation.RepeatSubmit;
import top.fzshuai.common.core.controller.BaseController;
import top.fzshuai.common.core.domain.PageQuery;
import top.fzshuai.common.core.domain.R;
import top.fzshuai.common.core.page.TableDataInfo;
import top.fzshuai.common.core.validate.AddGroup;
import top.fzshuai.common.core.validate.EditGroup;
import top.fzshuai.common.enums.BusinessType;
import top.fzshuai.common.utils.poi.ExcelUtil;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

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
public class BlogPageController extends BaseController {

    private final IBlogPageService blogPageService;

    /**
     * 查询页面列表
     */
    @SaCheckPermission("blog:page:list")
    @GetMapping("/list")
    public TableDataInfo<BlogPageVo> list(BlogPageBo bo, PageQuery pageQuery) {
        return blogPageService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出页面列表
     */
    @SaCheckPermission("blog:page:export")
    @Log(title = "页面", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(BlogPageBo bo, HttpServletResponse response) {
        List<BlogPageVo> list = blogPageService.queryPageList(bo);
        ExcelUtil.exportExcel(list, "页面", BlogPageVo.class, response);
    }

    /**
     * 获取页面详细信息
     *
     * @param pageId 主键
     */
    @SaCheckPermission("blog:page:query")
    @GetMapping("/{pageId}")
    public R<BlogPageVo> getInfo(@NotNull(message = "主键不能为空")
                             @PathVariable Long pageId) {
        return R.ok(blogPageService.queryPageById(pageId));
    }

    /**
     * 新增页面
     */
    @SaCheckPermission("blog:page:add")
    @Log(title = "页面", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody BlogPageBo bo) {
        return toAjax(blogPageService.insertByBo(bo));
    }

    /**
     * 修改页面
     */
    @SaCheckPermission("blog:page:edit")
    @Log(title = "页面", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody BlogPageBo bo) {
        return toAjax(blogPageService.updateByBo(bo));
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
        return toAjax(blogPageService.deleteWithValidByIds(Arrays.asList(pageIds), true));
    }

}
