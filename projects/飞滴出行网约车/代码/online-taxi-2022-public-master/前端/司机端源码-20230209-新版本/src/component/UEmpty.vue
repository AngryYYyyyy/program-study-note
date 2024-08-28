<template>
    <view class="empty">
        <view class="desc" :style="`margin-top:${$props.top}`">{{desc}}</view>
    </view>
</template>
<script setup>
import { computed } from "vue";

const $props = defineProps({
    desc : {
        type: String,
        default : '暂无数据'
    },
    top : {
        type: String,
        default: '50%'
    }
});
const desc = computed(()=> $props.desc || '暂无数据')
</script>

<style lang="scss" scoped>
.empty{
    width: 100%;
    height: 100%;
    text-align: center;
}
.desc{
    color: $uni-text-color-grey;
    font-size: $uni-font-size-base;
}
</style>