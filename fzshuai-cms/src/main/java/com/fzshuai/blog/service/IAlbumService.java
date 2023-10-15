package com.fzshuai.blog.service;

import com.fzshuai.blog.domain.bo.AlbumBO;
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
     * @return 相册列表
     */
    List<AlbumVO> selectAlbumList();

    /**
     * 查询相册管理
     *
     * @param photoAlbumId 相册id
     * @return 相册
     */
    AlbumVO selectAlbumById(Long photoAlbumId);

    /**
     * 查询相册列表
     *
     * @param bo        相册对象
     * @param pageQuery 分页对象
     * @return 相册列表
     */
    TableDataInfo<AlbumVO> selectAlbumPage(AlbumBO bo, PageQuery pageQuery);

    /**
     * 查询相册列表
     *
     * @param bo 相册对象
     * @return 相册列表
     */
    List<AlbumVO> selectAlbumList(AlbumBO bo);

    /**
     * 新增相册
     *
     * @param bo 相册对象
     * @return 是否成功
     */
    Boolean insertByBo(AlbumBO bo);

    /**
     * 修改相册
     *
     * @param bo 相册对象
     * @return 是否成功
     */
    Boolean updateByBo(AlbumBO bo);

    /**
     * 校验并批量删除相册信息
     *
     * @param ids     相册id
     * @param isValid 是否校验
     * @return 是否成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
