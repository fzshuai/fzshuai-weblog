package top.fzshuai.blog.service;

import top.fzshuai.blog.domain.dto.TalkDTO;
import top.fzshuai.blog.domain.vo.PageResultVO;
import top.fzshuai.blog.domain.vo.TalkVO;
import top.fzshuai.blog.domain.bo.TalkBO;
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
    List<String> selectTalkHomeList();

    /**
     * 获取前端说说列表
     *
     * @return 说说列表
     */
    PageResultVO<TalkDTO> selectTalkPageList();

    /**
     * 查询说说
     */
    TalkVO selectTalkById(Long talkId);

    /**
     * 查询说说列表
     */
    TableDataInfo<TalkVO> selectTalkPageList(TalkBO bo, PageQuery pageQuery);

    /**
     * 查询说说列表
     */
    List<TalkVO> selectTalkList(TalkBO bo);

    /**
     * 新增说说
     */
    Boolean insertByBo(TalkBO bo);

    /**
     * 修改说说
     */
    Boolean updateByBo(TalkBO bo);

    /**
     * 校验并批量删除说说信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
