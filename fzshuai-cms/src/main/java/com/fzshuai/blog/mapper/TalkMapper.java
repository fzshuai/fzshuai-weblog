package com.fzshuai.blog.mapper;

import com.fzshuai.blog.domain.Talk;
import com.fzshuai.blog.domain.dto.TalkDTO;
import com.fzshuai.blog.domain.vo.TalkVo;
import com.fzshuai.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 说说Mapper接口
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public interface TalkMapper extends BaseMapperPlus<TalkMapper, Talk, TalkVo> {

    /**
     * 获取说说列表
     *
     * @param current 页码
     * @param size    大小
     * @return {@link List< TalkDTO >}
     */
    List<TalkDTO> listTalks(@Param("current") Long current, @Param("size") Long size);

    /**
     * 查看后台说说
     */
    List<TalkVo> listBackTalks();

    /**
     * 根据id查询后台说说
     */
    TalkVo BackTalks(Long id);
}
