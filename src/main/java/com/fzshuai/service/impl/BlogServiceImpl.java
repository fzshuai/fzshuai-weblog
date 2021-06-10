package com.fzshuai.service.impl;

import com.fzshuai.NotFoundException;
import com.fzshuai.dao.BlogRepository;
import com.fzshuai.po.Blog;
import com.fzshuai.po.Type;
import com.fzshuai.service.BlogService;
import com.fzshuai.util.MarkdownUtils;
import com.fzshuai.util.MyBeanUtils;
import com.fzshuai.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @author 软件二班傅同学
 * @description TODO
 * @date 2021-02-07 23:01
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        // 首先根据id查询对应的博客是否存在
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }
        // 为了不改变博客数据库中保存的格式，首先获取博客的副本
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        // 获取博客正文
        String content = b.getContent();
        // 调用工具类中的方法实现格式转换
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        // 更新浏览次数，打开一次算一次浏览
        blogRepository.updateViews(id);
        return b;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        // 这里调用了JpaSpecificationExecutor中的findAll方法，方法的参数为Specification对象
        return blogRepository.findAll(new Specification<Blog>() {
            // 重写toPredicate方法，添加查询条件
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                // 如果输入了标题信息，则根据标题构建查询语句
                // 这里使用模糊查询
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(cb.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
                }
                // 如果输入了类型，则获取输入类型对应的类型id，将其作为查询条件
                if (blog.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                // 如果点了推荐，则同样将其作为查询条件
                if (blog.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                // 构建复合查询条件
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query, pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogRepository.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year, blogRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        // 如果执行的是新增操作，则需设置CreateTime和UpdateTime为相同值
        // 同时设置Views值为0
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } else {
            // 否则，说明执行的是更新操作，只需要设置UpdateTime
            blog.setUpdateTime(new Date());
        }
        // 最后调用save方法保存博客
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.findById(id).orElse(null);
        if (b == null) {
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
