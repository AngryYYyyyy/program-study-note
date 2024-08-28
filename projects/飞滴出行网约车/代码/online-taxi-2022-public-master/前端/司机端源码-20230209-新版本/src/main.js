/*
 * @Author: ch cwl_ch@163.com
 * @Date: 2022-11-21 17:23:51
 * @LastEditors: ch
 * @LastEditTime: 2022-12-13 17:41:14
 * @Description: file content
 */
import {
	createSSRApp
} from "vue";
import App from "./App.vue";
import store from './store'
export function createApp() {
	const app = createSSRApp(App);
	app.use(store)
	return {
		app,
	};
}
