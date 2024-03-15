package top.fzshuai.blog.service.impl;

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
import top.fzshuai.blog.domain.bo.FriendLinkBO;
import top.fzshuai.blog.domain.vo.FriendLinkVO;
import top.fzshuai.blog.domain.FriendLink;
import top.fzshuai.blog.mapper.FriendLinkMapper;
import top.fzshuai.blog.service.IFriendLinkService;

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
    public List<FriendLinkVO> selectFriendLinkList() {
        // 查询友链列表
        List<FriendLink> friendLinkList = baseMapper.selectList(null);
        return BeanCopyUtils.copyList(friendLinkList, FriendLinkVO.class);
    }

    /**
     * 查询友人链接
     */
    @Override
    public FriendLinkVO selectFriendLinkById(Long friendLinkId) {
        return baseMapper.selectVoById(friendLinkId);
    }

    /**
     * 查询友人链接列表
     */
    @Override
    public TableDataInfo<FriendLinkVO> selectFriendLinkPageList(FriendLinkBO bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FriendLink> lqw = buildQueryWrapper(bo);
        Page<FriendLinkVO> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询友人链接列表
     */
    @Override
    public List<FriendLinkVO> selectFriendLinkList(FriendLinkBO bo) {
        LambdaQueryWrapper<FriendLink> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FriendLink> buildQueryWrapper(FriendLinkBO bo) {
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
    public Boolean insertByBo(FriendLinkBO bo) {
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
    public Boolean updateByBo(FriendLinkBO bo) {
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
