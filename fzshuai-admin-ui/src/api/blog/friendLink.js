import request from '@/utils/request'

// 查询友人链接列表
export function listFriendLink(query) {
  return request({
    url: '/blog/friendLink/list',
    method: 'get',
    params: query
  })
}

// 查询友人链接详细
export function getFriendLink(friendLinkId) {
  return request({
    url: '/blog/friendLink/' + friendLinkId,
    method: 'get'
  })
}

// 新增友人链接
export function addFriendLink(data) {
  return request({
    url: '/blog/friendLink',
    method: 'post',
    data: data
  })
}

// 修改友人链接
export function updateFriendLink(data) {
  return request({
    url: '/blog/friendLink',
    method: 'put',
    data: data
  })
}

// 删除友人链接
export function delFriendLink(friendLinkId) {
  return request({
    url: '/blog/friendLink/' + friendLinkId,
    method: 'delete'
  })
}
