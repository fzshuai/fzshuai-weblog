import request from '@/utils/request'

// 查询说说列表
export function listTalk(query) {
  return request({
    url: '/blog/talk/list',
    method: 'get',
    params: query
  })
}

// 查询说说详细
export function getTalk(talkId) {
  return request({
    url: '/blog/talk/' + talkId,
    method: 'get'
  })
}

// 新增说说
export function addTalk(data) {
  return request({
    url: '/blog/talk',
    method: 'post',
    data: data
  })
}

// 修改说说
export function updateTalk(data) {
  return request({
    url: '/blog/talk',
    method: 'put',
    data: data
  })
}

// 删除说说
export function delTalk(talkId) {
  return request({
    url: '/blog/talk/' + talkId,
    method: 'delete'
  })
}
