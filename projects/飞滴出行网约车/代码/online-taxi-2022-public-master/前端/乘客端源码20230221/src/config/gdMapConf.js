/*
 * @Author: ch cwl_ch@163.com
 * @Date: 2022-12-28 14:39:29
 * @LastEditors: ch
 * @LastEditTime: 2023-01-03 21:00:14
 * @Description: 高德地图配置，需要自行去高德开放平台申请
 */
export default {
    // 高德地图JS Api key
    key:'83b7fb6fb16083a0950a992c0c3f9f71',
    // 高德地图JS Api key对应的秘钥，正式环境最好不要放前端
    securityJsCode : '4d4bf6cc97cd7a9bbc513ae209e90a56',
    // 城市获取key
    cityKey : 'fe5524e832a0fc6e2bcaf1bb781ac830',
    // 高德城市请求地址
    cityApiUrl : 'https://restapi.amap.com/v3/config/district',
    // 默认选中城市
    city: {
        adcode: "110000",
        center: "116.407387,39.904179",
        citycode: "010",
        name: "北京市"
    }
}