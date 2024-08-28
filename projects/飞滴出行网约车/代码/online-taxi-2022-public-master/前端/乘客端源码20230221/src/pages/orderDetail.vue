<template>
    <view class="wrapper">
        <BMap ref="mapRef"/>
        <BSseMessage @receiveMsg="handleReceiveMsg"/>
        <view class="operation">
            <template v-if="orderDetail.orderStatus >= ORDER_STATUS.tripFinish ">
                <view class="desc" v-if="orderDetail.orderStatus === ORDER_STATUS.tripFinish">行程已完成，请等待司机收款</view>
                <view v-if="orderDetail.orderStatus === ORDER_STATUS.awaitPay">
                    <text class="desc">您的行程已结束，请您尽快完成支付</text>
                    <button @click="handlePay" class="btn">￥{{orderDetail.price}}立即支付</button>
                </view>
            </template>
            <template v-else>
                <view class="desc" v-if="orderDetail.orderStatus === ORDER_STATUS.orderStart">等待附近司机接单中...</view>
                <view class="desc">
                    <text>{{orderDetail.vehicleNo}}</text>
                    <text v-if="orderDetail.orderStatus === ORDER_STATUS.driverReceive">已接单</text>
                    <text v-if="orderDetail.orderStatus === ORDER_STATUS.driverToPickUp">正在赶来的路上</text>
                    <text v-if="orderDetail.orderStatus === ORDER_STATUS.driverArriveStartPoint">到达上车点</text>
                    <text v-if="orderDetail.orderStatus === ORDER_STATUS.tripStart">正在为您服务</text>
                </view>
                <button @click="handleCancel"  class="btn btn_cancel">取消订单</button>
            </template>
        </view>
    </view>
</template>
<script setup>
import { onLoad } from "@dcloudio/uni-app"
import { ApiGetCurrentOrder, ApiPostOrderCancel } from "../api/order"
import { HandleApiError } from "../utils";
import { ORDER_STATUS} from '../config/dicts';
import BSseMessage from '../component/BSseMessage.vue';
import BMap from '../component/BMap.vue';
import { onMounted, ref } from "vue";

let $routerQuery = {};
let orderDetail = ref({});
let mapRef = ref(null);

onLoad((option)=>{
    $routerQuery = option;
});
onMounted(()=>{
    getOrderDetail();
})
const  getOrderDetail = async () =>{
    const {error, result} = await ApiGetCurrentOrder();
    if(!result){
        uni.navigateTo({url:'/pages/index/index'})
    }else{
        orderDetail.value = result;
        mapRef.value.driving([result.depLongitude, result.depLatitude], [result.destLongitude, result.destLatitude]);
    }
}
/**
 * @Description: 取消订单
 * @return {*}
 */
const handleCancel = async () =>{
    const {error, result} = await ApiPostOrderCancel({orderId: orderDetail.value.id});
    if(!HandleApiError(error)){
        uni.redirectTo({url:'/pages/index/index'});
    }
}
/**
 * @Description: 点击支付，跳转到支付网页；
 * @return {*}
 */
const handlePay = () =>{
    uni.navigateTo({
        url : `/pages/pay?id=${orderDetail.value.id}&price=${orderDetail.value.price}`
    })
}
/**
 * @Description: 接收订单变更消息
 * 暂时只有订单收款时会通知，使用价格判断
 * 比较完善的消息通知应该有消息分类，每个节点变化都通知
 * @param {*} msg
 * @return {*}
 */
const handleReceiveMsg = (msg) =>{
    
    if(msg.price){
        orderDetail.value.price = msg.price;
        orderDetail.value.orderStatus = ORDER_STATUS.awaitPay;
    }
}
</script>
<style lang="scss" scoped>
.wrapper {
    width: 100%;
    height: calc(100vh - 30px);
}

.operation{
    position: fixed;
    bottom: 30rpx;
    left: 30rpx;
    right: 30rpx;
    background: $uni-bg-color;
    border-radius: $uni-border-radius-lg;
    z-index: 9;
    padding: $uni-spacing-max;
    box-shadow: 0 0 10rpx rgba(0, 0, 0, 0.1);
}
.desc{
    height: 60rpx;
    display: flex;
    justify-content: space-between;
    font-size: $uni-font-size-base;
    color: $uni-text-color;

}
.btn{
    font-size: $uni-font-size-lg;
    margin-top: $uni-spacing-max;
    background: $uni-color-primary;
    color: $uni-text-color-inverse;
    &__cancel{
        background: $uni-bg-color-grey;
        color: $uni-text-color;
        border: 1px solid $uni-border-color;
    }

    &::after{
        display: none;
    }
}
</style>