package com.fzshuai.blog.service;

import com.fzshuai.blog.domain.vo.BlogHomeInfoVO;
import com.fzshuai.blog.domain.vo.BlogInfoVO;

/**
 * 博客信息服务
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public interface IBlogInfoService {

    /**
     * 查询首页数据
     *
     * @return 博客首页信息
     */
    BlogHomeInfoVO selectBlogHomeInfo();

    /**
     * 查询关于我内容
     *
     * @return 关于我内容
     */
    String selectAbout();

    /**
     * 修改关于我内容
     *
     * @param blogInfoVO 博客信息
     */
    void updateAbout(BlogInfoVO blogInfoVO);

    /**
     * 上传访客信息
     */
    void reportVisitor();
}
