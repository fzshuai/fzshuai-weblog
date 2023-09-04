package com.fzshuai.blog.mapper;

import com.fzshuai.blog.domain.Album;
import com.fzshuai.blog.domain.vo.AlbumVo;
import com.fzshuai.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Select;

/**
 * 相册Mapper接口
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public interface AlbumMapper extends BaseMapperPlus<AlbumMapper, Album, AlbumVo> {

    /**
     * 将文章图片转换为url
     */
    @Select("select o.url from sys_oss o where o.oss_id = #{url}")
    String ImgUrl(Long url);

    /**
     * 查询相册内照片数量
     */
    @Select("select COUNT(*) from blog_photo bp where bp.album_id = #{id}")
    Integer PhotoCount(Long id);
}
