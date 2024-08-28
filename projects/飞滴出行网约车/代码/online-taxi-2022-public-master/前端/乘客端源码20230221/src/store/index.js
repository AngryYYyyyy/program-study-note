/*
 * @Author: ch cwl_ch@163.com
 * @Date: 2022-12-13 17:29:11
 * @LastEditors: ch
 * @LastEditTime: 2022-12-30 16:24:03
 * @Description: file content
 */

import { createStore } from 'vuex';
import STORAGE_KEY from '../config/storageKey';
import SERVER_CONF from '../config/serverConf'
import gdMapConf from '../config/gdMapConf';
const serverConf = uni.getStorageSync(STORAGE_KEY.serverConf);
export default createStore({
    state: {
        city : gdMapConf.city,
        token : uni.getStorageSync(STORAGE_KEY.token) || '',
        userInfo : JSON.parse(uni.getStorageSync(STORAGE_KEY.userInfo) || '{}'),
        serverConf : serverConf ? JSON.parse(serverConf) : SERVER_CONF
    },
    mutations: {
        setCity(state, data) {
            state.city = data;
        },
		setToken (state, token = ''){
			state.token = token;
			uni.setStorageSync(STORAGE_KEY.token, token);
		},
		setUserInfo (state, userInfo = {}){
			state.userInfo = userInfo;
			uni.setStorageSync(STORAGE_KEY.userInfo, JSON.stringify(userInfo));
		},
        setServerConf (state, config){
            state.serverConf = config;
			uni.setStorageSync(STORAGE_KEY.serverConf, JSON.stringify(config));
        }
    }
});