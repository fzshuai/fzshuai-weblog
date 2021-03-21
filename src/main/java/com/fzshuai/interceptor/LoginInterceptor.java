package com.fzshuai.interceptor;

<<<<<<< HEAD
import org.springframework.web.servlet.HandlerInterceptor;
=======
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
>>>>>>> d30a2ee (项目第一次提交)

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 软件二班傅同学
<<<<<<< HEAD
 * @date 2021-01-22 23:07
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断用户是否登录，未登录重定向到登录页面
        if (request.getSession().getAttribute("user") == null){
=======
 * @description TODO
 * @date 2021-02-07 22:00
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if (request.getSession().getAttribute("user") == null) {
>>>>>>> d30a2ee (项目第一次提交)
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> d30a2ee (项目第一次提交)
