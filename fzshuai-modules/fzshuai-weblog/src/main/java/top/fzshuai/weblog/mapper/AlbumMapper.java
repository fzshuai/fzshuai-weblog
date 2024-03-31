package top.fzshuai.weblog.mapper;

import top.fzshuai.weblog.domain.Album;
import top.fzshuai.weblog.domain.vo.AlbumVo;
import top.fzshuai.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

/**
 * 相册Mapper接口
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public interface AlbumMapper extends BaseMapperPlus<AlbumMapper, Album, AlbumVo> {

    /**
     * 查询相册内照片数量
     *
     * @param albumId 相册id
     * @return 相册内的照片数量
     */
    Integer selectPhotoCountByAlbumId(@Param("albumId") Long albumId);

}
