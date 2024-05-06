package top.fzshuai.system.service;

import top.fzshuai.system.domain.bo.SysSocialUserBo;
import top.fzshuai.system.domain.vo.SysSocialUserVo;

import java.util.List;

/**
 * @author fzshuai
 * @date 2024/03/28 21:46
 * @since 1.0
 */
public interface ISysSocialUserService {

    /**
     * 查询社交用户
     */
    SysSocialUserVo queryById(String id);

    /**
     * 查询社交用户列表
     */
    List<SysSocialUserVo> queryList();

    /**
     * 查询社交用户列表
     */
    List<SysSocialUserVo> queryListByUserId(Long userId);

    /**
     * 根据 authId 查询社交用户
     */
    SysSocialUserVo queryByAuthId(String authId);

    /**
     * 新增社交用户
     */
    Boolean insertByBo(SysSocialUserBo bo);

    /**
     * 更新社交用户
     */
    Boolean updateByBo(SysSocialUserBo bo);

    /**
     * 删除社交用户
     */
    Boolean deleteWithValidById(Long id);

}
