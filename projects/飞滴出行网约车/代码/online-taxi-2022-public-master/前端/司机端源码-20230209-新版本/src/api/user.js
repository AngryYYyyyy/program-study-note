/*
 * @Author: ch cwl_ch@163.com
 * @Date: 2022-12-19 11:30:32
 * @LastEditors: ch
 * @LastEditTime: 2023-01-03 16:53:12
 * @Description: file content
 */
import { MsbRequest } from '../plugins/requset';

/**
 * @Description: 获取验证码
 * @param {*} passengerPhone
 * @return {*}
 */
const ApiGetVerifyCode = ({driverPhone}) => MsbRequest.post('/verification-code',{
    driverPhone
},{notVerifyToken:true})
/** 
 * @Description: 验证验证码
 * @param {*} passengerPhone
 * @param {*} verificationCode
 * @return {*}
 */
const ApiPostVerifyCodeCheck = ({driverPhone,verificationCode}) => MsbRequest.post('/verification-code-check',{
    driverPhone,
    verificationCode,
},{notVerifyToken:true})
/**
 * @Description: 根据token获取司机车辆信息
 * @return {*}
 */
const ApiGetUserCarInfo = () => MsbRequest.get('/driver-car-binding-relationship');

const ApiPostUpdatePoint = (data = {carId, points}) => MsbRequest.post('/point/upload', data);

const ApiGetWorkStatus = (params = {driverId}) => MsbRequest.get('/work-status', params);

const ApiPostUpdateWorkStatus = (data = {driverId, workStatus}) => MsbRequest.post('/driver-user-work-status', data)
export {
    ApiGetVerifyCode,
    ApiPostVerifyCodeCheck,
    ApiGetUserCarInfo,
    ApiPostUpdatePoint,
    ApiGetWorkStatus,
    ApiPostUpdateWorkStatus
}