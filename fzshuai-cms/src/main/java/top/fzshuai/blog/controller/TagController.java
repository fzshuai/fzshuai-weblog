package top.fzshuai.blog.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import top.fzshuai.blog.domain.bo.TagBO;
import top.fzshuai.blog.domain.dto.TagDTO;
import top.fzshuai.blog.domain.vo.PageResultVO;
import top.fzshuai.blog.domain.vo.TagVO;
import top.fzshuai.blog.service.ITagService;
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
     * @return {@link R< TagDTO >} 标签列表
     */
    @GetMapping("/tags")
    public R<PageResultVO<TagDTO>> listTags() {
        return R.ok(tagService.selectTagList());
    }

    /**
     * 查询文章标签列表
     */
    @SaCheckPermission("blog:tag:list")
    @GetMapping("/list")
    public TableDataInfo<TagVO> list(TagBO bo, PageQuery pageQuery) {
        return tagService.selectTagPageList(bo, pageQuery);
    }

    /**
     * 导出文章标签列表
     */
    @SaCheckPermission("blog:tag:export")
    @Log(title = "文章标签", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TagBO bo, HttpServletResponse response) {
        List<TagVO> list = tagService.selectTagList(bo);
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
        return R.ok(tagService.selectTagById(tagId));
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
