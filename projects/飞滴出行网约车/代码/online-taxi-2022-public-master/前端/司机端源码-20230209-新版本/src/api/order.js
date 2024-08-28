/*
 * @Author: ch cwl_ch@163.com
 * @Date: 2022-12-19 15:33:58
 * @LastEditors: ch
 * @LastEditTime: 2022-12-27 18:20:27
 * @Description: file content
 */
import { MsbRequest } from "../plugins/requset";

const ApiGetOrderDetail = (data= {orderId}) => MsbRequest.get('/order/detail',data);

const ApiPostOrderCancel = ( data = {orderId}) => MsbRequest.post('/order/cancel',data);
/**
 * @Description: 去接乘客
 * @param {*} orderId 订单ID
 * @param {*} toPickUpPassengerTime 出发时间
 * @param {*} toPickUpPassengerLongitude 出发经度
 * @param {*} toPickUpPassengerLatitude 出发纬度
 * @param {*} toPickUpPassengerAddress 出发地址
 * @return {*}
 */
const ApiPostToPickUpPassenger = (data = {orderId,toPickUpPassengerTime,toPickUpPassengerLongitude,toPickUpPassengerLatitude,toPickUpPassengerAddress}) => MsbRequest.post('/order/to-pick-up-passenger',data);
/**
 * @Description: 到达上车点
 * @param {*} orderId 订单ID
 * @return {*}
 */
const ApiPostToDeparture = (data = {orderId}) => MsbRequest.post('/order/arrived-departure', data)
/**
 * @Description: 接到乘客
 * @param {*} orderId 
 * @param {*} pickUpPassengerLongitude
 * @param {*} pickUpPassengerLatitude
 * @return {*}
 */
const ApiPostPickUpPassenger = (data = { orderId, pickUpPassengerLongitude, pickUpPassengerLatitude}) => MsbRequest.post('/order/pick-up-passenger', data)
/**
 * @Description: 到达目的地
 * @param {*} data
 * @param {*} passengerGetoffLongitude
 * @param {*} passengerGetoffLatitude
 * @return {*}
 */
const ApiPostPassengerOff = (data = { orderId, passengerGetoffLongitude, passengerGetoffLatitude}) => MsbRequest.post('/order/passenger-getoff', data)

const ApiPostOrderPayInfo = (data = {orderId, price, passengerId}) => MsbRequest.post('/pay/push-pay-info',data, {
  "content-type" : 'application/x-www-form-urlencoded'
})
const ApiGetCurrentOrder = () => MsbRequest.get('/order/current')
export {
  ApiGetOrderDetail,
  ApiPostOrderCancel,
  ApiPostToPickUpPassenger,
  ApiPostToDeparture,
  ApiPostPickUpPassenger,
  ApiPostPassengerOff,
  ApiPostOrderPayInfo,
  ApiGetCurrentOrder

}