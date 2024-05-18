package top.fzshuai.blog.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import top.fzshuai.blog.domain.bo.AlbumBo;
import top.fzshuai.blog.domain.vo.AlbumVo;
import top.fzshuai.blog.service.IAlbumService;
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
 * 相册
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/blog/album")
public class AlbumController extends BaseController {

    private final IAlbumService albumService;

    /**
     * 前台获取相册列表
     *
     * @return 相册列表
     */
    @SaIgnore
    @GetMapping("/photos/albums")
    public R<List<AlbumVo>> listPhotoAlbums() {
        return R.ok(albumService.queryAlbumList());
    }

    /**
     * 查询相册管理列表
     */
    @SaCheckPermission("blog:album:list")
    @GetMapping("/list")
    public TableDataInfo<AlbumVo> list(AlbumBo bo, PageQuery pageQuery) {
        return albumService.queryAlbumPageList(bo, pageQuery);
    }

    /**
     * 导出相册列表
     */
    @SaCheckPermission("blog:album:export")
    @Log(title = "相册", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(AlbumBo bo, HttpServletResponse response) {
        List<AlbumVo> list = albumService.queryAlbumList(bo);
        ExcelUtil.exportExcel(list, "相册", AlbumVo.class, response);
    }

    /**
     * 获取相册详细信息
     *
     * @param albumId 主键
     */
    @SaCheckPermission("blog:album:query")
    @GetMapping("/{albumId}")
    public R<AlbumVo> getInfo(@NotNull(message = "主键不能为空")
                              @PathVariable Long albumId) {
        return R.ok(albumService.queryAlbumById(albumId));
    }

    /**
     * 新增相册
     */
    @SaCheckPermission("blog:album:add")
    @Log(title = "相册", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AlbumBo bo) {
        return toAjax(albumService.insertByBo(bo));
    }

    /**
     * 修改相册
     */
    @SaCheckPermission("blog:album:edit")
    @Log(title = "相册", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AlbumBo bo) {
        return toAjax(albumService.updateByBo(bo));
    }

    /**
     * 删除相册
     *
     * @param albumIds 主键串
     */
    @SaCheckPermission("blog:album:remove")
    @Log(title = "相册", businessType = BusinessType.DELETE)
    @DeleteMapping("/{albumIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] albumIds) {
        return toAjax(albumService.deleteWithValidByIds(Arrays.asList(albumIds), true));
    }

}
