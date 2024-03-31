package top.fzshuai.weblog.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import top.fzshuai.weblog.domain.vo.BlogHomeInfoVo;
import top.fzshuai.weblog.domain.vo.BlogInfoVo;
import top.fzshuai.weblog.service.IBlogInfoService;
import top.fzshuai.common.core.controller.BaseController;
import top.fzshuai.common.core.domain.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 博客信息控制器
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@RestController
@RequestMapping("/blog/blogInfo")
public class BlogInfoController extends BaseController {

    @Resource
    private IBlogInfoService blogInfoService;

    /**
     * 查看博客信息
     */
    @SaIgnore
    @GetMapping()
    public R<BlogHomeInfoVo> getBlogHomeInfo() {
        return R.ok(blogInfoService.selectBlogHomeInfo());
    }

    /**
     * 查看关于我信息
     *
     * @return {@link R<String>} 关于我信息
     */
    @SaIgnore
    @GetMapping("/about")
    public R<String> getAbout() {
        return R.ok(blogInfoService.selectAbout());
    }

    /**
     * 修改关于我信息
     *
     * @param blogInfoVO 博客信息
     * @return {@link R<>}
     */
    @PutMapping("/admin/about")
    public R<?> updateAbout(@Valid @RequestBody BlogInfoVo blogInfoVO) {
        blogInfoService.updateAbout(blogInfoVO);
        return R.ok();
    }

    /**
     * 上传访客信息
     *
     * @return {@link R}
     */
    @SaIgnore
    @PostMapping("/report")
    public R<?> report() {
        blogInfoService.reportVisitor();
        return R.ok();
    }
}
