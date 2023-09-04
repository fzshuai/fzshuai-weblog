import request from '@/utils/request'

// 查询留言列表
export function listMessage(query) {
  return request({
    url: '/blog/message/list',
    method: 'get',
    params: query
  })
}

// 查询留言详细
export function getMessage(messageId) {
  return request({
    url: '/blog/message/' + messageId,
    method: 'get'
  })
}

// 新增留言
export function addMessage(data) {
  return request({
    url: '/blog/message',
    method: 'post',
    data: data
  })
}

// 修改留言
export function updateMessage(data) {
  return request({
    url: '/blog/message',
    method: 'put',
    data: data
  })
}

// 删除留言
export function delMessage(messageId) {
  return request({
    url: '/blog/message/' + messageId,
    method: 'delete'
  })
}
