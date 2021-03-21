package com.fzshuai.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
<<<<<<< HEAD
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 软件二班傅同学
 * @date 2021-01-22 23:25
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //加载登录适配器
=======
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author 软件二班傅同学
 * @description TODO
 * @date 2021-02-04 20:22
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
>>>>>>> d30a2ee (项目第一次提交)
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");
    }
}
