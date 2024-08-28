<template>
    <view class="map-wrapper">
        <BMap ref="mapRef"/>
        <view class="panel">
            <view class="panel--bar">
                <text>预计价格：</text>
                <text class="price">￥{{priceResult.price}}</text>
            </view>
            <view class="panel--bar">
                <text>出发时间：</text>
                <view class="time-bar">
                    <picker mode="date" fields="day" :value="departDay" :start="start" :end="end">
                        <view class="time">{{departDay}}</view>
                    </picker>
                    <picker mode="time" :value="departTime" @change="handleTimeChange" start="00:00" end="23:59">
                        <view> {{departTime}}</view>
                    </picker>
                </view>
            </view>
            <view class="operation">
                <button class="btn btn__cancel" @click="handleCancel">取消</button>
                <button class="btn" @click="handleConfirm">确认呼叫</button>
            </view>
        </view>
    </view>
</template>
<script setup>
import BMap from '../component/BMap.vue';
import { onLoad} from '@dcloudio/uni-app';
import { computed, nextTick, onMounted, ref } from 'vue';
import { useStore } from 'vuex';
import { HandleApiError } from '../utils';
import { ApiGetPrice, ApiPostOrderAdd, ApiGetCurrentOrder } from '../api/order';
import { _FormatDate } from '@gykeji/jsutil';


    const $store = useStore(); 
	const city = computed(()=> $store.state.city);
    const userInfo = computed(()=> $store.state.userInfo);
    const start = _FormatDate(new Date(), 'yyyy-mm-dd');
    const end = _FormatDate(new Date().getTime() + 3 * 24 * 60 * 60 * 1000, 'yyyy-mm-dd');
    const mapRef = ref(null);
    const startDate = new Date()
    let $routerQuery = {};
    let priceResult = ref({});
    let departTime = ref();
    let departDay = ref();
    

    onLoad((option) => {
        $routerQuery = option;
    });
    onMounted(()=>{
        getUserProgressOrder();
        getPrice();
        departDay.value = _FormatDate(new Date(), 'yyyy-mm-dd');
        departTime.value = _FormatDate(new Date().getTime() + 5 * 60 * 1000, 'hh:ii');
    })


const getPrice = async ()=>{
    const {slng:depLongitude, slat:depLatitude, elng:destLongitude, elat:destLatitude,s:dep,e:dest} = $routerQuery;
    const {error, result} = await ApiGetPrice({
        depLongitude,
        depLatitude,
        destLongitude,
        destLatitude,
        vehicleType: 1,
        cityCode: city.value.adcode
    });
    if(!HandleApiError(error)){
        priceResult.value = result;
        nextTick(()=>{
            mapDriving([depLongitude,depLatitude],[destLongitude, destLatitude]);
        })
		
    }
}
const handleTimeChange = (e) =>{
    departTime.value = e.detail.value;
}
const handleConfirm = async () =>{
    const {slng:depLongitude, slat:depLatitude, elng:destLongitude, elat:destLatitude,s:dep,e:dest} = $routerQuery;
    const {error, result} = await ApiPostOrderAdd({
        address: city.value.adcode,
        departTime: `${departDay.value} ${departTime.value}:01`,
        orderTime: _FormatDate(new Date(),'yyyy-mm-dd hh:ii:ss'),
        departure: dep,
        depLongitude,
        depLatitude,
        destination:dest,
        destLongitude,
        destLatitude,
        encrypt: 14,//不知道是什么
        fareType: priceResult.value.fareType,
        fareVersion: priceResult.value.fareVersion,
        passengerId: userInfo.value.id,
        passengerPhone: userInfo.value.passengerPhone,
        vehicleType: priceResult.value.vehicleType
    });
    if(!HandleApiError(error)){
        uni.redirectTo({url:'/pages/orderDetail'})
    }
}
const handleCancel = ()=>{
    uni.navigateBack();
}
const mapDriving = (dep,dest)=>{
    if(!mapRef.value.driving){
        setTimeout(()=>{
            mapDriving(dep,dest)
        }, 500)
        return false
    }
    mapRef.value.driving(dep,dest);
}

const getUserProgressOrder = async () => {
    const {  result } = await ApiGetCurrentOrder();
    if (result) {
        uni.redirectTo({url:'/pages/orderDetail'})
    }
}
</script>
<style scoped lang="scss">

	.map-wrapper {
		width: 100%;
		height: calc(100vh - 30px);
	}
    .panel{
        position: fixed;
        bottom: 30rpx;
        left: 30rpx;
        right: 30rpx;
        background: $uni-bg-color;
        border-radius: $uni-border-radius-lg;
        z-index: 9;
        padding: $uni-spacing-row-max;
        box-shadow: 0 0 10rpx rgba(0, 0, 0, 0.1);
        &--bar{
            display: flex;
            justify-content: space-between;
            align-items: center;
            height: 70rpx;
            text{
                font-size: $uni-font-size-base;
                color: $uni-text-color;
            }
        }
        .price{
            font-size: 38rpx;
            color: $uni-color-red;
            font-weight: bold;
        }
        .time-bar{
            display: flex;
            align-items: center;
            &::after{
                display: block;
                content: '';
                height: 15rpx;
                width: 15rpx;
                border: 1px solid $uni-border-color;
                transform: rotate(45deg);
                border-left: 0;
                border-bottom: 0;
                margin-left: $uni-spacing-base;
            }
        }
    }
    .operation{
        display: flex;
        align-items: center;
        margin-top: $uni-spacing-lg;
        justify-content: space-between;
    }
    .btn{
		font-size: $uni-font-size-lg;
        margin: 0;
        background: $uni-color-primary;
        color: $uni-text-color-inverse;
        width: 45%;
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