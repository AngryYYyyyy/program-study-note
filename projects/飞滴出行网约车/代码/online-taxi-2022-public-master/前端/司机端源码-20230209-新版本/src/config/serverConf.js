/*
 * @Author: ch cwl_ch@163.com
 * @Date: 2022-12-30 14:47:25
 * @LastEditors: ch
 * @LastEditTime: 2023-01-03 15:33:23
 * @Description: 服务端配置
 */

export default {
    // #ifdef H5
        // sse服务
        sse: '/sseApi',
        // 其他接口服务
        other: '/api',
    // #endif

    // #ifdef APP-PLUS || MP
        // sse服务
        sse: 'http://192.168.40.193:60001',
        // 其他接口服务
        other: 'http://192.168.40.193:8088'
    // #endif
}