package top.fzshuai.weblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.fzshuai.weblog.domain.ArticleTag;
import top.fzshuai.weblog.domain.dto.TagDto;
import top.fzshuai.weblog.domain.vo.PageResultVo;
import top.fzshuai.weblog.mapper.ArticleTagMapper;
import top.fzshuai.common.exception.ServiceException;
import top.fzshuai.common.utils.BeanCopyUtils;
import top.fzshuai.common.utils.StringUtils;
import top.fzshuai.common.core.page.TableDataInfo;
import top.fzshuai.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.fzshuai.weblog.domain.bo.TagBo;
import top.fzshuai.weblog.domain.vo.TagVo;
import top.fzshuai.weblog.domain.Tag;
import top.fzshuai.weblog.mapper.TagMapper;
import top.fzshuai.weblog.service.ITagService;

import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.Objects;

/**
 * 文章标签Service业务层处理
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@RequiredArgsConstructor
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    private final TagMapper baseMapper;
    private final ArticleTagMapper articleTagMapper;

    /**
     * 查询前台标签
     *
     * @return
     */
    @Override
    public PageResultVo<TagDto> selectTagList() {
        // 查询标签列表
        List<Tag> tagList = baseMapper.selectList(null);
        // 转换DTO
        List<TagDto> tagDtoList = BeanCopyUtils.copyList(tagList, TagDto.class);
        // 查询标签数量
        Long count = baseMapper.selectCount(null);
        return new PageResultVo<>(tagDtoList, Integer.parseInt(String.valueOf(count)));
    }

    /**
     * 查询文章标签
     */
    @Override
    public TagVo selectTagById(Long tagId) {
        return baseMapper.selectVoById(tagId);
    }

    /**
     * 查询文章标签列表
     */
    @Override
    public TableDataInfo<TagVo> selectTagPageList(TagBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Tag> lqw = buildQueryWrapper(bo);
        Page<TagVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询文章标签列表
     */
    @Override
    public List<TagVo> selectTagList(TagBo bo) {
        LambdaQueryWrapper<Tag> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Tag> buildQueryWrapper(TagBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Tag> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getTagName()), Tag::getTagName, bo.getTagName());
        return lqw;
    }

    /**
     * 新增文章标签
     */
    @Override
    public Boolean insertByBo(TagBo bo) {
        Tag add = BeanUtil.toBean(bo, Tag.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setTagId(add.getTagId());
        }
        return flag;
    }

    /**
     * 修改文章标签
     */
    @Override
    public Boolean updateByBo(TagBo bo) {
        Tag update = BeanUtil.toBean(bo, Tag.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Tag tag) {
        // TODO 做一些数据校验,如唯一约束
        // 查询标签名是否存在
        Tag existTag = baseMapper.selectOne(new LambdaQueryWrapper<Tag>()
            .select(Tag::getTagId)
            .eq(Tag::getTagName, tag.getTagName()));
        if (Objects.nonNull(existTag) && !existTag.getTagId().equals(tag.getTagId())) {
            throw new ServiceException("标签名已存在");
        }
    }

    /**
     * 批量删除文章标签
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> tagIds, Boolean isValid) {
        if (isValid) {
            // TODO 做一些业务上的校验,判断是否需要校验
        }
        Long count = articleTagMapper.selectCount(new LambdaQueryWrapper<ArticleTag>()
            .in(ArticleTag::getTagId, tagIds));
        if (count > 0) {
            return false;
        }
        return baseMapper.deleteBatchIds(tagIds) > 0;
    }

}
