package com.fzshuai.blog.controller;


import cn.dev33.satoken.annotation.SaIgnore;
import com.fzshuai.blog.domain.vo.BlogHomeInfoVo;
import com.fzshuai.blog.domain.vo.BlogInfoVo;
import com.fzshuai.blog.service.IBlogInfoService;
import com.fzshuai.common.core.controller.BaseController;
import com.fzshuai.common.core.domain.R;
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
     *
     * @return {@link R<BlogHomeInfoVo>} 博客信息
     */
    @SaIgnore
    @GetMapping()
    public R<BlogHomeInfoVo> getBlogHomeInfo() {
        return R.ok(blogInfoService.getBlogHomeInfo());
    }

    /**
     * 查看关于我信息
     *
     * @return {@link R<String>} 关于我信息
     */
    @SaIgnore
    @GetMapping("/about")
    public R<String> getAbout() {
        return R.ok(blogInfoService.getAbout());
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
        blogInfoService.report();
        return R.ok();
    }
}
