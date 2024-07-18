import request from '@/utils/request'

// 查询单元 列表
export function listUnit(query) {
  return request({
    url: '/system/unit/list',
    method: 'get',
    params: query
  })
}

// 查询单元 详细
export function getUnit(unitId) {
  return request({
    url: '/system/unit/' + unitId,
    method: 'get'
  })
}

// 新增单元 
export function addUnit(data) {
  return request({
    url: '/system/unit',
    method: 'post',
    data: data
  })
}

// 修改单元 
export function updateUnit(data) {
  return request({
    url: '/system/unit',
    method: 'put',
    data: data
  })
}

// 删除单元 
export function delUnit(unitId) {
  return request({
    url: '/system/unit/' + unitId,
    method: 'delete'
  })
}

// 导出单元 
export function exportUnit(query) {
  return request({
    url: '/system/unit/export',
    method: 'get',
    params: query
  })
}