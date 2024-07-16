import request from '@/utils/request'

// 查询楼栋 列表
export function listBuilding(query) {
  return request({
    url: '/system/building/list',
    method: 'get',
    params: query
  })
}

// 查询楼栋 详细
export function getBuilding(buildingId) {
  return request({
    url: '/system/building/' + buildingId,
    method: 'get'
  })
}

// 新增楼栋 
export function addBuilding(data) {
  return request({
    url: '/system/building',
    method: 'post',
    data: data
  })
}

// 修改楼栋 
export function updateBuilding(data) {
  return request({
    url: '/system/building',
    method: 'put',
    data: data
  })
}

// 删除楼栋 
export function delBuilding(buildingId) {
  return request({
    url: '/system/building/' + buildingId,
    method: 'delete'
  })
}

// 导出楼栋 
export function exportBuilding(query) {
  return request({
    url: '/system/building/export',
    method: 'get',
    params: query
  })
}
// 楼栋下拉
export function queryPullDown(query) {
  return request({
    url: '/system/building/queryPullDown',
    method: 'get',
    params: query
  })
}

// 楼栋下拉
export function queryPullDownRoom(query) {
  return request({
    url: '/system/building/queryPullDownRoom',
    method: 'get',
    params: query
  })
}
