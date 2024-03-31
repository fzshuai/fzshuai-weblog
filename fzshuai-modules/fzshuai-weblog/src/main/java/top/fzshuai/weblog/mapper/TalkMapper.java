package top.fzshuai.weblog.mapper;

import top.fzshuai.weblog.domain.Talk;
import top.fzshuai.weblog.domain.dto.TalkDto;
import top.fzshuai.weblog.domain.vo.TalkVo;
import top.fzshuai.common.core.mapper.BaseMapperPlus;
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
     * @return {@link List<  TalkDto  >}
     */
    List<TalkDto> selectTalkList(@Param("current") Long current, @Param("size") Long size);

    /**
     * 查看后台说说
     */
    List<TalkVo> selectAdminTalkList();

    /**
     * 根据id查询后台说说
     */
    TalkVo selectAdminTalkById(Long id);

}
