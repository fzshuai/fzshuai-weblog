import request from '@/utils/request'

// 查询访问量列表
export function listUniqueView(query) {
  return request({
    url: '/blog/uniqueView/list',
    method: 'get',
    params: query
  })
}

// 查询访问量详细
export function getUniqueView(uniqueViewId) {
  return request({
    url: '/blog/uniqueView/' + uniqueViewId,
    method: 'get'
  })
}

// 新增访问量
export function addUniqueView(data) {
  return request({
    url: '/blog/uniqueView',
    method: 'post',
    data: data
  })
}

// 修改访问量
export function updateUniqueView(data) {
  return request({
    url: '/blog/uniqueView',
    method: 'put',
    data: data
  })
}

// 删除访问量
export function delUniqueView(uniqueViewId) {
  return request({
    url: '/blog/uniqueView/' + uniqueViewId,
    method: 'delete'
  })
}
