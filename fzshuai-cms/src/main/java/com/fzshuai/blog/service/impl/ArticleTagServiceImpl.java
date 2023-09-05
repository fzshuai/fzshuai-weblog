package com.fzshuai.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzshuai.common.core.page.TableDataInfo;
import com.fzshuai.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.fzshuai.blog.domain.bo.ArticleTagBO;
import com.fzshuai.blog.domain.vo.ArticleTagVO;
import com.fzshuai.blog.domain.ArticleTag;
import com.fzshuai.blog.mapper.ArticleTagMapper;
import com.fzshuai.blog.service.IArticleTagService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 文章和文章标签关联Service业务层处理
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@RequiredArgsConstructor
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements IArticleTagService {

    private final ArticleTagMapper baseMapper;

    /**
     * 查询文章和文章标签关联
     */
    @Override
    public ArticleTagVO queryById(Long articleId){
        return baseMapper.selectVoById(articleId);
    }

    /**
     * 查询文章和文章标签关联列表
     */
    @Override
    public TableDataInfo<ArticleTagVO> queryPageList(ArticleTagBO bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ArticleTag> lqw = buildQueryWrapper(bo);
        Page<ArticleTagVO> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询文章和文章标签关联列表
     */
    @Override
    public List<ArticleTagVO> queryList(ArticleTagBO bo) {
        LambdaQueryWrapper<ArticleTag> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ArticleTag> buildQueryWrapper(ArticleTagBO bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ArticleTag> lqw = Wrappers.lambdaQuery();
        return lqw;
    }

    /**
     * 新增文章和文章标签关联
     */
    @Override
    public Boolean insertByBo(ArticleTagBO bo) {
        ArticleTag add = BeanUtil.toBean(bo, ArticleTag.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setArticleId(add.getArticleId());
        }
        return flag;
    }

    /**
     * 修改文章和文章标签关联
     */
    @Override
    public Boolean updateByBo(ArticleTagBO bo) {
        ArticleTag update = BeanUtil.toBean(bo, ArticleTag.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ArticleTag entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除文章和文章标签关联
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
