<script setup>
import { onLaunch } from "@dcloudio/uni-app";
import { useStore } from "vuex";
import { ApiGetUserInfo } from "./api/user";
import { ApiGetCityList } from "./api/city";
const $store = useStore();

onLaunch(() => {
	console.log('App Launch')
  	getLocation();
  	getUserInfo();
});

/**
 * @Description: 获取用户信息
 * @return {*}
 */
const getUserInfo = async () => {
  const { error, result } = await ApiGetUserInfo();
  if (result) {
    $store.commit("setUserInfo", result);
  }
};
/**
 * @Description: 默认定位到当前城市
 * @return {*}
 */
const getLocation = async () => {
  const {error, result}= await ApiGetCityList();
	uni.getLocation({
		type: 'gcj02',
		geocode: true,
		success (res) {
		  const {address,longitude,latitude} = res;
		  $store.commit('setCity',{
			adcode : result.find(i => i.citycode === address.cityCode).adcode,
			cityCode: address.cityCode,
			name: address.city,
			center: `${longitude},${latitude}`,
		  })
		}
	});
}
</script>

<style>
/*每个页面公共css */
</style>
