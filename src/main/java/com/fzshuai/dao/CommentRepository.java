package com.fzshuai.dao;

import com.fzshuai.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 软件二班傅同学
 * @description TODO
 * @date 2021-02-03 21:20
 */
public interface CommentRepository extends JpaRepository<Comment,Long>{

    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);
}
