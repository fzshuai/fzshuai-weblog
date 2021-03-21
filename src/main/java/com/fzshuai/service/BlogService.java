package com.fzshuai.service;

import com.fzshuai.po.Blog;
import com.fzshuai.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
<<<<<<< HEAD

/**
 * @author 软件二班傅同学
 * @date 2021-01-26 20:16
=======
import java.util.Map;

/**
 * @author 软件二班傅同学
 * @description TODO
 * @date 2021-02-07 22:59
>>>>>>> d30a2ee (项目第一次提交)
 */
public interface BlogService {

    Blog getBlog(Long id);

<<<<<<< HEAD
=======
    // 博客内容格式转换  MakeDown -> HTML
    Blog getAndConvert(Long id);

    // 根据复合查询条件获取博客列表，实现分页查询
>>>>>>> d30a2ee (项目第一次提交)
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

<<<<<<< HEAD
    List<Blog> listRecommendBlogTop(Integer size);

    Blog saveBlog(Blog blog);

=======
    Page<Blog> listBlog(String query, Pageable pageable);

    List<Blog> listRecommendBlogTop(Integer size);

    Map<String, List<Blog>> archiveBlog();

    Long countBlog();

    Blog saveBlog(Blog blog);

    // 更新博客
>>>>>>> d30a2ee (项目第一次提交)
    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);
}
