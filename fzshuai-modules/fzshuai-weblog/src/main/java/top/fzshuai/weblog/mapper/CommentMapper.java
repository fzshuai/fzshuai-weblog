package top.fzshuai.weblog.mapper;

import top.fzshuai.weblog.domain.Comment;
import top.fzshuai.weblog.domain.dto.CommentCountDto;
import top.fzshuai.weblog.domain.dto.CommentDto;
import top.fzshuai.weblog.domain.dto.ReplyCountDto;
import top.fzshuai.weblog.domain.dto.ReplyDto;
import top.fzshuai.weblog.domain.vo.CommentVo;
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
     * @param commentVO 评论信息
     * @return 评论集合
     */
    List<CommentDto> selectCommentList(@Param("current") Long current, @Param("size") Long size, @Param("commentVO") CommentVo commentVO);

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
    List<CommentCountDto> selectCommentCountByTopicIds(List<Long> topicIdList);

}
