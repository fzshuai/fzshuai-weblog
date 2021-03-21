package com.fzshuai.service;

import com.fzshuai.po.Comment;

import java.util.List;

/**
 * @author 软件二班傅同学
 * @description TODO
 * @date 2021-02-08 19:38
 */
public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
