import request from '@/utils/request'

// 查询房屋绑定 列表
export function listRoom(query) {
  return request({
    url: '/system/ownerRoom/list',
    method: 'get',
    params: query
  })
}

// 查询房屋绑定 详细
export function getRoom(ownerRoomId) {
  return request({
    url: '/system/ownerRoom/' + ownerRoomId,
    method: 'get'
  })
}

// 新增房屋绑定 
export function addRoom(data) {
  return request({
    url: '/system/ownerRoom',
    method: 'post',
    data: data
  })
}

// 修改房屋绑定 
export function updateRoom(data) {
  return request({
    url: '/system/ownerRoom',
    method: 'put',
    data: data
  })
}

// 删除房屋绑定 
export function delRoom(ownerRoomId) {
  return request({
    url: '/system/ownerRoom/' + ownerRoomId,
    method: 'delete'
  })
}

// 导出房屋绑定 
export function exportRoom(query) {
  return request({
    url: '/system/ownerRoom/export',
    method: 'get',
    params: query
  })
}
