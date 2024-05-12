package top.fzshuai.blog.service;

import top.fzshuai.blog.domain.bo.CommentBo;
import top.fzshuai.blog.domain.dto.CommentDto;
import top.fzshuai.blog.domain.dto.ReplyDto;
import top.fzshuai.blog.domain.vo.CommentVo;
import top.fzshuai.blog.domain.vo.PageResultVo;
import top.fzshuai.common.core.domain.PageQuery;
import top.fzshuai.common.core.page.TableDataInfo;

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
    PageResultVo<CommentDto> queryCommentList(CommentVo commentVO);

    /**
     * 查询文章评论
     */
    CommentVo queryCommentById(Long commentId);

    /**
     * 查询文章评论列表
     */
    TableDataInfo<CommentVo> queryCommentPageList(CommentBo bo, PageQuery pageQuery);

    /**
     * 查询文章评论列表
     */
    List<CommentVo> queryCommentList(CommentBo bo);

    /**
     * 查看评论下的回复
     *
     * @param commentId 评论主键
     * @return 回复列表
     */
    List<ReplyDto> queryReplieListByCommentId(Long commentId);

    /**
     * 博客前台添加评论
     *
     * @param commentVO 评论对象
     */
    void insertComment(CommentVo commentVO);


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

    /**
     * 点赞评论
     *
     * @param commentId 评论id
     */
    void likeComment(Long commentId);

}
