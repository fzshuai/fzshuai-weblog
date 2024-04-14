package top.fzshuai.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.fzshuai.common.utils.BeanCopyUtils;
import top.fzshuai.system.domain.SysSocialUser;
import top.fzshuai.system.domain.bo.SysSocialUserBo;
import top.fzshuai.system.domain.vo.SysSocialUserVo;
import top.fzshuai.system.mapper.SysSocialUserMapper;
import top.fzshuai.system.service.ISysSocialUserService;

import java.util.List;

/**
 * 社交用户 业务层处理
 *
 * @author fzshuai
 * @date 2024/03/28 21:47
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class SysSocialUserServiceImpl implements ISysSocialUserService {

    private final SysSocialUserMapper baseMapper;

    /**
     * 查询社交用户
     */
    @Override
    public SysSocialUserVo queryById(String id) {
        return baseMapper.selectVoById(id);
    }

    /**
     * 授权列表
     */
    @Override
    public List<SysSocialUserVo> queryList() {
        List<SysSocialUser> socialUserList = baseMapper.selectList(new LambdaQueryWrapper<SysSocialUser>()
            .orderByDesc(SysSocialUser::getSocialId));
        return BeanCopyUtils.copyList(socialUserList, SysSocialUserVo.class);
    }

    @Override
    public List<SysSocialUserVo> queryListByUserId(Long userId) {
        return baseMapper.selectVoList(new LambdaQueryWrapper<SysSocialUser>().eq(SysSocialUser::getUserId, userId));
    }

    /**
     * 新增社交用户
     */
    @Override
    public Boolean insertByBo(SysSocialUserBo bo) {
        SysSocialUser add = BeanCopyUtils.copy(bo, SysSocialUser.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            if (add != null) {
                bo.setSocialId(add.getSocialId());
            } else {
                return false;
            }
        }
        return flag;
    }

    /**
     * 更新社交用户
     */
    @Override
    public Boolean updateByBo(SysSocialUserBo bo) {
        SysSocialUser update = BeanCopyUtils.copy(bo, SysSocialUser.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysSocialUser entity) {
        // TODO 做一些数据校验,如唯一约束
    }

    /**
     * 删除社交用户
     */
    @Override
    public Boolean deleteWithValidById(Long id) {
        return baseMapper.deleteById(id) > 0;
    }

    /**
     * 根据 authId 查询社交用户
     *
     * @param authId 认证id
     * @return 社交用户
     */
    @Override
    public SysSocialUserVo selectByAuthId(String authId) {
        return baseMapper.selectByAuthId(authId);
    }

}
