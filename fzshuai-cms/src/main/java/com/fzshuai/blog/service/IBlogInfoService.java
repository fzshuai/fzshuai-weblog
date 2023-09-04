package com.fzshuai.blog.service;

import com.fzshuai.blog.domain.vo.BlogHomeInfoVo;
import com.fzshuai.blog.domain.vo.BlogInfoVo;
import com.fzshuai.blog.domain.vo.WebsiteConfigVo;

/**
 * 博客信息服务
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public interface IBlogInfoService {

    /**
     * 获取首页数据
     *
     * @return 博客首页信息
     */
    BlogHomeInfoVo getBlogHomeInfo();

    /**
     * 获取关于我内容
     *
     * @return 关于我内容
     */
    String getAbout();

    /**
     * 修改关于我内容
     *
     * @param blogInfoVO 博客信息
     */
    void updateAbout(BlogInfoVo blogInfoVO);

    /**
     * 上传访客信息
     */
    void report();
}
