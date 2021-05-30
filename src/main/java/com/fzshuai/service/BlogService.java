package com.fzshuai.service;

import com.fzshuai.po.Blog;
import com.fzshuai.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author 软件二班傅同学
 * @description TODO
 * @date 2021-02-07 22:59
 */
public interface BlogService {

    Blog getBlog(Long id);

    // 博客内容格式转换  MakeDown -> HTML
    Blog getAndConvert(Long id);

    // 根据复合查询条件获取博客列表，实现分页查询
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(String query, Pageable pageable);

    List<Blog> listRecommendBlogTop(Integer size);

    Map<String, List<Blog>> archiveBlog();

    Long countBlog();

    Blog saveBlog(Blog blog);

    // 更新博客
    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);
}
