package com.fzshuai.blog.mapper;

import com.fzshuai.blog.domain.Album;
import com.fzshuai.blog.domain.vo.AlbumVO;
import com.fzshuai.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 相册Mapper接口
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public interface AlbumMapper extends BaseMapperPlus<AlbumMapper, Album, AlbumVO> {

    /**
     * 查询相册内照片数量
     *
     * @param albumId 相册id
     * @return 相册内的照片数量
     */
    Integer selectPhotoCountByAlbumId(@Param("albumId") Long albumId);
}
