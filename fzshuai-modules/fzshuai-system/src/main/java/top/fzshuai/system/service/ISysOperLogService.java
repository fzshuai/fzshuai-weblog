package top.fzshuai.system.service;

import top.fzshuai.common.core.domain.PageQuery;
import top.fzshuai.common.core.page.TableDataInfo;
import top.fzshuai.system.domain.SysOperLog;

import java.util.List;

/**
 * 操作日志 服务层
 *
 * @author Lion Li fzshuai
 */
public interface ISysOperLogService {

    TableDataInfo<SysOperLog> queryPageOperLogList(SysOperLog operLog, PageQuery pageQuery);

    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    void insertOperlog(SysOperLog operLog);

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    List<SysOperLog> queryOperLogList(SysOperLog operLog);

    /**
     * 批量删除系统操作日志
     *
     * @param operIds 需要删除的操作日志ID
     * @return 结果
     */
    int deleteOperLogByIds(Long[] operIds);

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    SysOperLog queryOperLogById(Long operId);

    /**
     * 清空操作日志
     */
    void cleanOperLog();
}
