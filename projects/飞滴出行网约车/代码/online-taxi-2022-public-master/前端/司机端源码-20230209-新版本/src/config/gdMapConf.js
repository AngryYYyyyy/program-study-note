/*
 * @Author: ch cwl_ch@163.com
 * @Date: 2022-12-28 14:39:29
 * @LastEditors: ch
 * @LastEditTime: 2022-12-30 16:35:53
 * @Description: 高德地图配置，需要自行去高德开放平台申请
 */
export default {
    // 高德地图JS Api key
    key:'336c720ecc79d891b7198912f47e7a32',
    // 高德地图JS Api key对应的秘钥，正式环境最好不要放前端
    securityJsCode : 'c20760ec4150c700d74ca33235bd9a76',
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