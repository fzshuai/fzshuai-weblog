package top.fzshuai.blog.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import top.fzshuai.blog.domain.bo.TalkBo;
import top.fzshuai.blog.domain.dto.TalkDto;
import top.fzshuai.blog.domain.vo.PageResultVo;
import top.fzshuai.blog.domain.vo.TalkVo;
import top.fzshuai.blog.service.ITalkService;
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
     */
    @SaIgnore
    @GetMapping("/home/talks")
    public R<List<String>> listHomeTalks() {
        return R.ok(talkService.queryTalkHomeList());
    }

    /**
     * 博客前端查看说说列表
     */
    @SaIgnore
    @GetMapping("/talks")
    public R<PageResultVo<TalkDto>> listTalks() {
        return R.ok(talkService.queryTalkPageList());
    }

    /**
     * 查询说说列表
     */
    @SaCheckPermission("blog:talk:list")
    @GetMapping("/list")
    public TableDataInfo<TalkVo> list(TalkBo bo, PageQuery pageQuery) {
        return talkService.queryTalkPageList(bo, pageQuery);
    }

    /**
     * 导出说说列表
     */
    @SaCheckPermission("blog:talk:export")
    @Log(title = "说说", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TalkBo bo, HttpServletResponse response) {
        List<TalkVo> list = talkService.queryTalkList(bo);
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
        return R.ok(talkService.queryTalkById(talkId));
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
