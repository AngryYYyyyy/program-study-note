/*
 * @Author: ch
 * @Date: 2022-03-17 17:42:32
 * @LastEditors: ch
 * @LastEditTime: 2023-01-03 15:35:27
 * @Description: 项目接口请求统一处理器，返回一个需要token和不需要token的请求封装方法
 */

import MsbUniRequest from './msbUniRequest';
import $store from '@/store';
import { _ToAsyncAwait } from '@gykeji/jsutil';

/**
 * 接口返回成功结果统一处理
 * @param {*} response
 * @param {*} option
 */
const successIntercept = (response, option) =>{
	clearRepeat(option);
	if(response.statusCode === 200){
		const result = response.data;
		if(result.code === 1){
			return result.data;
		}
		if(result.code === 0){
			uni.navigateTo({url : '/login'});
			$store.commit('setToken', '');
			return result;
		}
		return Promise.reject(result);
	}
	return response;
}
/**
 * 接口返回错误结果统一处理
 * @param {*} error 
 * @param {*} option 
 */
const errorIntercept = (error, option) =>{
	clearRepeat(option);
	if(error.statusCode === 404){
		error.errMsg = '404 请求地址未找到';
	}
	return {message:error.errMsg,code:error.statusCode}
}

//正在执行的请求标识 
let repeatFlag = [];
/**
 * 验证是否重复请求，没有则添加一条到标记
 * @param {*} option 
 */
const repeatVerify = (option)=>{
	let flag = {
		url : option.url,
		method : option.method,
		data : option.data
	}
	if(repeatFlag.includes(JSON.stringify(flag))){
		return Promise.reject({message:'请勿频繁操作'});
	}
	repeatFlag.push(JSON.stringify(flag));
	return false;
};
/**
 * 清除请求标记
 * @param {*} option 
 */
const clearRepeat = (option) =>{
	repeatFlag = repeatFlag.filter( i => {
		return i !== JSON.stringify({
			url : option.url,
			method : option.method,
			data : option.data
		})
	});
}



// 接口封装
const Request = new MsbUniRequest();
// Request.baseUrl = 'http://192.168.1.199:8081';
// Request.baseUrl = '/api';

Request.baseUrl = $store.state.serverConf.other;

Request.use('request', (option) => {
	const token = $store.state.token;
	if(!token && !option.header.notVerifyToken){
		// 登录状态处理，没有token直接跳转至登录
		uni.redirectTo({
			url: '/pages/login'
		});
		return Promise.reject({message:'要先登录才能操作哦~'});
	}
	if(!option.header.notVerifyToken){
		option.header = {...option.header, Authorization:token};
	}
	
	
	if(option.header.repeat){
		// 如果当前请求不允许重复调用，则检查重复请求，当前接口有正在请求则不发起请求
		const isRepeatVerify = repeatVerify(option);
		if(isRepeatVerify){
			return isRepeatVerify;
		}
	}
	delete option.header.repeat
	delete option.header.notVerifyToken
	return option;
})
Request.use('success', successIntercept);
Request.use('error', errorIntercept);

const MsbRequest = {
	get : (...args) => _ToAsyncAwait(Request.get(...args)),
	post: (...args) => _ToAsyncAwait(Request.post(...args)),
	put: (...args) => _ToAsyncAwait(Request.put(...args)),
	delete: (...args) => _ToAsyncAwait(Request.delete(...args)),
}
export {
	MsbRequest
}

