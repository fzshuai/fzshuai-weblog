package top.fzshuai.blog.controller;

import java.util.List;
import java.util.Arrays;

import cn.dev33.satoken.annotation.SaIgnore;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;

import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import top.fzshuai.common.annotation.RepeatSubmit;
import top.fzshuai.common.annotation.Log;
import top.fzshuai.common.core.controller.BaseController;
import top.fzshuai.common.core.domain.PageQuery;
import top.fzshuai.common.core.domain.R;
import top.fzshuai.common.core.validate.AddGroup;
import top.fzshuai.common.core.validate.EditGroup;
import top.fzshuai.common.enums.BusinessType;
import top.fzshuai.common.utils.poi.ExcelUtil;
import top.fzshuai.blog.domain.bo.ArticleBO;
import top.fzshuai.blog.service.IArticleService;
import top.fzshuai.common.core.page.TableDataInfo;
import top.fzshuai.blog.domain.vo.*;

/**
 * 文章
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/blog/article")
public class ArticleController extends BaseController {

    private final IArticleService articleService;

    /**
     * 查看文章归档
     *
     * @return 文章归档列表
     */
    @SaIgnore
    @GetMapping("/articles/archives")
    public R<PageResultVO<ArchiveVO>> listArchives() {
        return R.ok(articleService.selectArticleArchiveList());
    }

    /**
     * 查看首页文章
     *
     * @return 首页文章列表
     */
    @SaIgnore
    @GetMapping("/articles")
    public R<List<ArticleHomeVO>> listArticles() {
        return R.ok(articleService.selectArticleHomeList());
    }

    /**
     * 根据id查看文章
     *
     * @param articleId 文章id
     * @return 文章信息
     */
    @SaIgnore
    @GetMapping("/articles/{articleId}")
    public R<ArticleDetailVO> getArticleById(@PathVariable("articleId") Long articleId) {
        return R.ok(articleService.selectArticleDetailById(articleId));
    }

    /**
     * 根据条件查询文章
     *
     * @param condition 条件
     * @return 文章列表
     */
    @GetMapping("/articles/condition")
    public R<ArticlePreviewListVO> listArticlesByCondition(ConditionVO condition) {
        return R.ok(articleService.selectArticlePreviewList(condition));
    }

    /**
     * 搜索文章
     *
     * @param condition 条件
     * @return 文章列表
     */
    @SaIgnore
    @GetMapping("/articles/search")
    public R<List<ArticleSearchVO>> listArticlesBySearch(ConditionVO condition) {
        return R.ok(articleService.searchArticle(condition));
    }

    /**
     * 点赞文章
     *
     * @param articleId 文章id
     * @return R
     */
    @PostMapping("/articles/{articleId}/like")
    public R<?> saveArticleLike(@PathVariable("articleId") Long articleId) {
        articleService.likeArticle(articleId);
        return R.ok();
    }

    /**
     * 查询文章列表
     */
    @SaCheckPermission("blog:article:list")
    @GetMapping("/list")
    public TableDataInfo<ArticleVO> list(ArticleBO bo, PageQuery pageQuery) {
        return articleService.selectArticlePageList(bo, pageQuery);
    }

    /**
     * 导出文章列表
     */
    @SaCheckPermission("blog:article:export")
    @Log(title = "文章", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ArticleBO bo, HttpServletResponse response) {
        List<ArticleVO> list = articleService.selectArticleList(bo);
        ExcelUtil.exportExcel(list, "文章", ArticleVO.class, response);
    }

    /**
     * 获取文章详细信息
     *
     * @param articleId 主键
     */
    @SaCheckPermission("blog:article:query")
    @GetMapping("/{articleId}")
    public R<ArticleVO> getInfo(@NotNull(message = "主键不能为空")
                                @PathVariable Long articleId) {
        return R.ok(articleService.selectArticleById(articleId));
    }

    /**
     * 新增文章
     */
    @SaCheckPermission("blog:article:add")
    @Log(title = "文章", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ArticleBO bo) {
        return toAjax(articleService.insertByBo(bo));
    }

    /**
     * 修改文章
     */
    @SaCheckPermission("blog:article:edit")
    @Log(title = "文章", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ArticleBO bo) {
        return toAjax(articleService.updateByBo(bo));
    }

    /**
     * 删除文章
     *
     * @param articleIds 主键串
     */
    @SaCheckPermission("blog:article:remove")
    @Log(title = "文章", businessType = BusinessType.DELETE)
    @DeleteMapping("/{articleIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] articleIds) {
        return toAjax(articleService.deleteWithValidByIds(Arrays.asList(articleIds), true));
    }
}