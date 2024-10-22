package top.fzshuai.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.fzshuai.blog.domain.Photo;
import top.fzshuai.blog.domain.Album;
import top.fzshuai.blog.domain.bo.PhotoBo;
import top.fzshuai.blog.domain.dto.FrontPhotoDto;
import top.fzshuai.blog.domain.dto.PhotoDto;
import top.fzshuai.blog.domain.dto.UpdateAlbumDto;
import top.fzshuai.blog.domain.vo.PhotoVo;
import top.fzshuai.blog.mapper.AlbumMapper;
import top.fzshuai.blog.mapper.PhotoMapper;
import top.fzshuai.blog.service.IPhotoService;
import top.fzshuai.common.core.domain.PageQuery;
import top.fzshuai.common.core.page.TableDataInfo;
import top.fzshuai.common.exception.base.BaseException;
import top.fzshuai.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static top.fzshuai.blog.enums.PhotoAlbumStatusEnum.PUBLIC;
import static top.fzshuai.common.constant.BlogConstant.FALSE;

/**
 * 照片Service业务层处理
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@RequiredArgsConstructor
@Service
public class PhotoServiceImpl implements IPhotoService {

    private final PhotoMapper baseMapper;
    private final AlbumMapper albumMapper;

    @Override
    public FrontPhotoDto queryPhotoByAlbumId(Long albumId, PageQuery pageQuery) {
        // 查询相册信息
        Album album = albumMapper.selectOne(new LambdaQueryWrapper<Album>()
            .eq(Album::getAlbumId, albumId)
            .eq(Album::getIsDelete, FALSE)
            .eq(Album::getStatus, PUBLIC.getStatus()));
        if (Objects.isNull(album)) {
            throw new BaseException("相册不存在");
        }
        List<String> photolist = baseMapper.selectPhotoUrlListByAlbumId(albumId, pageQuery.getPageNum(), pageQuery.getPageSize());
        FrontPhotoDto frontPhotoDto = FrontPhotoDto.builder()
            .photoAlbumCover(album.getAlbumCover())
            .photoAlbumName(album.getAlbumName())
            .photoList(photolist)
            .build();
        return frontPhotoDto;

    }

    /**
     * 查询照片
     */
    @Override
    public PhotoVo queryPhotoById(Long photoId) {
        return baseMapper.selectVoById(photoId);
    }

    /**
     * 查询照片列表
     */
    @Override
    public TableDataInfo<PhotoVo> queryPhotoPageList(PhotoBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Photo> lqw = buildQueryWrapper(bo);
        Page<PhotoVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询照片列表
     */
    @Override
    public List<PhotoVo> queryPhotoList(PhotoBo bo) {
        LambdaQueryWrapper<Photo> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Photo> buildQueryWrapper(PhotoBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Photo> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getAlbumId() != null, Photo::getAlbumId, bo.getAlbumId());
        lqw.like(StringUtils.isNotBlank(bo.getPhotoName()), Photo::getPhotoName, bo.getPhotoName());
        lqw.eq(StringUtils.isNotBlank(bo.getPhotoDesc()), Photo::getPhotoDesc, bo.getPhotoDesc());
        lqw.eq(StringUtils.isNotBlank(bo.getPhotoSrc()), Photo::getPhotoSrc, bo.getPhotoSrc());
        lqw.eq(bo.getIsDelete() != null, Photo::getIsDelete, bo.getIsDelete());
        return lqw;
    }

    /**
     * 新增照片
     */
    @Override
    public Boolean insertByBo(PhotoDto bo) {
        Boolean flag = true;
        for (int i = 0; i < bo.getPhotoUrlList().size(); i++) {
            Photo photo = new Photo();
            photo.setAlbumId(bo.getAlbumId());
            photo.setPhotoSrc(bo.getPhotoUrlList().get(i));
            photo.setPhotoName(bo.getPhotoNameList().get(i));
            flag = baseMapper.insert(photo) > 0;
        }
        return flag;
    }

    /**
     * 修改照片
     */
    @Override
    public Boolean updateByBo(PhotoBo bo) {
        Photo update = BeanUtil.toBean(bo, Photo.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }


    /**
     * 修改照片相册·
     */
    @Override
    public Boolean updateByBo(UpdateAlbumDto updateAlbumDto) {
        Boolean flag = true;
        for (Long id : updateAlbumDto.getIds()) {
            Photo photo = new Photo();
            photo.setPhotoId(id);
            photo.setAlbumId(updateAlbumDto.getAlbumId());
            flag = baseMapper.updateById(photo) > 0;
        }
        return flag;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Photo entity) {
        // TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除照片
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            // TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

}
