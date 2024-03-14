package top.fzshuai.blog.service;

import top.fzshuai.blog.domain.vo.ChatRecordVO;
import top.fzshuai.blog.domain.bo.ChatRecordBO;
import top.fzshuai.common.core.page.TableDataInfo;
import top.fzshuai.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 聊天记录Service接口
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public interface IChatRecordService {

    /**
     * 查询聊天记录
     */
    ChatRecordVO queryById(Long chatRecordId);

    /**
     * 查询聊天记录列表
     */
    TableDataInfo<ChatRecordVO> queryPageList(ChatRecordBO bo, PageQuery pageQuery);

    /**
     * 查询聊天记录列表
     */
    List<ChatRecordVO> queryList(ChatRecordBO bo);

    /**
     * 新增聊天记录
     */
    Boolean insertByBo(ChatRecordBO bo);

    /**
     * 修改聊天记录
     */
    Boolean updateByBo(ChatRecordBO bo);

    /**
     * 校验并批量删除聊天记录信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
