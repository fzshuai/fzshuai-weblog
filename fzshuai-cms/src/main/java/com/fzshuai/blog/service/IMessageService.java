package com.fzshuai.blog.service;

import com.fzshuai.blog.domain.dto.MessageDTO;
import com.fzshuai.blog.domain.vo.MessageVO;
import com.fzshuai.blog.domain.bo.MessageBO;
import com.fzshuai.common.core.page.TableDataInfo;
import com.fzshuai.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 留言Service接口
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public interface IMessageService {

    /**
     * 前台添加留言弹幕
     *
     * @param messageVo 留言对象
     */
    void saveMessage(MessageVO messageVo);

    /**
     * 查看前台留言弹幕
     *
     * @return 留言列表
     */
    List<MessageDTO> listMessages();

    /*
     * 查询留言
     */
    MessageVO queryById(Long messageId);

    /**
     * 查询留言列表
     */
    TableDataInfo<MessageVO> queryPageList(MessageBO bo, PageQuery pageQuery);

    /**
     * 查询留言列表
     */
    List<MessageVO> queryList(MessageBO bo);

    /**
     * 新增留言
     */
    Boolean insertByBo(MessageBO bo);

    /**
     * 修改留言
     */
    Boolean updateByBo(MessageBO bo);

    /**
     * 校验并批量删除留言信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 审核留言
     */
    Boolean auditMessage(Long id, Boolean review);
}
