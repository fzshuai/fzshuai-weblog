package top.fzshuai.weblog.service;

import top.fzshuai.weblog.domain.vo.ChatRecordVo;
import top.fzshuai.weblog.domain.bo.ChatRecordBo;
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
    ChatRecordVo queryById(Long chatRecordId);

    /**
     * 查询聊天记录列表
     */
    TableDataInfo<ChatRecordVo> queryPageList(ChatRecordBo bo, PageQuery pageQuery);

    /**
     * 查询聊天记录列表
     */
    List<ChatRecordVo> queryList(ChatRecordBo bo);

    /**
     * 新增聊天记录
     */
    Boolean insertByBo(ChatRecordBo bo);

    /**
     * 修改聊天记录
     */
    Boolean updateByBo(ChatRecordBo bo);

    /**
     * 校验并批量删除聊天记录信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
