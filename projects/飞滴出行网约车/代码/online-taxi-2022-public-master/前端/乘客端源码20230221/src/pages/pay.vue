<template>
    <web-view :src="url" />
</template>
<script setup>
import { onLoad } from "@dcloudio/uni-app"
import { ref } from "@vue/reactivity";
import { useStore } from "vuex";
const $store = useStore()

let url = ref()
onLoad((option)=>{
    url.value = `${$store.state.serverConf.pay}/alipay/pay?subject=${decodeURIComponent('车费')}&outTradeNo=${option.id}&totalAmount=${option.price}`;
    // #ifdef H5
    window.location.href = url.value;
    // endif
})
</script>