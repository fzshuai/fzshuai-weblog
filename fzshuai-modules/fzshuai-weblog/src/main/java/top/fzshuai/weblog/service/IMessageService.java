package top.fzshuai.weblog.service;

import top.fzshuai.weblog.domain.vo.MessageVo;
import top.fzshuai.weblog.domain.bo.MessageBo;
import top.fzshuai.common.core.page.TableDataInfo;
import top.fzshuai.common.core.domain.PageQuery;

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
    void insertMessage(MessageVo messageVo);

    /**
     * 查看前台留言弹幕
     *
     * @return 留言列表
     */
    List<MessageVo> selectMessageList();

    /**
     * 查询留言
     */
    MessageVo selectMessageById(Long messageId);

    /**
     * 查询留言列表
     */
    TableDataInfo<MessageVo> selectMessagePageList(MessageBo bo, PageQuery pageQuery);

    /**
     * 查询留言列表
     */
    List<MessageVo> selectMessageList(MessageBo bo);

    /**
     * 新增留言
     */
    Boolean insertByBo(MessageBo bo);

    /**
     * 修改留言
     */
    Boolean updateByBo(MessageBo bo);

    /**
     * 校验并批量删除留言信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 审核留言
     */
    Boolean auditMessage(Long id, Boolean review);

}
