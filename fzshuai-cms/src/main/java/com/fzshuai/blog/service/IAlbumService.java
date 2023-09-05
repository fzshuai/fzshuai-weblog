package com.fzshuai.blog.service;

import com.fzshuai.blog.domain.bo.AlbumBO;
import com.fzshuai.blog.domain.dto.AlbumDTO;
import com.fzshuai.blog.domain.vo.AlbumVO;
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
    AlbumVO queryById(Long photoAlbumId);

    /**
     * 查询相册列表
     */
    TableDataInfo<AlbumVO> queryPageList(AlbumBO bo, PageQuery pageQuery);

    /**
     * 查询相册列表
     */
    List<AlbumVO> queryList(AlbumBO bo);

    /**
     * 新增相册
     */
    Boolean insertByBo(AlbumBO bo);

    /**
     * 修改相册
     */
    Boolean updateByBo(AlbumBO bo);

    /**
     * 校验并批量删除相册信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
