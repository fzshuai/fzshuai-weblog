import request from '@/utils/request'

// 查询页面列表
export function listPage(query) {
  return request({
    url: '/blog/page/list',
    method: 'get',
    params: query
  })
}

// 查询页面详细
export function getPage(pageId) {
  return request({
    url: '/blog/page/' + pageId,
    method: 'get'
  })
}

// 新增页面
export function addPage(data) {
  return request({
    url: '/blog/page',
    method: 'post',
    data: data
  })
}

// 修改页面
export function updatePage(data) {
  return request({
    url: '/blog/page',
    method: 'put',
    data: data
  })
}

// 删除页面
export function delPage(pageId) {
  return request({
    url: '/blog/page/' + pageId,
    method: 'delete'
  })
}
