package top.fzshuai.blog.service;

import top.fzshuai.blog.domain.dto.TalkDto;
import top.fzshuai.blog.domain.vo.PageResultVo;
import top.fzshuai.blog.domain.vo.TalkVo;
import top.fzshuai.blog.domain.bo.TalkBo;
import top.fzshuai.common.core.page.TableDataInfo;
import top.fzshuai.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 说说Service接口
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public interface ITalkService {

    /**
     * 获取首页说说列表
     *
     * @return 说说列表
     */
    List<String> queryTalkHomeList();

    /**
     * 获取前端说说列表
     *
     * @return 说说列表
     */
    PageResultVo<TalkDto> queryTalkPageList();

    /**
     * 查询说说
     */
    TalkVo queryTalkById(Long talkId);

    /**
     * 查询说说列表
     */
    TableDataInfo<TalkVo> queryTalkPageList(TalkBo bo, PageQuery pageQuery);

    /**
     * 查询说说列表
     */
    List<TalkVo> queryTalkList(TalkBo bo);

    /**
     * 新增说说
     */
    Boolean insertByBo(TalkBo bo);

    /**
     * 修改说说
     */
    Boolean updateByBo(TalkBo bo);

    /**
     * 校验并批量删除说说信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
