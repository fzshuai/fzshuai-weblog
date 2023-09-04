import request from '@/utils/request'


// 查询网站配置详细
export function getWebsiteConfig() {
  return request({
    url: '/blog/website/admin/config',
    method: 'get'
  })
}

// 修改网站配置
export function updateWebsiteConfig(data) {
  return request({
    url: '/blog/website/config',
    method: 'put',
    data: data
  })
}
