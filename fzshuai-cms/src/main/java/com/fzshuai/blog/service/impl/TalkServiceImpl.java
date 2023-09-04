package com.fzshuai.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fzshuai.blog.domain.Talk;
import com.fzshuai.blog.domain.bo.TalkBo;
import com.fzshuai.blog.domain.dto.CommentCountDTO;
import com.fzshuai.blog.domain.dto.TalkDTO;
import com.fzshuai.blog.domain.vo.PageResult;
import com.fzshuai.blog.domain.vo.TalkVo;
import com.fzshuai.blog.mapper.CommentMapper;
import com.fzshuai.blog.mapper.TalkMapper;
import com.fzshuai.blog.service.ITalkService;
import com.fzshuai.blog.utils.HTMLUtils;
import com.fzshuai.common.core.domain.PageQuery;
import com.fzshuai.common.core.page.TableDataInfo;
import com.fzshuai.common.helper.LoginHelper;
import com.fzshuai.common.utils.StringUtils;
import com.fzshuai.common.utils.blog.BlogPageUtils;
import com.fzshuai.common.utils.redis.RedisUtils;
import com.fzshuai.system.mapper.SysOssMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.fzshuai.common.constant.RedisConstant.TALK_LIKE_COUNT;

/**
 * 说说Service业务层处理
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@RequiredArgsConstructor
@Service
public class TalkServiceImpl implements ITalkService {

    private final TalkMapper baseMapper;
    private final CommentMapper commentMapper;
    private final SysOssMapper ossMapper;

    /**
     * 博客前端首页说说
     *
     * @return
     */
    @Override
    public List<String> listHomeTalks() {
        // 查询最新10条说说
        return baseMapper.selectList(new LambdaQueryWrapper<Talk>()
                        .orderByDesc(Talk::getIsTop)
                        .orderByDesc(Talk::getTalkId)
                        .last("limit 10"))
                .stream()
                .map(item -> item.getContent().length() > 200 ? HTMLUtils.deleteTag(item.getContent().substring(0, 200)) : HTMLUtils.deleteTag(item.getContent()))
                .collect(Collectors.toList());
    }

    /**
     * 博客前端说说列表
     *
     * @return
     */
    @Override
    public PageResult<TalkDTO> listTalks() {
        // 查询说说总量
        Long count = baseMapper.selectCount(new LambdaQueryWrapper<>());
        if (count == 0) {
            return new PageResult<>();
        }
        // 分页查询说说
        List<TalkDTO> result = baseMapper.listTalks(BlogPageUtils.getLimitCurrent(), BlogPageUtils.getSize());

        // 查询说说评论量
        List<Long> topicIdList = result.stream()
                .map(TalkDTO::getTalkId)
                .collect(Collectors.toList());
        Map<Long, Integer> commentCountMap = commentMapper.listCommentCountByTopicIds(topicIdList)
                .stream()
                .collect(Collectors.toMap(CommentCountDTO::getCommentId, CommentCountDTO::getCommentCount));
        // 查询说说点赞量
        Map<String, Object> likeCountMap = RedisUtils.getCacheMap(TALK_LIKE_COUNT);
        result.forEach(item -> {
            item.setLikeCount((Integer) likeCountMap.get(item.getTalkId().toString()));
            item.setCommentCount(commentCountMap.get(item.getTalkId()));
            // 转换图片格式
            if (Objects.nonNull(item.getImages())) {
                List<String> list = Arrays.asList(item.getImages().split(","));
                // 将图片转换为url路径
                List<String> urlList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    String url = ossMapper.selectVoById(Long.parseLong(list.get(i))).getUrl();
                    urlList.add(url);
                    item.setImages(url);
                }
                item.setImgList(urlList);

            }
        });
        return new PageResult<>(result, Integer.parseInt(String.valueOf(count)));
    }

    /**
     * 查询说说
     */
    @Override
    public TalkVo queryById(Long talkId) {
        TalkVo result = baseMapper.BackTalks(talkId);
        if (Objects.nonNull(result.getImages())) {
            List<String> list = Arrays.asList(result.getImages().split(","));
            // 将图片转换为url路径
            result.setImgList(list);
        }
        return result;
    }

    /**
     * 查询说说列表
     */
    @Override
    public TableDataInfo<TalkVo> queryPageList(TalkBo bo, PageQuery pageQuery) {
        Long count = baseMapper.selectCount(new LambdaQueryWrapper<>());
        if (count == 0) {
            return new TableDataInfo<>();
        }
        List<TalkVo> result = baseMapper.listBackTalks();
        result.forEach(item -> {
            if (Objects.nonNull(item.getImages())) {
                List<String> list = Arrays.asList(item.getImages().split(","));
                // 将图片转换为url路径
                List<String> urlList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    String url = ossMapper.selectVoById(Long.parseLong(list.get(i))).getUrl();
                    urlList.add(url);
                    item.setImages(url);
                }
                item.setImgList(urlList);

            }
        });
        return TableDataInfo.build(result);
    }

    /**
     * 查询说说列表
     */
    @Override
    public List<TalkVo> queryList(TalkBo bo) {
        LambdaQueryWrapper<Talk> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Talk> buildQueryWrapper(TalkBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Talk> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, Talk::getUserId, bo.getUserId());
        lqw.eq(StringUtils.isNotBlank(bo.getContent()), Talk::getContent, bo.getContent());
        lqw.eq(StringUtils.isNotBlank(bo.getImages()), Talk::getImages, bo.getImages());
        lqw.eq(bo.getIsTop() != null, Talk::getIsTop, bo.getIsTop());
        return lqw;
    }

    /**
     * 新增说说
     */
    @Override
    public Boolean insertByBo(TalkBo bo) {
        bo.setUserId(LoginHelper.getUserId());
        if (Objects.nonNull(bo.getImages())) {
            List<String> list = Arrays.asList(bo.getImages().split(","));
            // 将图片转换为url路径
            bo.setImgList(list);
        }
        Talk add = BeanUtil.toBean(bo, Talk.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setTalkId(add.getTalkId());
        }
        return flag;
    }

    /**
     * 修改说说
     */
    @Override
    public Boolean updateByBo(TalkBo bo) {
        bo.setUserId(LoginHelper.getUserId());
        if (Objects.nonNull(bo.getImages())) {
            List<String> list = Arrays.asList(bo.getImages().split(","));
            // 将图片转换为url路径
            bo.setImgList(list);
        }
        Talk update = BeanUtil.toBean(bo, Talk.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Talk entity) {
        // TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除说说
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            // TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
