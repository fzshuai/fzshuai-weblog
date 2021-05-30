package com.fzshuai.controller;

import com.fzshuai.po.Comment;
import com.fzshuai.po.User;
import com.fzshuai.service.BlogService;
import com.fzshuai.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * @author 软件二班傅同学
 * @description TODO
 * @date 2021-02-14 21:23
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    /**
     * 此时为POST请求，评论信息包装为Comment对象
     * 评论人信息根据session中保存的user获取
     *
     * @param comment
     * @param session
     * @return
     */
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        // 首先获取到对应博客的id
        Long blogId = comment.getBlog().getId();
        // 根据博客id获取到对应的博客，设置Comment对象相应字段的值
        comment.setBlog(blogService.getBlog(blogId));
        // 判断评论的是博主还是访客
        User user = (User) session.getAttribute("user");
        if (user != null) {
            // 如果是博主，除了设置头像外，还需要设置adminComment字段为true
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            // 否则只需要选择访客头像
            // adminComment默认为false，表示为访客评论
            comment.setAvatar(avatar);
        }
        // 最后保存评论，重定向到评论显示部分
        commentService.saveComment(comment);
        // 重定向到/comments/" + blogId，“/comments/" + blogId”为请求路径，执行comments方法
        return "redirect:/comments/" + blogId;
    }
}
