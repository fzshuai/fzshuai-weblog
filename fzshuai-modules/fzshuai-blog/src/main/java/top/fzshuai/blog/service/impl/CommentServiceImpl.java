package top.fzshuai.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import top.fzshuai.blog.domain.Comment;
import top.fzshuai.blog.domain.bo.CommentBo;
import top.fzshuai.blog.domain.dto.CommentDto;
import top.fzshuai.blog.domain.dto.ReplyCountDto;
import top.fzshuai.blog.domain.dto.ReplyDto;
import top.fzshuai.blog.domain.vo.CommentVo;
import top.fzshuai.blog.domain.vo.PageResultVo;
import top.fzshuai.blog.domain.vo.WebsiteConfigVo;
import top.fzshuai.blog.mapper.ArticleMapper;
import top.fzshuai.blog.mapper.CommentMapper;
import top.fzshuai.blog.service.ICommentService;
import top.fzshuai.blog.service.IWebsiteConfigService;
import top.fzshuai.blog.utils.HTMLUtils;
import top.fzshuai.common.core.domain.PageQuery;
import top.fzshuai.common.core.page.TableDataInfo;
import top.fzshuai.common.helper.LoginHelper;
import top.fzshuai.common.utils.ServletUtils;
import top.fzshuai.common.utils.StringUtils;
import top.fzshuai.common.utils.blog.BlogPageUtils;
import top.fzshuai.common.utils.ip.AddressUtils;
import top.fzshuai.common.utils.redis.RedisUtils;
import top.fzshuai.system.mapper.SysUserMapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static top.fzshuai.common.constant.BlogConstant.*;

/**
 * 文章评论Service业务层处理
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements ICommentService {

    private final CommentMapper baseMapper;
    private final ArticleMapper articleMapper;
    private final SysUserMapper sysUserMapper;
    private final IWebsiteConfigService websiteConfigService;

    /**
     * 查询博客前台评论
     *
     * @param commentVo 评论信息
     */
    @Override
    public PageResultVo<CommentDto> queryCommentList(CommentVo commentVo) {
        // 查询评论量
        Long commentCount = baseMapper.selectCount(new LambdaQueryWrapper<Comment>()
            .eq(Objects.nonNull(commentVo.getTopicId()), Comment::getTopicId, commentVo.getTopicId())
            .eq(Comment::getType, commentVo.getType())
            .isNull(Comment::getParentId)
            .eq(Comment::getState, STATE));
        if (commentCount == 0) {
            return new PageResultVo<>();
        }
        // 分页查询评论数据
        List<CommentDto> commentDtoList = baseMapper.selectCommentList(BlogPageUtils.getLimitCurrent(), BlogPageUtils.getSize(), commentVo);
        if (CollectionUtils.isEmpty(commentDtoList)) {
            return new PageResultVo<>();
        }
        // 查询redis的评论点赞数据
        Map<String, Object> likeCountMap = RedisUtils.getCacheMap(COMMENT_LIKE_COUNT);
        // 提取评论id集合
        List<Long> commentIdList = commentDtoList.stream()
            .map((CommentDto::getCommentId))
            .collect(Collectors.toList());
        // 根据评论id集合查询回复数据
        List<ReplyDto> replyDtoList = baseMapper.selectReplyListByIds(commentIdList);
        // 封装回复点赞量
        replyDtoList.forEach(item -> item.setLikeCount((Integer) likeCountMap.get(item.getCommentId().toString())));
        // 根据评论id分组回复数据
        Map<Long, List<ReplyDto>> replyMap = replyDtoList.stream()
            .collect(Collectors.groupingBy(ReplyDto::getParentId));
        // 根据评论id查询回复量
        Map<Long, Integer> replyCountMap = baseMapper.selectReplyCountByIds(commentIdList)
            .stream().collect(Collectors.toMap(ReplyCountDto::getCommentId, ReplyCountDto::getReplyCount));
        // 封装评论数据
        commentDtoList.forEach(item -> {
            item.setLikeCount((Integer) likeCountMap.get(item.getCommentId().toString()));
            item.setReplyDtoList(replyMap.get(item.getCommentId()));
            item.setReplyCount(replyCountMap.get(item.getCommentId()));
        });
        return new PageResultVo<>(commentDtoList, Integer.parseInt(String.valueOf(commentCount)));
    }

    /**
     * 查询文章评论
     */
    @Override
    public CommentVo queryCommentById(Long commentId) {
        return baseMapper.selectVoById(commentId);
    }

    /**
     * 查询文章评论列表
     */
    @Override
    public TableDataInfo<CommentVo> queryCommentPageList(CommentBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Comment> lqw = buildQueryWrapper(bo);
        Page<CommentVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        result.getRecords().forEach(commentVo -> {
            commentVo.setNickname(sysUserMapper.selectVoById(commentVo.getReplyUserId()).getNickName());
            commentVo.setArticleTitle(articleMapper.selectById(commentVo.getTopicId()).getArticleTitle());
            commentVo.setReplyUserName(sysUserMapper.selectVoById(commentVo.getReplyUserId()).getUserName());
        });
        return TableDataInfo.build(result);
    }

    /**
     * 查询文章评论列表
     */
    @Override
    public List<CommentVo> queryCommentList(CommentBo bo) {
        LambdaQueryWrapper<Comment> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    @Override
    public List<ReplyDto> queryReplieListByCommentId(Long commentId) {
        // 转换页码查询评论下的回复
        List<ReplyDto> replyDtoList = baseMapper.selectReplieListByCommentId(BlogPageUtils.getLimitCurrent(), BlogPageUtils.getSize(), commentId);
        // 查询redis的评论点赞数据
        Map<String, Object> likeCountMap = RedisUtils.getCacheMap(COMMENT_LIKE_COUNT);
        // 封装点赞数据
        replyDtoList.forEach(item -> item.setLikeCount((Integer) likeCountMap.get(item.getCommentId().toString())));
        return replyDtoList;
    }

    private LambdaQueryWrapper<Comment> buildQueryWrapper(CommentBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Comment> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, Comment::getUserId, bo.getUserId());
        lqw.eq(bo.getTopicId() != null, Comment::getTopicId, bo.getTopicId());
        lqw.eq(StringUtils.isNotBlank(bo.getCommentContent()), Comment::getCommentContent, bo.getCommentContent());
        lqw.eq(bo.getReplyUserId() != null, Comment::getReplyUserId, bo.getReplyUserId());
        lqw.eq(bo.getParentId() != null, Comment::getParentId, bo.getParentId());
        lqw.eq(bo.getType() != null, Comment::getType, bo.getType());
        lqw.eq(bo.getIsDelete() != null, Comment::getIsDelete, bo.getIsDelete());
        lqw.eq(StringUtils.isNotBlank(bo.getIpAddress()), Comment::getIpAddress, bo.getIpAddress());
        lqw.eq(StringUtils.isNotBlank(bo.getIpSource()), Comment::getIpSource, bo.getIpSource());
        lqw.eq(bo.getState() != null, Comment::getState, bo.getState());
        return lqw;
    }

    /**
     * 博客前台添加评论
     *
     * @param commentVo 评论对象
     */
    @Override
    public void insertComment(CommentVo commentVo) {
        // 判断是否需要审核
        WebsiteConfigVo websiteConfig = websiteConfigService.queryWebsiteConfig();
        Integer isReview = websiteConfig.getIsCommentReview();
        // 过滤标签
        commentVo.setCommentContent(HTMLUtils.deleteTag(commentVo.getCommentContent()));
        Comment comment = Comment.builder()
            .userId(commentVo.getUserId())
            .replyUserId(commentVo.getReplyUserId())
            .topicId(commentVo.getTopicId())
            .commentContent(commentVo.getCommentContent())
            .parentId(commentVo.getParentId())
            .type(commentVo.getType())
            .ipAddress(ServletUtils.getClientIP())
            .ipSource(AddressUtils.getRealAddressByIP(ServletUtils.getClientIP()))
            .state(isReview == TRUE ? 2 : 1)
            .build();
        baseMapper.insert(comment);
        // 判断是否开启邮箱通知,通知用户
        // if (websiteConfig.getIsEmailNotice().equals(TRUE)) {
        //     CompletableFuture.runAsync(() -> notice(comment));
        // }
    }

    /**
     * 新增文章评论
     */
    @Override
    public Boolean insertByBo(CommentBo bo) {
        Comment add = BeanUtil.toBean(bo, Comment.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setCommentId(add.getCommentId());
        }
        return flag;
    }

    /**
     * 修改文章评论
     */
    @Override
    public Boolean updateByBo(CommentBo bo) {
        Comment update = BeanUtil.toBean(bo, Comment.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Comment entity) {
        // TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除文章评论
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            // TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 审核评论
     */
    @Override
    public Boolean auditComment(Long commentId, Boolean status) {
        return baseMapper.update(null, new LambdaUpdateWrapper<Comment>()
            .set(Comment::getState, status ? 1 : 3)
            .eq(Comment::getCommentId, commentId)) > 0;
    }

    @Override
    public void likeComment(Long commentId) {
        // 判断是否点赞
        String commentLikeKey = COMMENT_USER_LIKE + LoginHelper.getUserId();
        if (RedisUtils.isExistsCacheSetObject(commentLikeKey, commentId)) {
            // 点过赞则删除评论id
            RedisUtils.delCacheSetObject(commentLikeKey, commentId);
            // 评论点赞量-1
            RedisUtils.decrCacheMapValue(COMMENT_LIKE_COUNT, commentId.toString(), 1L);
        } else {
            // 未点赞则增加评论id
            RedisUtils.setCacheSet(commentLikeKey, commentId);
            // 评论点赞量+1
            RedisUtils.incrCacheMapValue(COMMENT_LIKE_COUNT, commentId.toString(), 1L);
        }
    }

}
