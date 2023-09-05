package com.fzshuai.blog.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.fzshuai.blog.domain.bo.TagBO;
import com.fzshuai.blog.domain.dto.TagDTO;
import com.fzshuai.blog.domain.vo.PageResultVO;
import com.fzshuai.blog.domain.vo.TagVO;
import com.fzshuai.blog.service.ITagService;
import com.fzshuai.common.annotation.Log;
import com.fzshuai.common.annotation.RepeatSubmit;
import com.fzshuai.common.core.controller.BaseController;
import com.fzshuai.common.core.domain.PageQuery;
import com.fzshuai.common.core.domain.R;
import com.fzshuai.common.core.page.TableDataInfo;
import com.fzshuai.common.core.validate.AddGroup;
import com.fzshuai.common.core.validate.EditGroup;
import com.fzshuai.common.enums.BusinessType;
import com.fzshuai.common.utils.poi.ExcelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * 文章标签
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/blog/tag")
public class TagController extends BaseController {

    private final ITagService tagService;

    /**
     * 查询标签列表
     *
     * @return {@link R<TagDTO>} 标签列表
     */
    @GetMapping("/tags")
    public R<PageResultVO<TagDTO>> listTags() {
        return R.ok(tagService.listTags());
    }

    /**
     * 查询文章标签列表
     */
    @SaCheckPermission("blog:tag:list")
    @GetMapping("/list")
    public TableDataInfo<TagVO> list(TagBO bo, PageQuery pageQuery) {
        return tagService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出文章标签列表
     */
    @SaCheckPermission("blog:tag:export")
    @Log(title = "文章标签", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TagBO bo, HttpServletResponse response) {
        List<TagVO> list = tagService.queryList(bo);
        ExcelUtil.exportExcel(list, "文章标签", TagVO.class, response);
    }

    /**
     * 获取文章标签详细信息
     *
     * @param tagId 主键
     */
    @SaCheckPermission("blog:tag:query")
    @GetMapping("/{tagId}")
    public R<TagVO> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long tagId) {
        return R.ok(tagService.queryById(tagId));
    }

    /**
     * 新增文章标签
     */
    @SaCheckPermission("blog:tag:add")
    @Log(title = "文章标签", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TagBO bo) {
        return toAjax(tagService.insertByBo(bo));
    }

    /**
     * 修改文章标签
     */
    @SaCheckPermission("blog:tag:edit")
    @Log(title = "文章标签", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TagBO bo) {
        return toAjax(tagService.updateByBo(bo));
    }

    /**
     * 删除文章标签
     *
     * @param tagIds 主键串
     */
    @SaCheckPermission("blog:tag:remove")
    @Log(title = "文章标签", businessType = BusinessType.DELETE)
    @DeleteMapping("/{tagIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] tagIds) {
        return toAjax(tagService.deleteWithValidByIds(Arrays.asList(tagIds), true));
    }
}
