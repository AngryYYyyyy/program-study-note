/*
 * @Author: ch cwl_ch@163.com
 * @Date: 2022-12-19 11:30:32
 * @LastEditors: ch
 * @LastEditTime: 2022-12-30 18:19:04
 * @Description: file content
 */
import { MsbRequest } from '../plugins/requset';

/**
 * @Description: 获取验证码
 * @param {*} passengerPhone
 * @return {*}
 */
const ApiGetVerifyCode = ({passengerPhone}) => MsbRequest.post('/verification-code',{
    passengerPhone
},{notVerifyToken:true})
/** 
 * @Description: 验证验证码
 * @param {*} passengerPhone
 * @param {*} verificationCode
 * @return {*}
 */
const ApiPostVerifyCodeCheck = ({passengerPhone,verificationCode}) => MsbRequest.post('/verification-code-check',{
    passengerPhone,
    verificationCode,
},{notVerifyToken:true})
/**
 * @Description: 根据token获取乘客信息
 * @return {*}
 */
const ApiGetUserInfo = () => MsbRequest.get('/users/');

export {
    ApiGetVerifyCode,
    ApiPostVerifyCodeCheck,
    ApiGetUserInfo
}