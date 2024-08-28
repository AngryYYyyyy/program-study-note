/*
 * @Author: ch cwl_ch@163.com
 * @Date: 2022-12-27 14:52:17
 * @LastEditors: ch
 * @LastEditTime: 2022-12-27 15:12:06
 * @Description: file content
 */
const ORDER_STATUS = {
    // 乘客发起订单
    orderStart : 1,
    // 司机接单
    driverReceive: 2,
    // 司机去接乘客
    driverToPickUp: 3,
    // 司机到底上车点
    driverArriveStartPoint: 4,
    // 行程开始，乘客上车
    tripStart: 5,
    // 行程结束，到达目的地
    tripFinish: 6,
    // 发起收款，待支付
    awaitPay: 7,
    // 付款完成，订单完成
    orderFinish: 8,
    // 订单取消
    orderCancel: 9,
    
}
export {
    ORDER_STATUS
}