import request from '@/utils/request'

// 查询报修信息列表
export function listRepair(query) {
  return request({
    url: '/system/repair/list',
    method: 'get',
    params: query
  })
}

// 查询报修信息详细
export function getRepair(repairId) {
  return request({
    url: '/system/repair/' + repairId,
    method: 'get'
  })
}

// 新增报修信息
export function addRepair(data) {
  return request({
    url: '/system/repair',
    method: 'post',
    data: data
  })
}

// 修改报修信息
export function updateRepair(data) {
  return request({
    url: '/system/repair',
    method: 'put',
    data: data
  })
}

// 删除报修信息
export function delRepair(repairId) {
  return request({
    url: '/system/repair/' + repairId,
    method: 'delete'
  })
}

// 导出报修信息
export function exportRepair(query) {
  return request({
    url: '/system/repair/export',
    method: 'get',
    params: query
  })
}