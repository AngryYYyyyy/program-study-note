<template>
    <view class="wrapper">
        <view class="search-box">
            <input class="search-input" v-model="searchStr" placeholder="请输入关键词搜索"/>
            <text @click="handleCancel">取消</text>
        </view>
        <view class="city-item" v-for="item in filterList" :key="item.adcode" @click="handleCity(item)">
            <text>{{item.name}}</text>
        </view>
    </view>
</template>
<script setup>
import { onMounted, ref, computed } from "vue";
import { useStore } from "vuex";
import gdMapConf from "../config/gdMapConf";

const $store = useStore();

let cityList = ref([]);
let searchStr = ref('');
let filterList = computed(()=>{
    return cityList.value.filter(i=> i.name.includes(searchStr.value))
});
onMounted(()=>{
    getCityList()
})
const getCityList = () =>{
    uni.request({
		method: 'GET',
		url: `${gdMapConf.cityApiUrl}?subdistrict=2&key=${gdMapConf.cityKey}`,
		success(res){
            cityList.value = formatCity(res.data.districts[0].districts).sort((a,b)=>{
                return a.name.localeCompare(b.name, 'zh-CN');
            });
            
		}
	})
};
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
const handleCity = (item) => {
    $store.commit('setCity', item);
    uni.navigateBack();
}

const handleCancel = () =>{
    uni.navigateBack();
}
	
</script>
<style lang="scss" scoped>
.wrapper{
    padding: $uni-spacing-max;
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
.city-item{
    height: 70rpx;
    line-height: 70rpx;
    font-size: $uni-font-size-base;
    border-bottom: 1px solid #eee;
}
</style>
