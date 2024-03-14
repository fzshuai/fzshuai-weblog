package top.fzshuai.blog.mapper;

import top.fzshuai.blog.domain.Comment;
import top.fzshuai.blog.domain.dto.CommentCountDTO;
import top.fzshuai.blog.domain.dto.CommentDTO;
import top.fzshuai.blog.domain.dto.ReplyCountDTO;
import top.fzshuai.blog.domain.dto.ReplyDTO;
import top.fzshuai.blog.domain.vo.CommentVO;
import top.fzshuai.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章评论Mapper接口
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public interface CommentMapper extends BaseMapperPlus<CommentMapper, Comment, CommentVO> {

    /**
     * 查看评论
     *
     * @param current   当前页码
     * @param size      大小
     * @param commentVO 评论信息
     * @return 评论集合
     */
    List<CommentDTO> selectCommentList(@Param("current") Long current, @Param("size") Long size, @Param("commentVO") CommentVO commentVO);

    /**
     * 查看评论id集合下的回复
     *
     * @param commentIdList 评论id集合
     * @return 回复集合
     */
    List<ReplyDTO> selectReplyListByIds(@Param("commentIdList") List<Long> commentIdList);

    /**
     * 根据评论id查询回复总量
     *
     * @param commentIdList 评论id集合
     * @return 回复数量
     */
    List<ReplyCountDTO> selectReplyCountByIds(@Param("commentIdList") List<Long> commentIdList);

    /**
     * 根据评论主题id获取评论量
     *
     * @param topicIdList 说说id列表
     * @return 说说评论量
     */
    List<CommentCountDTO> selectCommentCountByTopicIds(List<Long> topicIdList);

}
