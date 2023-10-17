package com.fzshuai.blog.service;

import com.fzshuai.blog.domain.bo.FriendLinkBO;
import com.fzshuai.blog.domain.vo.FriendLinkVO;
import com.fzshuai.common.core.domain.PageQuery;
import com.fzshuai.common.core.page.TableDataInfo;

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
    List<FriendLinkVO> selectFriendLinkList();

    /**
     * 查询友人链接
     */
    FriendLinkVO selectFriendLinkById(Long friendLinkId);

    /**
     * 查询友人链接列表
     */
    TableDataInfo<FriendLinkVO> selectFriendLinkPageList(FriendLinkBO bo, PageQuery pageQuery);

    /**
     * 查询友人链接列表
     */
    List<FriendLinkVO> selectFriendLinkList(FriendLinkBO bo);

    /**
     * 新增友人链接
     */
    Boolean insertByBo(FriendLinkBO bo);

    /**
     * 修改友人链接
     */
    Boolean updateByBo(FriendLinkBO bo);

    /**
     * 校验并批量删除友人链接信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
