package com.fzshuai.blog.service;

import com.fzshuai.blog.domain.dto.FriendLinkDTO;
import com.fzshuai.blog.domain.vo.FriendLinkVo;
import com.fzshuai.blog.domain.bo.FriendLinkBo;
import com.fzshuai.common.core.page.TableDataInfo;
import com.fzshuai.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 友人链接Service接口
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public interface IFriendLinkService {

    /**
     * 查看前台友链列表
     *
     * @return 友链列表
     */
    List<FriendLinkDTO> listFriendLinks();

    /**
     * 查询友人链接
     */
    FriendLinkVo queryById(Long friendLinkId);

    /**
     * 查询友人链接列表
     */
    TableDataInfo<FriendLinkVo> queryPageList(FriendLinkBo bo, PageQuery pageQuery);

    /**
     * 查询友人链接列表
     */
    List<FriendLinkVo> queryList(FriendLinkBo bo);

    /**
     * 新增友人链接
     */
    Boolean insertByBo(FriendLinkBo bo);

    /**
     * 修改友人链接
     */
    Boolean updateByBo(FriendLinkBo bo);

    /**
     * 校验并批量删除友人链接信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
