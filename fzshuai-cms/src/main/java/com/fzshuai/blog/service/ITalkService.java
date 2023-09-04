package com.fzshuai.blog.service;

import com.fzshuai.blog.domain.dto.TalkDTO;
import com.fzshuai.blog.domain.vo.PageResult;
import com.fzshuai.blog.domain.vo.TalkVo;
import com.fzshuai.blog.domain.bo.TalkBo;
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
     * @return {@link PageResult < TalkDTO >} 说说列表
     */
    PageResult<TalkDTO> listTalks();

    /**
     * 查询说说
     */
    TalkVo queryById(Long talkId);

    /**
     * 查询说说列表
     */
    TableDataInfo<TalkVo> queryPageList(TalkBo bo, PageQuery pageQuery);

    /**
     * 查询说说列表
     */
    List<TalkVo> queryList(TalkBo bo);

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
