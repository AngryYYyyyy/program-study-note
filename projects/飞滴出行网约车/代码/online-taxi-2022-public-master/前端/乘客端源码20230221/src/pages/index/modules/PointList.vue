<template>
    <view :class="['point-search', {'point-search_show':myVisible}]">
        <view class="search-box">
            <text class="search-city" @click="handleCity">{{city.name}}</text>
            <input class="search-input" v-model="searchStr" @input="handleSearch" placeholder="请输入关键词搜索"/>
            <text @click="myVisible = false">取消</text>
        </view>
        <view class="point-list">
            <view class="point-item" v-for="item in searchList" :key="item.id" @click="handleChangePoint(item)">
                <view class="point-item--name">{{item.name}}</view>
                <view class="point-item--addres">{{item.address}}</view>
            </view>
        </view>
    </view>
</template>
<script setup>
import {computed, inject, ref} from 'vue';
import { useStore } from 'vuex';
const $props = defineProps({
    visible: {
        type : Boolean,
        default : false
    }
});
const $emits = defineEmits(['change','update:visible']);
const $store = useStore();
const piMapSearch = inject('mapSearch');
const city = computed(()=> $store.state.city)
let myVisible = computed({
    get:()=> $props.visible,
    set(val){
        $emits('update:visible',val)
    }
});
let searchStr = ref('');
let searchList = ref([]);
const handleSearch = () =>{
    piMapSearch(searchStr.value,(result)=>{
        searchList.value = result.pois;
    })
}
const handleChangePoint = (item) =>{
    $emits('change', item);
    myVisible.value = false;
}

const handleCity = () =>{
    uni.navigateTo({url: '/pages/city'});
}
</script>
<style scoped lang="scss">
.point-search{
    position: fixed;
    height: 0;
    left: 0;
    right: 0;
    bottom: 0;
    z-index: 9;
    background: #fff;
    overflow: auto;
    transition: all 1s;
}
.point-search_show{
    height: calc(100vh - var(--window-top));
    padding: $uni-spacing-max;
    box-sizing: border-box;
}
.search-box{
    display: flex;
    align-items: center;
    font-size: $uni-font-size-base;
    margin-bottom: $uni-spacing-max;
}
.search-input{
    background: $uni-bg-color-grey;
    font-size: $uni-font-size-sm;
    height: 50rpx;
    padding: 0 10rpx;
    border: 1px solid $uni-border-color;
    border-radius: $uni-border-radius-base;
    margin-right: $uni-spacing-big;
    flex: 1;
}
.search-city{
    font-size: $uni-font-size-base;
    margin-right: $uni-spacing-big;
    max-width: 150rpx;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
.point-item{
    font-size: $uni-font-size-base;
    border-bottom: 1px solid #eee;
    padding: $uni-spacing-lg 0 $uni-spacing-max;
    &--name{
        margin: $uni-spacing-big 0;
    }
    &--addres{
        font-size: $uni-font-size-base;
        color: $uni-text-color-grey;
        line-height: 32rpx;
    }
}
</style>