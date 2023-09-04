package com.fzshuai.blog.service;

import com.fzshuai.blog.domain.bo.AlbumBo;
import com.fzshuai.blog.domain.dto.AlbumDTO;
import com.fzshuai.blog.domain.vo.AlbumVo;
import com.fzshuai.common.core.domain.PageQuery;
import com.fzshuai.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 相册Service接口
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public interface IAlbumService {

    /**
     * 前台获取相册列表
     *
     * @return {@link List<AlbumDTO>}相册列表
     */
    List<AlbumDTO> listPhotoAlbums();

    /**
     * 查询相册管理
     */
    AlbumVo queryById(Long photoAlbumId);

    /**
     * 查询相册列表
     */
    TableDataInfo<AlbumVo> queryPageList(AlbumBo bo, PageQuery pageQuery);

    /**
     * 查询相册列表
     */
    List<AlbumVo> queryList(AlbumBo bo);

    /**
     * 新增相册
     */
    Boolean insertByBo(AlbumBo bo);

    /**
     * 修改相册
     */
    Boolean updateByBo(AlbumBo bo);

    /**
     * 校验并批量删除相册信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
