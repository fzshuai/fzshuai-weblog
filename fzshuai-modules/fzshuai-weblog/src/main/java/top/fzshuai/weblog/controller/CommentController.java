package top.fzshuai.weblog.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import top.fzshuai.weblog.domain.bo.CommentBo;
import top.fzshuai.weblog.domain.dto.CommentDto;
import top.fzshuai.weblog.domain.vo.CommentVo;
import top.fzshuai.weblog.domain.vo.PageResultVo;
import top.fzshuai.weblog.service.ICommentService;
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
 * 文章评论
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/blog/comment")
public class CommentController extends BaseController {

    private final ICommentService commentService;

    /**
     * 查询博客前台评论
     *
     * @param commentVO 评论信息
     */
    @SaIgnore
    @GetMapping("/comments")
    public R<PageResultVo<CommentDto>> listComments(CommentVo commentVO) {
        return R.ok(commentService.selectCommentList(commentVO));
    }

    /**
     * 博客前台添加评论
     *
     * @param commentVO 评论信息
     */
    @SaIgnore
    @PostMapping("/comments")
    public R<?> saveComment(@Valid @RequestBody CommentVo commentVO) {
        commentService.insertComment(commentVO);
        return R.ok();
    }

    /**
     * 评论点赞
     *
     * @param commentId 评论id
     */
    @PostMapping("/comments/{commentId}/like")
    public R<?> saveCommentLike(@PathVariable("commentId") Long commentId) {
        commentService.likeComment(commentId);
        return R.ok();
    }

    /**
     * 查询文章评论列表
     */
    @SaIgnore
    @SaCheckPermission("blog:comment:list")
    @GetMapping("/list")
    public TableDataInfo<CommentVo> list(CommentBo bo, PageQuery pageQuery) {
        return commentService.selectCommentPageList(bo, pageQuery);
    }

    /**
     * 导出文章评论列表
     */
    @SaCheckPermission("blog:comment:export")
    @Log(title = "文章评论", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CommentBo bo, HttpServletResponse response) {
        List<CommentVo> list = commentService.selectCommentList(bo);
        ExcelUtil.exportExcel(list, "文章评论", CommentVo.class, response);
    }

    /**
     * 获取文章评论详细信息
     *
     * @param commentId 主键
     */
    @SaIgnore
    @SaCheckPermission("blog:comment:query")
    @GetMapping("/{commentId}")
    public R<CommentVo> getInfo(@NotNull(message = "主键不能为空")
                                @PathVariable Long commentId) {
        return R.ok(commentService.selectCommentById(commentId));
    }

    /**
     * 新增文章评论
     */
    @SaCheckPermission("blog:comment:add")
    @Log(title = "文章评论", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CommentBo bo) {
        return toAjax(commentService.insertByBo(bo));
    }

    /**
     * 修改文章评论
     */
    @SaCheckPermission("blog:comment:edit")
    @Log(title = "文章评论", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CommentBo bo) {
        return toAjax(commentService.updateByBo(bo));
    }

    /**
     * 删除文章评论
     *
     * @param commentIds 主键串
     */
    @SaCheckPermission("blog:comment:remove")
    @Log(title = "文章评论", businessType = BusinessType.DELETE)
    @DeleteMapping("/{commentIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] commentIds) {
        return toAjax(commentService.deleteWithValidByIds(Arrays.asList(commentIds), true));
    }

    /**
     * 审核评论
     */
    @GetMapping("/audit")
    public R<Void> audit(Long id, Boolean status) {
        return toAjax(commentService.auditComment(id, status));
    }

}
