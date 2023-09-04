package com.fzshuai.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzshuai.blog.domain.Comment;
import com.fzshuai.blog.domain.bo.CommentBo;
import com.fzshuai.blog.domain.dto.CommentDTO;
import com.fzshuai.blog.domain.dto.ReplyCountDTO;
import com.fzshuai.blog.domain.dto.ReplyDTO;
import com.fzshuai.blog.domain.vo.CommentVo;
import com.fzshuai.blog.domain.vo.PageResult;
import com.fzshuai.blog.domain.vo.WebsiteConfigVo;
import com.fzshuai.blog.mapper.ArticleMapper;
import com.fzshuai.blog.mapper.CommentMapper;
import com.fzshuai.blog.service.ICommentService;
import com.fzshuai.blog.service.IWebsiteConfigService;
import com.fzshuai.blog.utils.HTMLUtils;
import com.fzshuai.common.core.domain.PageQuery;
import com.fzshuai.common.core.page.TableDataInfo;
import com.fzshuai.common.utils.ServletUtils;
import com.fzshuai.common.utils.StringUtils;
import com.fzshuai.common.utils.blog.BlogPageUtils;
import com.fzshuai.common.utils.ip.AddressUtils;
import com.fzshuai.common.utils.redis.RedisUtils;
import com.fzshuai.system.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.fzshuai.common.constant.BlogConstant.*;
import static com.fzshuai.common.constant.RedisConstant.COMMENT_LIKE_COUNT;

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
     * @param commentVO 评论信息
     */
    @Override
    public PageResult<CommentDTO> listComments(CommentVo commentVO) {
        // 查询评论量
        Long commentCount = baseMapper.selectCount(new LambdaQueryWrapper<Comment>()
                .eq(Objects.nonNull(commentVO.getTopicId()), Comment::getTopicId, commentVO.getTopicId())
                .eq(Comment::getType, commentVO.getType())
                .isNull(Comment::getParentId)
                .eq(Comment::getState, STATE));
        if (commentCount == 0) {
            return new PageResult<>();
        }
        // 分页查询评论数据
        List<CommentDTO> commentDTOList = baseMapper.listComments(BlogPageUtils.getLimitCurrent(), BlogPageUtils.getSize(), commentVO);
        if (CollectionUtils.isEmpty(commentDTOList)) {
            return new PageResult<>();
        }
        // 查询redis的评论点赞数据
        Map<String, Object> likeCountMap = RedisUtils.getCacheMap(COMMENT_LIKE_COUNT);
        // 提取评论id集合
        List<Long> commentIdList = commentDTOList.stream()
                .map((CommentDTO::getCommentId))
                .collect(Collectors.toList());
        // 根据评论id集合查询回复数据
        List<ReplyDTO> replyDTOList = baseMapper.listReplies(commentIdList);
        // 封装回复点赞量
        replyDTOList.forEach(item -> item.setLikeCount((Integer) likeCountMap.get(item.getCommentId().toString())));
        // 根据评论id分组回复数据
        Map<Long, List<ReplyDTO>> replyMap = replyDTOList.stream()
                .collect(Collectors.groupingBy(ReplyDTO::getParentId));
        // 根据评论id查询回复量
        Map<Long, Integer> replyCountMap = baseMapper.listReplyCountByCommentId(commentIdList)
                .stream().collect(Collectors.toMap(ReplyCountDTO::getCommentId, ReplyCountDTO::getReplyCount));
        // 封装评论数据
        commentDTOList.forEach(item -> {
            item.setLikeCount((Integer) likeCountMap.get(item.getCommentId().toString()));
            item.setReplyDTOList(replyMap.get(item.getCommentId()));
            item.setReplyCount(replyCountMap.get(item.getCommentId()));
        });
        return new PageResult<>(commentDTOList, Integer.parseInt(String.valueOf(commentCount)));
    }

    /**
     * 博客前台添加评论
     *
     * @param commentVo 评论对象
     */
    @Override
    public void saveComment(CommentVo commentVo) {
        // 判断是否需要审核
        WebsiteConfigVo websiteConfig = websiteConfigService.getWebsiteConfig();
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
     * 查询文章评论
     */
    @Override
    public CommentVo queryById(Long commentId) {
        return baseMapper.selectVoById(commentId);
    }

    /**
     * 查询文章评论列表
     */
    @Override
    public TableDataInfo<CommentVo> queryPageList(CommentBo bo, PageQuery pageQuery) {
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
    public List<CommentVo> queryList(CommentBo bo) {
        LambdaQueryWrapper<Comment> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
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
}
