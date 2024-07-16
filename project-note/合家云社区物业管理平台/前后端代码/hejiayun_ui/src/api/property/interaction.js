import request from '@/utils/request'

// 查询社区互动列表
export function listInteraction(query) {
  return request({
    url: '/system/interaction/list',
    method: 'get',
    params: query
  })
}

// 查询社区互动详细
export function getInteraction(interactionId) {
  return request({
    url: '/system/interaction/' + interactionId,
    method: 'get'
  })
}

// 新增社区互动
export function addInteraction(data) {
  return request({
    url: '/system/interaction',
    method: 'post',
    data: data
  })
}

// 修改社区互动
export function updateInteraction(data) {
  return request({
    url: '/system/interaction',
    method: 'put',
    data: data
  })
}

// 删除社区互动
export function delInteraction(interactionId) {
  return request({
    url: '/system/interaction/' + interactionId,
    method: 'delete'
  })
}

// 导出社区互动
export function exportInteraction(query) {
  return request({
    url: '/system/interaction/export',
    method: 'get',
    params: query
  })
}

//评论 删除社区互动
export function delComment(interactionId) {
  return request({
    url: '/system/comment/' + interactionId,
    method: 'delete'
  })
}
