import request from '@/utils/request'

// 查询投诉建议 列表
export function listSuggest(query) {
  return request({
    url: '/system/suggest/list',
    method: 'get',
    params: query
  })
}

// 查询投诉建议 详细
export function getSuggest(complaintSuggestId) {
  return request({
    url: '/system/suggest/' + complaintSuggestId,
    method: 'get'
  })
}

// 新增投诉建议 
export function addSuggest(data) {
  return request({
    url: '/system/suggest',
    method: 'post',
    data: data
  })
}

// 修改投诉建议 
export function updateSuggest(data) {
  return request({
    url: '/system/suggest',
    method: 'put',
    data: data
  })
}

// 删除投诉建议 
export function delSuggest(complaintSuggestId) {
  return request({
    url: '/system/suggest/' + complaintSuggestId,
    method: 'delete'
  })
}

// 导出投诉建议 
export function exportSuggest(query) {
  return request({
    url: '/system/suggest/export',
    method: 'get',
    params: query
  })
}