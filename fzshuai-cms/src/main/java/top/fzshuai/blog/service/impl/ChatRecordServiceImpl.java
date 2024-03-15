package top.fzshuai.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import top.fzshuai.common.utils.StringUtils;
import top.fzshuai.common.core.page.TableDataInfo;
import top.fzshuai.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.fzshuai.blog.domain.bo.ChatRecordBO;
import top.fzshuai.blog.domain.vo.ChatRecordVO;
import top.fzshuai.blog.domain.ChatRecord;
import top.fzshuai.blog.mapper.ChatRecordMapper;
import top.fzshuai.blog.service.IChatRecordService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 聊天记录Service业务层处理
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@RequiredArgsConstructor
@Service
public class ChatRecordServiceImpl implements IChatRecordService {

    private final ChatRecordMapper baseMapper;

    /**
     * 查询聊天记录
     */
    @Override
    public ChatRecordVO queryById(Long chatRecordId) {
        return baseMapper.selectVoById(chatRecordId);
    }

    /**
     * 查询聊天记录列表
     */
    @Override
    public TableDataInfo<ChatRecordVO> queryPageList(ChatRecordBO bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ChatRecord> lqw = buildQueryWrapper(bo);
        Page<ChatRecordVO> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询聊天记录列表
     */
    @Override
    public List<ChatRecordVO> queryList(ChatRecordBO bo) {
        LambdaQueryWrapper<ChatRecord> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ChatRecord> buildQueryWrapper(ChatRecordBO bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ChatRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, ChatRecord::getUserId, bo.getUserId());
        lqw.like(StringUtils.isNotBlank(bo.getNickname()), ChatRecord::getNickname, bo.getNickname());
        lqw.eq(StringUtils.isNotBlank(bo.getAvatar()), ChatRecord::getAvatar, bo.getAvatar());
        lqw.eq(StringUtils.isNotBlank(bo.getContent()), ChatRecord::getContent, bo.getContent());
        lqw.eq(StringUtils.isNotBlank(bo.getIpAddress()), ChatRecord::getIpAddress, bo.getIpAddress());
        lqw.eq(StringUtils.isNotBlank(bo.getIpSource()), ChatRecord::getIpSource, bo.getIpSource());
        lqw.eq(bo.getType() != null, ChatRecord::getType, bo.getType());
        return lqw;
    }

    /**
     * 新增聊天记录
     */
    @Override
    public Boolean insertByBo(ChatRecordBO bo) {
        ChatRecord add = BeanUtil.toBean(bo, ChatRecord.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setChatRecordId(add.getChatRecordId());
        }
        return flag;
    }

    /**
     * 修改聊天记录
     */
    @Override
    public Boolean updateByBo(ChatRecordBO bo) {
        ChatRecord update = BeanUtil.toBean(bo, ChatRecord.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ChatRecord entity) {
        // TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除聊天记录
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            // TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

}
