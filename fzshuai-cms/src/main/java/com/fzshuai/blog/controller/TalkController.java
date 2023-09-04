package com.fzshuai.blog.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import com.fzshuai.blog.domain.bo.TalkBo;
import com.fzshuai.blog.domain.dto.TalkDTO;
import com.fzshuai.blog.domain.vo.PageResult;
import com.fzshuai.blog.domain.vo.TalkVo;
import com.fzshuai.blog.service.ITalkService;
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
 * 说说
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/blog/talk")
public class TalkController extends BaseController {

    private final ITalkService talkService;

    /**
     * 查看博客首页说说
     *
     * @return {@link R<String>}
     */
    @SaIgnore
    @GetMapping("/home/talks")
    public R<List<String>> listHomeTalks() {
        return R.ok(talkService.listHomeTalks());
    }

    /**
     * 博客前端查看说说列表
     *
     * @return {@link R<TalkDTO>}
     */
    @SaIgnore
    @GetMapping("/talks")
    public R<PageResult<TalkDTO>> listTalks() {
        return R.ok(talkService.listTalks());
    }

    /**
     * 查询说说列表
     */
    @SaCheckPermission("blog:talk:list")
    @GetMapping("/list")
    public TableDataInfo<TalkVo> list(TalkBo bo, PageQuery pageQuery) {
        return talkService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出说说列表
     */
    @SaCheckPermission("blog:talk:export")
    @Log(title = "说说", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TalkBo bo, HttpServletResponse response) {
        List<TalkVo> list = talkService.queryList(bo);
        ExcelUtil.exportExcel(list, "说说", TalkVo.class, response);
    }

    /**
     * 获取说说详细信息
     *
     * @param talkId 主键
     */
    @SaIgnore
    @SaCheckPermission("blog:talk:query")
    @GetMapping("/{talkId}")
    public R<TalkVo> getInfo(@NotNull(message = "主键不能为空")
                             @PathVariable Long talkId) {
        return R.ok(talkService.queryById(talkId));
    }

    /**
     * 新增说说
     */
    @SaCheckPermission("blog:talk:add")
    @Log(title = "说说", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TalkBo bo) {
        return toAjax(talkService.insertByBo(bo));
    }

    /**
     * 修改说说
     */
    @SaCheckPermission("blog:talk:edit")
    @Log(title = "说说", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TalkBo bo) {
        return toAjax(talkService.updateByBo(bo));
    }

    /**
     * 删除说说
     *
     * @param talkIds 主键串
     */
    @SaCheckPermission("blog:talk:remove")
    @Log(title = "说说", businessType = BusinessType.DELETE)
    @DeleteMapping("/{talkIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] talkIds) {
        return toAjax(talkService.deleteWithValidByIds(Arrays.asList(talkIds), true));
    }

}
