<template>
    <view class="map-wrapper">
		<BMap ref="mapRef" />
		<view class="city" @click="handleCity()">{{city.name}}</view>
		<view class="system" @click="handleSystem()">设置</view>
		<SelectPoint v-if="loadingFinish" @confirm="handleConfrimPoint"/>
    </view>
</template>
<script setup>
    import {computed, onMounted, provide, ref, watch} from 'vue';
	import { useStore } from 'vuex';
	import BMap from '../../component/BMap.vue';
	import SelectPoint from './modules/SelectPoint.vue';
	
	const $store = useStore();
	const mapRef = ref(null);
	let city = computed(()=> $store.state.city);
	let loadingFinish = ref(false);
	provide('mapSearch',(str,cb) => mapRef.value.search(cb, str));
	
	onMounted(()=>{
		setCenter();
		watch(city, setCenter);
		// 临时加的一个延迟
		setTimeout(()=>{
			loadingFinish.value = true
		},7000)
	})

 /**
  * @Description: 城市变更
  * 重新设置地图显示区域
  * 清除已画好的路线
  * @return {*}
  */
 	function setCenter (){
		const center = city.value.center.split(',');
		mapRef.value.setCenter(...center);
		setTimeout(()=>mapRef.value.clearDriving(), 500);
	}	
 /**
  * @Description:  确认路线
  * 绘制路线，页面状态改为价格
  * @param {*} start
  * @param {*} end
  * @return {*}
  */	
	const handleConfrimPoint = async (start, end) =>{
		const [startLng, startLat] = start.location;
		const [endLng, endLat ] = end.location;
		uni.navigateTo({
			url : `/pages/createOrder?slng=${startLng}&slat=${startLat}&elng=${endLng}&elat=${endLat}&s=${start.name}&e=${end.name}`
		})
	}
 /**
  * @Description: 重新选城市
  * @return {*}
  */	
	const handleCity = () =>{
  		uni.navigateTo({url: '/pages/city'});
	}

	const handleSystem = () =>{
		uni.navigateTo({url: '/pages/account'});
	}
</script>
<style scoped lang="scss">

	.map-wrapper {
		width: 100%;
		height: 100vh;
	}
	.city,.system{
		position: fixed;
		background: #fff;
		padding: 7rpx 15rpx 10rpx;
		z-index: 9;
		top: calc(var(--window-top) + 30rpx);
		right: 30rpx;
		border-radius: 8rpx;
		box-shadow: 0 3rpx 5rpx 5rpx #ccc;
		font-size: $uni-font-size-sm;
		color: $uni-text-color;
		display: flex;
		align-items: center;
	}
	.city{
		left: 30rpx;
		right: auto;
		&::after{
			content: '';
			display: block;
			border: 10rpx solid #666;
			border-right-color: transparent;
			border-left-color: transparent;
			border-bottom-color: transparent;
			margin-left: 10rpx;
			margin-top: 12rpx;
			border-radius: 4rpx;
		}
	}
</style>