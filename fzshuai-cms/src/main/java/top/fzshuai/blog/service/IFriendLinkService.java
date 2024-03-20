package top.fzshuai.blog.service;

import top.fzshuai.blog.domain.bo.FriendLinkBo;
import top.fzshuai.blog.domain.vo.FriendLinkVo;
import top.fzshuai.common.core.domain.PageQuery;
import top.fzshuai.common.core.page.TableDataInfo;

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
    List<FriendLinkVo> selectFriendLinkList();

    /**
     * 查询友人链接
     */
    FriendLinkVo selectFriendLinkById(Long friendLinkId);

    /**
     * 查询友人链接列表
     */
    TableDataInfo<FriendLinkVo> selectFriendLinkPageList(FriendLinkBo bo, PageQuery pageQuery);

    /**
     * 查询友人链接列表
     */
    List<FriendLinkVo> selectFriendLinkList(FriendLinkBo bo);

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
