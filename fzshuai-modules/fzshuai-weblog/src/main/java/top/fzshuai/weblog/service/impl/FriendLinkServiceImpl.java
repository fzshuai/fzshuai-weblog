package top.fzshuai.weblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import top.fzshuai.common.utils.BeanCopyUtils;
import top.fzshuai.common.utils.StringUtils;
import top.fzshuai.common.core.page.TableDataInfo;
import top.fzshuai.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.fzshuai.weblog.domain.bo.FriendLinkBo;
import top.fzshuai.weblog.domain.vo.FriendLinkVo;
import top.fzshuai.weblog.domain.FriendLink;
import top.fzshuai.weblog.mapper.FriendLinkMapper;
import top.fzshuai.weblog.service.IFriendLinkService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 友人链接Service业务层处理
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@RequiredArgsConstructor
@Service
public class FriendLinkServiceImpl implements IFriendLinkService {

    private final FriendLinkMapper baseMapper;

    /**
     * 查看前台友链
     *
     * @return
     */
    @Override
    public List<FriendLinkVo> selectFriendLinkList() {
        // 查询友链列表
        List<FriendLink> friendLinkList = baseMapper.selectList(null);
        return BeanCopyUtils.copyList(friendLinkList, FriendLinkVo.class);
    }

    /**
     * 查询友人链接
     */
    @Override
    public FriendLinkVo selectFriendLinkById(Long friendLinkId) {
        return baseMapper.selectVoById(friendLinkId);
    }

    /**
     * 查询友人链接列表
     */
    @Override
    public TableDataInfo<FriendLinkVo> selectFriendLinkPageList(FriendLinkBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FriendLink> lqw = buildQueryWrapper(bo);
        Page<FriendLinkVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询友人链接列表
     */
    @Override
    public List<FriendLinkVo> selectFriendLinkList(FriendLinkBo bo) {
        LambdaQueryWrapper<FriendLink> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FriendLink> buildQueryWrapper(FriendLinkBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FriendLink> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getLinkName()), FriendLink::getLinkName, bo.getLinkName());
        lqw.eq(StringUtils.isNotBlank(bo.getLinkAvatar()), FriendLink::getLinkAvatar, bo.getLinkAvatar());
        lqw.eq(StringUtils.isNotBlank(bo.getLinkAddress()), FriendLink::getLinkAddress, bo.getLinkAddress());
        lqw.eq(StringUtils.isNotBlank(bo.getLinkIntro()), FriendLink::getLinkIntro, bo.getLinkIntro());
        return lqw;
    }

    /**
     * 新增友人链接
     */
    @Override
    public Boolean insertByBo(FriendLinkBo bo) {
        FriendLink add = BeanUtil.toBean(bo, FriendLink.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setFriendLinkId(add.getFriendLinkId());
        }
        return flag;
    }

    /**
     * 修改友人链接
     */
    @Override
    public Boolean updateByBo(FriendLinkBo bo) {
        FriendLink update = BeanUtil.toBean(bo, FriendLink.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FriendLink entity) {
        // TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除友人链接
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            // TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

}
