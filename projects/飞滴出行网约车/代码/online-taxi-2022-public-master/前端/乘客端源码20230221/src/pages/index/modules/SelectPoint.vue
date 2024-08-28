<template>
    <view class="select-box" >
        <view class="start" @click="startPointVisible = true">
            <text v-if="!startPoint.id">您在哪上车？</text>
            <template v-else>
                <text>您将从</text>
                <view class="start-point-text">{{startPoint.name}}</view>
                <text>上车</text>
            </template>
        </view>
        <view class="end" @click="endPointVisible = true">  
            <text>您要去哪儿？</text>
        </view>
    </view>
    <PointList v-model:visible="startPointVisible" @change="handleChangeStart"/>
    <PointList v-model:visible="endPointVisible" @change="handleChangeEnd"/>
</template>
<script setup>
import {ref, watch, computed} from 'vue';
import { useStore } from 'vuex';
import PointList from './PointList.vue'
const $emits = defineEmits(['confirm']);
const $store = useStore();
    let startPointVisible = ref(false);
    let startPoint = ref({});
    let endPointVisible = ref(false);
    let endPoint = ref({});
    let city =  computed(()=> $store.state.city);
    
    watch( city, ()=>{
		startPoint.value = {};
        endPoint.value = {};
	});
    const handleChangeStart = (item) =>{
        startPoint.value = item;
        endPoint.value = {};
    }
    const handleChangeEnd = (item) =>{
        endPoint.value = item;
        if(startPoint.value.id){
            $emits('confirm', startPoint.value, endPoint.value);
        }
    }
</script>
<style scoped lang="scss">
.select-box{
    position: fixed;
    bottom: 100rpx;
    left: $uni-spacing-row-max;
    right: $uni-spacing-row-max;
    background: $uni-bg-color;
    border-radius: $uni-border-radius-lg;
    z-index: 9;
    padding: $uni-spacing-row-max;
    box-shadow: 0 0 10rpx rgba(0, 0, 0, 0.1);
}
.start{
    height: 60rpx;
    font-size: $uni-font-size-base;
    display: flex;
    align-items: center;
    padding: 0 $uni-spacing-lg;
    margin-bottom: $uni-spacing-big;
    &::before{
        display: block;
        content: '';
        width: 10rpx;
        height: 10rpx;
        border-radius: 50%;
        background: #19c235;
        margin-right: $uni-spacing-row-base;

    }
}
.start-point-text{
    color: $uni-color-primary;
    font-weight: bold;
    margin: 0 $uni-spacing-sm;
    max-width: 400rpx;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
.end{
    background: #eee;
    border-radius: $uni-border-radius-base;
    height: 70rpx;
    line-height: 70rpx;
    padding: 0 $uni-spacing-lg;
    display: flex;
    align-items: center;
    font-size: $uni-font-size-lg;
    &::before{
        display: block;
        content: '';
        width: 10rpx;
        height: 10rpx;
        border-radius: 50%;
        background: #f0ad4e;
        margin-right: $uni-spacing-row-base;
        
    }
}
</style>