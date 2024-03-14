package top.fzshuai.blog.service;

import top.fzshuai.blog.domain.bo.PhotoBO;
import top.fzshuai.blog.domain.dto.FrontPhotoDto;
import top.fzshuai.blog.domain.dto.PhotoDTO;
import top.fzshuai.blog.domain.dto.UpdateAlbumDto;
import top.fzshuai.blog.domain.vo.PhotoVO;
import top.fzshuai.common.core.domain.PageQuery;
import top.fzshuai.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 照片Service接口
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public interface IPhotoService {

    /**
     * 前台根据相册id查看照片列表
     *
     * @param albumId 相册id
     * @return {@link List<FrontPhotoDto>} 照片列表
     */
    FrontPhotoDto selectPhotoByAlbumId(Long albumId, PageQuery pageQuery);

    /**
     * 查询照片
     */
    PhotoVO selectPhotoById(Long photoId);

    /**
     * 查询照片列表
     */
    TableDataInfo<PhotoVO> selectPhotoPageList(PhotoBO bo, PageQuery pageQuery);

    /**
     * 查询照片列表
     */
    List<PhotoVO> selectPhotoList(PhotoBO bo);

    /**
     * 新增照片
     */
    Boolean insertByBo(PhotoDTO bo);

    /**
     * 修改照片
     */
    Boolean updateByBo(PhotoBO bo);

    /**
     * 修改照片相册
     */
    Boolean updateByBo(UpdateAlbumDto updateAlbumDto);

    /**
     * 校验并批量删除照片信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
