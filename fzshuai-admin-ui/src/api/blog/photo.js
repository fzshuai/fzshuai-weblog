import request from '@/utils/request'

// 查询照片列表
export function listPhoto(query) {
  return request({
    url: '/blog/photo/list',
    method: 'get',
    params: query
  })
}

// 查询照片详细
export function getPhoto(photoId) {
  return request({
    url: '/blog/photo/' + photoId,
    method: 'get'
  })
}

// 新增照片
export function addPhoto(data) {
  return request({
    url: '/blog/photo',
    method: 'post',
    data: data
  })
}

// 修改照片
export function updatePhoto(data) {
  return request({
    url: '/blog/photo',
    method: 'put',
    data: data
  })
}

// 删除照片
export function delPhoto(photoId) {
  return request({
    url: '/blog/photo/' + photoId,
    method: 'delete'
  })
}
