package top.fzshuai.blog.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import top.fzshuai.blog.domain.Photo;
import top.fzshuai.blog.domain.vo.PhotoVO;
import top.fzshuai.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 照片Mapper接口
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public interface PhotoMapper extends BaseMapperPlus<PhotoMapper, Photo, PhotoVO> {

    @Override
    <P extends IPage<Photo>> P selectPage(P page, @Param(Constants.WRAPPER) Wrapper<Photo> queryWrapper);

    /**
     * 查询照片url列表
     */
    List<String> selectPhotoUrlListByAlbumId(@Param("albumId") Long albumId, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
}
