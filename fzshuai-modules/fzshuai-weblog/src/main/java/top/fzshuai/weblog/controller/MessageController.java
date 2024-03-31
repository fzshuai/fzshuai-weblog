package top.fzshuai.weblog.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import top.fzshuai.weblog.domain.bo.MessageBo;
import top.fzshuai.weblog.domain.vo.MessageVo;
import top.fzshuai.weblog.service.IMessageService;
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
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * 留言
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/blog/message")
public class MessageController extends BaseController {

    private final IMessageService messageService;

    /**
     * 前台添加留言
     */
    @SaIgnore
    @PostMapping("/messages")
    public R<?> saveMessage(@Valid @RequestBody MessageVo messageVo) {
        messageService.insertMessage(messageVo);
        return R.ok();
    }

    /**
     * 查看前台留言列表
     *
     * @return 留言列表
     */
    @SaIgnore
    @GetMapping("/messages")
    public R<List<MessageVo>> listMessages() {
        return R.ok(messageService.selectMessageList());
    }

    /**
     * 查询留言管理列表
     */
    @SaCheckPermission("blog:message:list")
    @GetMapping("/list")
    public TableDataInfo<MessageVo> list(MessageBo bo, PageQuery pageQuery) {
        return messageService.selectMessagePageList(bo, pageQuery);
    }

    /**
     * 导出留言列表
     */
    @SaCheckPermission("blog:message:export")
    @Log(title = "留言", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MessageBo bo, HttpServletResponse response) {
        List<MessageVo> list = messageService.selectMessageList(bo);
        ExcelUtil.exportExcel(list, "留言", MessageVo.class, response);
    }

    /**
     * 获取留言详细信息
     *
     * @param messageId 主键
     */
    @SaCheckPermission("blog:message:query")
    @GetMapping("/{messageId}")
    public R<MessageVo> getInfo(@NotNull(message = "主键不能为空")
                                @PathVariable Long messageId) {
        return R.ok(messageService.selectMessageById(messageId));
    }

    /**
     * 新增留言
     */
    @SaCheckPermission("blog:message:add")
    @Log(title = "留言", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MessageBo bo) {
        return toAjax(messageService.insertByBo(bo));
    }

    /**
     * 修改留言
     */
    @SaCheckPermission("blog:message:edit")
    @Log(title = "留言", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MessageBo bo) {
        return toAjax(messageService.updateByBo(bo));
    }

    /**
     * 删除留言
     *
     * @param messageIds 主键串
     */
    @SaCheckPermission("blog:message:remove")
    @Log(title = "留言", businessType = BusinessType.DELETE)
    @DeleteMapping("/{messageIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] messageIds) {
        return toAjax(messageService.deleteWithValidByIds(Arrays.asList(messageIds), true));
    }

    /**
     * 审核留言
     */
    @GetMapping("/audit")
    public R<Void> audit(Long id, Boolean review) {
        return toAjax(messageService.auditMessage(id, review));

    }

}
