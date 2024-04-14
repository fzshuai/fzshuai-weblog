package top.fzshuai.system.mapper;

import top.fzshuai.common.core.mapper.BaseMapperPlus;
import top.fzshuai.system.domain.SysSocialUser;
import top.fzshuai.system.domain.vo.SysSocialUserVo;

/**
 * 社交用户 Mapper 接口
 *
 * @author fzshuai
 * @date 2024/03/28 21:40
 * @since 1.0
 */
public interface SysSocialUserMapper extends BaseMapperPlus<SysSocialUserMapper, SysSocialUser, SysSocialUserVo> {

    /**
     * 根据 authId 查询社交用户
     *
     * @param authId 认证 ID
     * @return SysSocialUser
     */
    SysSocialUserVo selectByAuthId(String authId);

}
