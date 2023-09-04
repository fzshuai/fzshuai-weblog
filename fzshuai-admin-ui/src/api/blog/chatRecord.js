import request from '@/utils/request'

// 查询聊天记录列表
export function listChatRecord(query) {
  return request({
    url: '/blog/chatRecord/list',
    method: 'get',
    params: query
  })
}

// 查询聊天记录详细
export function getChatRecord(chatRecordId) {
  return request({
    url: '/blog/chatRecord/' + chatRecordId,
    method: 'get'
  })
}

// 新增聊天记录
export function addChatRecord(data) {
  return request({
    url: '/blog/chatRecord',
    method: 'post',
    data: data
  })
}

// 修改聊天记录
export function updateChatRecord(data) {
  return request({
    url: '/blog/chatRecord',
    method: 'put',
    data: data
  })
}

// 删除聊天记录
export function delChatRecord(chatRecordId) {
  return request({
    url: '/blog/chatRecord/' + chatRecordId,
    method: 'delete'
  })
}
