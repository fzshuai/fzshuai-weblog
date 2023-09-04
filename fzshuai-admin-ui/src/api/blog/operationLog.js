import request from '@/utils/request'

// 查询操作日志列表
export function listOperationLog(query) {
  return request({
    url: '/blog/operationLog/list',
    method: 'get',
    params: query
  })
}

// 查询操作日志详细
export function getOperationLog(operationLogId) {
  return request({
    url: '/blog/operationLog/' + operationLogId,
    method: 'get'
  })
}

// 新增操作日志
export function addOperationLog(data) {
  return request({
    url: '/blog/operationLog',
    method: 'post',
    data: data
  })
}

// 修改操作日志
export function updateOperationLog(data) {
  return request({
    url: '/blog/operationLog',
    method: 'put',
    data: data
  })
}

// 删除操作日志
export function delOperationLog(operationLogId) {
  return request({
    url: '/blog/operationLog/' + operationLogId,
    method: 'delete'
  })
}
