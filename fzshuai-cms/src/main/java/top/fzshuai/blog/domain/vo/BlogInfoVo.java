package top.fzshuai.blog.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 博客信息
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
public class BlogInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 关于我内容
     */
    private String aboutContent;

}
