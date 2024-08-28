<template>
    <!--  #ifdef  H5 -->
    <web-view v-if="userInfo.id" :src="url" ref="h5WebviewRef" style="width:0px; height:0px"/>
    <!--  #endif -->
    <!--  #ifndef  H5 -->
    <web-view  v-if="userInfo.id" :src="url" :fullscreen="false" @message="handleMessage" :webview-styles="{width:'0px',height:'0px'}"/>
    <!--  #endif -->
</template>
<script setup>
    import {computed, onMounted, ref} from 'vue';
	import { useStore } from 'vuex';
	const $store = useStore();
	const $emits = defineEmits(['receiveMsg']);
	const userInfo = computed(()=> $store.state.userInfo);
	let url = ref(`/static/sseMessage.html?userId=${userInfo.value.id}&uri=${encodeURIComponent($store.state.serverConf.sse)}`);
	onMounted(()=>{
		if(window){
			window.addEventListener("message", handleMessage, false);
		}
	})
	const handleMessage = (e)=>{

		$emits('receiveMsg', e.data.data.arg)

	}
</script>