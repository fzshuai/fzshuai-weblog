package top.fzshuai.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.fzshuai.blog.domain.Album;
import top.fzshuai.blog.domain.Photo;
import top.fzshuai.blog.domain.bo.AlbumBo;
import top.fzshuai.blog.domain.vo.AlbumVo;
import top.fzshuai.blog.domain.vo.PhotoVo;
import top.fzshuai.blog.mapper.AlbumMapper;
import top.fzshuai.blog.mapper.PhotoMapper;
import top.fzshuai.blog.service.IAlbumService;
import top.fzshuai.common.core.domain.PageQuery;
import top.fzshuai.common.core.page.TableDataInfo;
import top.fzshuai.common.exception.base.BaseException;
import top.fzshuai.common.utils.BeanCopyUtils;
import top.fzshuai.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import static top.fzshuai.blog.enums.PhotoAlbumStatusEnum.PUBLIC;
import static top.fzshuai.common.constant.BlogConstant.FALSE;

/**
 * 相册Service业务层处理
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@RequiredArgsConstructor
@Service
public class AlbumServiceImpl implements IAlbumService {

    private final AlbumMapper baseMapper;
    private final PhotoMapper photoMapper;

    @Override
    public List<AlbumVo> queryAlbumList() {
        // 查询相册列表
        List<Album> photoAlbumList = baseMapper.selectList(new LambdaQueryWrapper<Album>()
            .eq(Album::getStatus, PUBLIC.getStatus())
            .eq(Album::getIsDelete, FALSE)
            .orderByDesc(Album::getAlbumId));
        return BeanCopyUtils.copyList(photoAlbumList, AlbumVo.class);
    }

    /**
     * 查询相册
     */
    @Override
    public AlbumVo queryAlbumById(Long albumId) {
        AlbumVo albumVo = baseMapper.selectVoById(albumId);
        if (Objects.isNull(albumVo)) {
            throw new BaseException("相册不存在或已被删除");
        }
        albumVo.setPhotoCount(baseMapper.selectPhotoCountByAlbumId(albumId));
        return albumVo;
    }

    /**
     * 查询相册列表
     */
    @Override
    public TableDataInfo<AlbumVo> queryAlbumPageList(AlbumBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Album> lqw = buildQueryWrapper(bo);
        Page<AlbumVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询相册列表
     */
    @Override
    public List<AlbumVo> queryAlbumList(AlbumBo bo) {
        LambdaQueryWrapper<Album> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Album> buildQueryWrapper(AlbumBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Album> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getAlbumName()), Album::getAlbumName, bo.getAlbumName());
        lqw.eq(StringUtils.isNotBlank(bo.getAlbumDesc()), Album::getAlbumDesc, bo.getAlbumDesc());
        lqw.eq(StringUtils.isNotBlank(bo.getAlbumCover()), Album::getAlbumCover, bo.getAlbumCover());
        lqw.eq(bo.getIsDelete() != null, Album::getIsDelete, bo.getIsDelete());
        lqw.eq(bo.getStatus() != null, Album::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增相册
     */
    @Override
    public Boolean insertByBo(AlbumBo bo) {
        Album add = BeanUtil.toBean(bo, Album.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setAlbumId(add.getAlbumId());
        }
        return flag;
    }

    /**
     * 修改相册
     */
    @Override
    public Boolean updateByBo(AlbumBo bo) {
        Album update = BeanUtil.toBean(bo, Album.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Album entity) {
        // TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除相册
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            // TODO 做一些业务上的校验,判断是否需要校验
        }
        // 删除相册当前删除相册内的照片
        // 查询相册内所有照片
        List<PhotoVo> photoVoList = photoMapper.selectVoList(new LambdaQueryWrapper<Photo>().in(Photo::getAlbumId, ids));
        List<Long> photoIdList = new ArrayList<>();
        //
        photoVoList.forEach(photoVo -> {
            photoIdList.add(photoVo.getPhotoId());
        });
        // 删除相册对应的照片
        if (ObjectUtil.isNotEmpty(photoIdList)) {
            photoMapper.deleteBatchIds(photoIdList);
        }
        // 删除相册
        return baseMapper.deleteBatchIds(ids) > 0;
    }

}
