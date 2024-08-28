/*
 * @Author: ch cwl_ch@163.com
 * @Date: 2022-12-28 14:42:03
 * @LastEditors: ch
 * @LastEditTime: 2022-12-28 14:54:32
 * @Description: file content
 */

import { _ToAsyncAwait } from '@gykeji/jsutil';
import MAP_CON from '../config/gdMapConf';
const formatCity = (data)=>{
    let arr = [];
    data.forEach(i => {
        if(i.citycode.length){
            arr.push(i);
        }
        if(i.districts.length){
            arr = arr.concat(formatCity(i.districts))
        }
    });
    return arr;
}
/**
 * @Description: 获取城市列表，直接使用高德API
 * @return {*}
 */
const ApiGetCityList = () => _ToAsyncAwait(
    new Promise((res, rej) => {
        uni.request({
            method: 'GET',
            url: `${MAP_CON.cityApiUrl}?subdistrict=2&key=${MAP_CON.cityKey}`,
            success(result){
                res(
                    formatCity(result.data.districts[0].districts).sort((a,b)=>{
                        return a.name.localeCompare(b.name, 'zh-CN');
                    })
                );
            },
            error(e){
                rej(e)
            }
        })
    })
)
export {
    ApiGetCityList
}