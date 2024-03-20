package top.fzshuai.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.fzshuai.blog.domain.Message;
import top.fzshuai.blog.domain.bo.MessageBo;
import top.fzshuai.blog.domain.vo.MessageVo;
import top.fzshuai.blog.mapper.MessageMapper;
import top.fzshuai.blog.service.IMessageService;
import top.fzshuai.blog.service.IWebsiteConfigService;
import top.fzshuai.common.core.domain.PageQuery;
import top.fzshuai.common.core.page.TableDataInfo;
import top.fzshuai.common.utils.BeanCopyUtils;
import top.fzshuai.common.utils.ServletUtils;
import top.fzshuai.common.utils.StringUtils;
import top.fzshuai.common.utils.ip.AddressUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static top.fzshuai.common.constant.BlogConstant.*;

/**
 * 留言Service业务层处理
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements IMessageService {

    private final MessageMapper baseMapper;
    private final IWebsiteConfigService websiteConfigService;
    private final HttpServletRequest request;

    /**
     * 前台添加留言
     *
     * @param messageVO 留言对象
     */
    @Override
    public void insertMessage(MessageVo messageVO) {
        // 判断是否需要审核
        Integer isReview = websiteConfigService.selectWebsiteConfig().getIsMessageReview();
        // 获取用户ip
        String ipAddress = ServletUtils.getClientIP(request);
        String ipSource = AddressUtils.getRealAddressByIP(ipAddress);
        Message message = BeanCopyUtils.copy(messageVO, Message.class);
        message.setIpAddress(ipAddress);
        message.setIsReview(isReview == TRUE ? YES : NO);
        message.setIpSource(ipSource);
        baseMapper.insert(message);
    }

    /**
     * 查看前台留言弹幕
     *
     * @return
     */
    @Override
    public List<MessageVo> selectMessageList() {
        // 查询留言列表
        List<Message> messageList = baseMapper.selectList(new LambdaQueryWrapper<Message>()
            .select(Message::getMessageId, Message::getNickname, Message::getAvatar, Message::getMessageContent, Message::getTime)
            .eq(Message::getIsReview, YES));
        return BeanCopyUtils.copyList(messageList, MessageVo.class);
    }

    /*
     * 查询留言
     */
    @Override
    public MessageVo selectMessageById(Long messageId) {
        return baseMapper.selectVoById(messageId);
    }

    /**
     * 查询留言列表
     */
    @Override
    public TableDataInfo<MessageVo> selectMessagePageList(MessageBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Message> lqw = buildQueryWrapper(bo);
        Page<MessageVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询留言列表
     */
    @Override
    public List<MessageVo> selectMessageList(MessageBo bo) {
        LambdaQueryWrapper<Message> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Message> buildQueryWrapper(MessageBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Message> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getNickname()), Message::getNickname, bo.getNickname());
        lqw.eq(StringUtils.isNotBlank(bo.getAvatar()), Message::getAvatar, bo.getAvatar());
        lqw.eq(StringUtils.isNotBlank(bo.getMessageContent()), Message::getMessageContent, bo.getMessageContent());
        lqw.eq(StringUtils.isNotBlank(bo.getIpAddress()), Message::getIpAddress, bo.getIpAddress());
        lqw.eq(StringUtils.isNotBlank(bo.getIpSource()), Message::getIpSource, bo.getIpSource());
        lqw.eq(bo.getTime() != null, Message::getTime, bo.getTime());
        lqw.eq(bo.getIsReview() != null, Message::getIsReview, bo.getIsReview());
        return lqw;
    }

    /**
     * 新增留言
     */
    @Override
    public Boolean insertByBo(MessageBo bo) {
        Message add = BeanUtil.toBean(bo, Message.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setMessageId(add.getMessageId());
        }
        return flag;
    }

    /**
     * 修改留言
     */
    @Override
    public Boolean updateByBo(MessageBo bo) {
        Message update = BeanUtil.toBean(bo, Message.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Message entity) {
        // TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除留言
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            // TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }


    /**
     * 审核留言
     *
     * @param messageId
     * @param review
     * @return
     */
    @Override
    public Boolean auditMessage(Long messageId, Boolean review) {
        return baseMapper.update(null, new LambdaUpdateWrapper<Message>()
            .set(Message::getIsReview, review ? "Y" : "N")
            .eq(Message::getMessageId, messageId)) > 0;
    }

}
