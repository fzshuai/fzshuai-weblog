package com.fzshuai.controller.admin;

import com.fzshuai.po.User;
import com.fzshuai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author 软件二班傅同学
 * @description TODO
 * @date 2021-02-03 20:46
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        // 拿到User对象
        User user = userService.checkUser(username, password);
        // 如果当前user合法
        if (user != null) {
            // 那么在session中保存user，同时为了安全将password置空
            user.setPassword(null);
            session.setAttribute("user",user);
            // 跳转到欢迎页
            return "admin/index";
        } else {
            // 如果当前的user为空，说明用户名或密码错误
            // 使用attributes向前端传递提示message
            attributes.addFlashAttribute("message", "用户名和密码错误");
            // 重定向回登录页
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
