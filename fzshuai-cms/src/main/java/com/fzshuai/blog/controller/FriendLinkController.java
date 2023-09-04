package com.fzshuai.blog.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import com.fzshuai.blog.domain.bo.FriendLinkBo;
import com.fzshuai.blog.domain.dto.FriendLinkDTO;
import com.fzshuai.blog.domain.vo.FriendLinkVo;
import com.fzshuai.blog.service.IFriendLinkService;
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
 * 友人链接
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/blog/friendLink")
public class FriendLinkController extends BaseController {

    private final IFriendLinkService friendLinkService;

    /**
     * 查看前台友链列表
     *
     * @return {@link R< FriendLinkDTO >} 友链列表
     */
    @SaIgnore
    @GetMapping("/links")
    public R<List<FriendLinkDTO>> listFriendLinks() {
        return R.ok(friendLinkService.listFriendLinks());
    }

    /**
     * 查询友人链接列表
     */
    @SaCheckPermission("blog:friendLink:list")
    @GetMapping("/list")
    public TableDataInfo<FriendLinkVo> list(FriendLinkBo bo, PageQuery pageQuery) {
        return friendLinkService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出友人链接列表
     */
    @SaCheckPermission("blog:friendLink:export")
    @Log(title = "友人链接", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FriendLinkBo bo, HttpServletResponse response) {
        List<FriendLinkVo> list = friendLinkService.queryList(bo);
        ExcelUtil.exportExcel(list, "友人链接", FriendLinkVo.class, response);
    }

    /**
     * 获取友人链接详细信息
     *
     * @param friendLinkId 主键
     */
    @SaCheckPermission("blog:friendLink:query")
    @GetMapping("/{friendLinkId}")
    public R<FriendLinkVo> getInfo(@NotNull(message = "主键不能为空")
                                   @PathVariable Long friendLinkId) {
        return R.ok(friendLinkService.queryById(friendLinkId));
    }

    /**
     * 新增友人链接
     */
    @SaCheckPermission("blog:friendLink:add")
    @Log(title = "友人链接", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FriendLinkBo bo) {
        return toAjax(friendLinkService.insertByBo(bo));
    }

    /**
     * 修改友人链接
     */
    @SaCheckPermission("blog:friendLink:edit")
    @Log(title = "友人链接", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FriendLinkBo bo) {
        return toAjax(friendLinkService.updateByBo(bo));
    }

    /**
     * 删除友人链接
     *
     * @param friendLinkIds 主键串
     */
    @SaCheckPermission("blog:friendLink:remove")
    @Log(title = "友人链接", businessType = BusinessType.DELETE)
    @DeleteMapping("/{friendLinkIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] friendLinkIds) {
        return toAjax(friendLinkService.deleteWithValidByIds(Arrays.asList(friendLinkIds), true));
    }

}
