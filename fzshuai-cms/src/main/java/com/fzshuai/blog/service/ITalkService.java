package com.fzshuai.blog.service;

import com.fzshuai.blog.domain.dto.TalkDTO;
import com.fzshuai.blog.domain.vo.PageResultVO;
import com.fzshuai.blog.domain.vo.TalkVO;
import com.fzshuai.blog.domain.bo.TalkBO;
import com.fzshuai.common.core.page.TableDataInfo;
import com.fzshuai.common.core.domain.PageQuery;

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
     * @return {@link List<String>} 说说列表
     */
    List<String> listHomeTalks();

    /**
     * 获取前端1说说列表
     *
     * @return {@link PageResultVO < TalkDTO >} 说说列表
     */
    PageResultVO<TalkDTO> listTalks();

    /**
     * 查询说说
     */
    TalkVO queryById(Long talkId);

    /**
     * 查询说说列表
     */
    TableDataInfo<TalkVO> queryPageList(TalkBO bo, PageQuery pageQuery);

    /**
     * 查询说说列表
     */
    List<TalkVO> queryList(TalkBO bo);

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
