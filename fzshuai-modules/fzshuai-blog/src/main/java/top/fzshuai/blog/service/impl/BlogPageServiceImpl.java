package top.fzshuai.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import top.fzshuai.blog.domain.BlogPage;
import top.fzshuai.blog.domain.bo.BlogPageBo;
import top.fzshuai.blog.domain.vo.BlogPageVo;
import top.fzshuai.blog.mapper.BlogPageMapper;
import top.fzshuai.blog.service.IBlogPageService;
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
public class BlogPageServiceImpl implements IBlogPageService {

    private final BlogPageMapper baseMapper;

    /**
     * 查询博客页面
     */
    @Override
    public BlogPageVo queryPageById(Long pageId) {
        return baseMapper.selectVoById(pageId);
    }

    /**
     * 查询博客页面列表
     */
    @Override
    public TableDataInfo<BlogPageVo> queryPageList(BlogPageBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<BlogPage> lqw = buildQueryWrapper(bo);
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<BlogPageVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询博客页面列表
     */
    @Override
    public List<BlogPageVo> queryPageList(BlogPageBo bo) {
        LambdaQueryWrapper<BlogPage> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<BlogPage> buildQueryWrapper(BlogPageBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<BlogPage> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getPageName()), BlogPage::getPageName, bo.getPageName());
        lqw.eq(StringUtils.isNotBlank(bo.getPageLabel()), BlogPage::getPageLabel, bo.getPageLabel());
        lqw.eq(StringUtils.isNotBlank(bo.getPageCover()), BlogPage::getPageCover, bo.getPageCover());
        return lqw;
    }

    /**
     * 新增博客页面
     */
    @Override
    public Boolean insertByBo(BlogPageBo bo) {
        BlogPage add = BeanUtil.toBean(bo, BlogPage.class);
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
    public Boolean updateByBo(BlogPageBo bo) {
        BlogPage update = BeanUtil.toBean(bo, BlogPage.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(BlogPage entity) {
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
