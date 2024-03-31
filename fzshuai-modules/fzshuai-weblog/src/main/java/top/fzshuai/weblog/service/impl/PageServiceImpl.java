package top.fzshuai.weblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import top.fzshuai.weblog.domain.Page;
import top.fzshuai.weblog.domain.bo.PageBo;
import top.fzshuai.weblog.domain.vo.PageVo;
import top.fzshuai.weblog.mapper.PageMapper;
import top.fzshuai.weblog.service.IBlogPageService;
import top.fzshuai.common.core.domain.PageQuery;
import top.fzshuai.common.core.page.TableDataInfo;
import top.fzshuai.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 博客页面Service业务层处理
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@RequiredArgsConstructor
@Service
public class PageServiceImpl implements IBlogPageService {

    private final PageMapper baseMapper;

    /**
     * 查询博客页面
     */
    @Override
    public PageVo selectPageById(Long pageId) {
        return baseMapper.selectVoById(pageId);
    }

    /**
     * 查询博客页面列表
     */
    @Override
    public TableDataInfo<PageVo> selectPageList(PageBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Page> lqw = buildQueryWrapper(bo);
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<PageVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询博客页面列表
     */
    @Override
    public List<PageVo> selectPageList(PageBo bo) {
        LambdaQueryWrapper<Page> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Page> buildQueryWrapper(PageBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Page> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getPageName()), Page::getPageName, bo.getPageName());
        lqw.eq(StringUtils.isNotBlank(bo.getPageLabel()), Page::getPageLabel, bo.getPageLabel());
        lqw.eq(StringUtils.isNotBlank(bo.getPageCover()), Page::getPageCover, bo.getPageCover());
        return lqw;
    }

    /**
     * 新增博客页面
     */
    @Override
    public Boolean insertByBo(PageBo bo) {
        Page add = BeanUtil.toBean(bo, Page.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setPageId(add.getPageId());
        }
        return flag;
    }

    /**
     * 修改博客页面
     */
    @Override
    public Boolean updateByBo(PageBo bo) {
        Page update = BeanUtil.toBean(bo, Page.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Page entity) {
        // TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除博客页面
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            // TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
