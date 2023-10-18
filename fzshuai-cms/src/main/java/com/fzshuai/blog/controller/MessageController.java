package com.fzshuai.blog.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import com.fzshuai.blog.domain.bo.MessageBO;
import com.fzshuai.blog.domain.dto.MessageDTO;
import com.fzshuai.blog.domain.vo.MessageVO;
import com.fzshuai.blog.service.IMessageService;
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
    public R<?> saveMessage(@Valid @RequestBody MessageVO messageVo) {
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
    public R<List<MessageVO>> listMessages() {
        return R.ok(messageService.selectMessageList());
    }

    /**
     * 查询留言管理列表
     */
    @SaCheckPermission("blog:message:list")
    @GetMapping("/list")
    public TableDataInfo<MessageVO> list(MessageBO bo, PageQuery pageQuery) {
        return messageService.selectMessagePageList(bo, pageQuery);
    }

    /**
     * 导出留言列表
     */
    @SaCheckPermission("blog:message:export")
    @Log(title = "留言", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MessageBO bo, HttpServletResponse response) {
        List<MessageVO> list = messageService.selectMessageList(bo);
        ExcelUtil.exportExcel(list, "留言", MessageVO.class, response);
    }

    /**
     * 获取留言详细信息
     *
     * @param messageId 主键
     */
    @SaCheckPermission("blog:message:query")
    @GetMapping("/{messageId}")
    public R<MessageVO> getInfo(@NotNull(message = "主键不能为空")
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
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MessageBO bo) {
        return toAjax(messageService.insertByBo(bo));
    }

    /**
     * 修改留言
     */
    @SaCheckPermission("blog:message:edit")
    @Log(title = "留言", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MessageBO bo) {
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
    public R<Void> audit(Long id,Boolean review){
        return toAjax(messageService.auditMessage(id,review));

    }
}
