package top.fzshuai.blog.mapper;

import top.fzshuai.blog.domain.Comment;
import top.fzshuai.blog.domain.dto.CommentCountDto;
import top.fzshuai.blog.domain.dto.CommentDto;
import top.fzshuai.blog.domain.dto.ReplyCountDto;
import top.fzshuai.blog.domain.dto.ReplyDto;
import top.fzshuai.blog.domain.vo.CommentVo;
import top.fzshuai.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章评论Mapper接口
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public interface CommentMapper extends BaseMapperPlus<CommentMapper, Comment, CommentVo> {

    /**
     * 查看评论
     *
     * @param current   当前页码
     * @param size      大小
     * @param commentVo 评论信息
     * @return 评论集合
     */
    List<CommentDto> selectCommentList(@Param("current") Long current, @Param("size") Long size, @Param("commentVo") CommentVo commentVo);

    /**
     * 查看评论id集合下的回复
     *
     * @param commentIdList 评论id集合
     * @return 回复集合
     */
    List<ReplyDto> selectReplyListByIds(@Param("commentIdList") List<Long> commentIdList);

    /**
     * 根据评论id查询回复总量
     *
     * @param commentIdList 评论id集合
     * @return 回复数量
     */
    List<ReplyCountDto> selectReplyCountByIds(@Param("commentIdList") List<Long> commentIdList);

    /**
     * 根据评论主题id获取评论量
     *
     * @param topicIdList 说说id列表
     * @return 说说评论量
     */
    List<CommentCountDto> selectCommentCountByTopicIds(@Param("topicIdList") List<Long> topicIdList);

    /**
     * 查看当条评论下的回复
     *
     * @param commentId 评论id
     * @param current   当前页码
     * @param size      大小
     * @return 回复集合
     */
    List<ReplyDto> selectReplieListByCommentId(@Param("current") Long current, @Param("size") Long size, @Param("commentId") Long commentId);
}
