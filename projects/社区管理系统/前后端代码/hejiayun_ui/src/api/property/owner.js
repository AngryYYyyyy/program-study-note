import request from '@/utils/request'

// 查询业主 列表
export function listOwner(query) {
  return request({
    url: '/system/owner/list',
    method: 'get',
    params: query
  })
}

// 查询业主 详细
export function getOwner(ownerId) {
  return request({
    url: '/system/owner/' + ownerId,
    method: 'get'
  })
}

// 新增业主 
export function addOwner(data) {
  return request({
    url: '/system/owner',
    method: 'post',
    data: data
  })
}

// 修改业主 
export function updateOwner(data) {
  return request({
    url: '/system/owner',
    method: 'put',
    data: data
  })
}

// 删除业主 
export function delOwner(ownerId) {
  return request({
    url: '/system/owner/' + ownerId,
    method: 'delete'
  })
}

// 导出业主 
export function exportOwner(query) {
  return request({
    url: '/system/owner/export',
    method: 'get',
    params: query
  })
}