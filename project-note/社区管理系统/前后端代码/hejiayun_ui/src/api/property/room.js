import request from '@/utils/request'

// 查询房间 列表
export function listRoom(query) {
  return request({
    url: '/system/room/list',
    method: 'get',
    params: query
  })
}

// 查询房间 详细
export function getRoom(roomId) {
  return request({
    url: '/system/room/' + roomId,
    method: 'get'
  })
}

// 新增房间 
export function addRoom(data) {
  return request({
    url: '/system/room',
    method: 'post',
    data: data
  })
}

// 修改房间 
export function updateRoom(data) {
  return request({
    url: '/system/room',
    method: 'put',
    data: data
  })
}

// 删除房间 
export function delRoom(roomId) {
  return request({
    url: '/system/room/' + roomId,
    method: 'delete'
  })
}

// 导出房间 
export function exportRoom(query) {
  return request({
    url: '/system/room/export',
    method: 'get',
    params: query
  })
}