<template>
    <view class="wrapper">
        <view class="title">在此设置你的后端服务地址：</view>
        <view class="item">
            <text class="label">SSE服务</text>
            <input class="input" v-model="serverConf.sse" />    
        </view>
        <view class="item">
            <text class="label">其他服务</text>
            <input class="input" v-model="serverConf.other" />    
        </view>
        <button class="btn" @click="handleSave">保存</button>
        <view class="desc">保存成功后如不生效，请重启APP</view>
        <button class="btn btn__logout" @click="handleLogout">退出</button>
    </view>
</template>
<script setup>
import { computed} from "vue";
import { useStore } from "vuex";
import { ShowToast } from "../utils";

const $store = useStore();
let serverConf = computed({
    get: ()=> $store.state.serverConf,
    set(val){
    }
});
const handleSave = () =>{
    $store.commit('setServerConf', serverConf.value);
    ShowToast('保存成功');
    uni.redirectTo({url:'/pages/index'});
}
const handleLogout = () =>{
    $store.commit('setToken', '');
    uni.redirectTo({url:'/pages/login'});
}
</script>
<style lang="scss" scoped>
.item,.title{
    margin: $uni-spacing-max 0;
}
.wrapper{
    margin: $uni-spacing-max;
}
.input{
    border: 1px solid $uni-border-color;
    height: 36rpx;
    margin-top: $uni-spacing-base;
}
.label{
    font-size: $uni-font-size-base;
    color: $uni-text-color;
}

.btn{
    font-size: $uni-font-size-lg;
    margin-top: $uni-spacing-max;
    background: $uni-color-primary;
    color: $uni-text-color-inverse;
    &::after{
        display: none;
    }
    &__logout{
        background: $uni-color-error;
    }
}
.desc{
    font-size: $uni-font-size-base;
    color: $uni-color-red;
    margin-top: $uni-spacing-max;
}
</style>