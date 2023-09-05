package com.fzshuai.framework.interceptor;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzshuai.common.utils.StringUtils;
import com.fzshuai.common.utils.blog.BlogPageUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.fzshuai.common.constant.BlogConstant.*;


/**
 * 博客分页拦截器
 *
 * @author fzshuai
 * @date 2023-06-15
 **/
public class BlogPaginationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String currentPage = request.getParameter(CURRENT);
        String pageSize = Optional.ofNullable(request.getParameter(SIZE)).orElse(DEFAULT_SIZE);
        if (StringUtils.isNotEmpty(currentPage)) {
            BlogPageUtils.setCurrentPage(new Page<>(Long.parseLong(currentPage), Long.parseLong(pageSize)));
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        BlogPageUtils.remove();
    }
}
