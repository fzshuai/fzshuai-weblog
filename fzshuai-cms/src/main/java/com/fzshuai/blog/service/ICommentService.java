package com.fzshuai.blog.service;

import com.fzshuai.blog.domain.dto.CommentDTO;
import com.fzshuai.blog.domain.vo.CommentVO;
import com.fzshuai.blog.domain.bo.CommentBO;
import com.fzshuai.blog.domain.vo.PageResultVO;
import com.fzshuai.common.core.page.TableDataInfo;
import com.fzshuai.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 文章评论Service接口
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public interface ICommentService {

    /**
     * 查询博客前台评论
     *
     * @param commentVO 评论信息
     */
    PageResultVO<CommentDTO> selectCommentList(CommentVO commentVO);

    /**
     * 博客前台添加评论
     *
     * @param commentVO 评论对象
     */
    void insertComment(CommentVO commentVO);

    /**
     * 查询文章评论
     */
    CommentVO selectCommentById(Long commentId);

    /**
     * 查询文章评论列表
     */
    TableDataInfo<CommentVO> selectCommentPageList(CommentBO bo, PageQuery pageQuery);

    /**
     * 查询文章评论列表
     */
    List<CommentVO> selectCommentList(CommentBO bo);

    /**
     * 新增文章评论
     */
    Boolean insertByBo(CommentBO bo);

    /**
     * 修改文章评论
     */
    Boolean updateByBo(CommentBO bo);

    /**
     * 校验并批量删除文章评论信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 审核评论
     */
    Boolean auditComment(Long id, Boolean type);

    /**
     * 点赞评论
     *
     * @param commentId 评论id
     */
    void likeComment(Long commentId);
}
