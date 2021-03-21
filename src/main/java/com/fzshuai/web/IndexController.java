package com.fzshuai.web;

import com.fzshuai.NotFoundException;
import com.fzshuai.service.BlogService;
import com.fzshuai.service.TagService;
import com.fzshuai.service.TypeService;
import com.fzshuai.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sun.management.counter.perf.PerfInstrumentation;

/**
 * @author 软件二班傅同学
 * @date 2021-01-18 10:59
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {

        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(5));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));

//        System.out.println("-----------index-----------");
        return "index";
    }

    @GetMapping("/blog/{id}")
    public String blog() {
        return "blog";
    }
}
