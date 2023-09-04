package com.fzshuai.blog.service;

import com.fzshuai.blog.domain.dto.CommentDTO;
import com.fzshuai.blog.domain.vo.CommentVo;
import com.fzshuai.blog.domain.bo.CommentBo;
import com.fzshuai.blog.domain.vo.PageResult;
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
    PageResult<CommentDTO> listComments(CommentVo commentVO);

    /**
     * 博客前台添加评论
     *
     * @param commentVO 评论对象
     */
    void saveComment(CommentVo commentVO);

    /**
     * 查询文章评论
     */
    CommentVo queryById(Long commentId);

    /**
     * 查询文章评论列表
     */
    TableDataInfo<CommentVo> queryPageList(CommentBo bo, PageQuery pageQuery);

    /**
     * 查询文章评论列表
     */
    List<CommentVo> queryList(CommentBo bo);

    /**
     * 新增文章评论
     */
    Boolean insertByBo(CommentBo bo);

    /**
     * 修改文章评论
     */
    Boolean updateByBo(CommentBo bo);

    /**
     * 校验并批量删除文章评论信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 审核评论
     */
    Boolean auditComment(Long id, Boolean type);
}
