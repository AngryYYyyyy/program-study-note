<template>
	<view id="a-map-container" 
		:markers="markers" :change:markers="AMapInstance.reloadMarker"
		:lines="lines" :change:lines="AMapInstance.reloadLines">
	</view>
</template>

<script>
	import {ref} from 'vue'
	export default {
		props: ['markers', 'lines'],
		setup(props) {
			
			const showDetail = function(e) {
				
				uni.showModal({
					title: `${e.title||''}详情`,
					content: '位置：' + e.position + '\nJSON：' + JSON.stringify(e).substr(0,20) + '...'
				})
			}
			return {
				showDetail, ...props
			}
		
		}
	}
	
</script>

<script module="AMapInstance" lang="renderjs">
	import AMapLoader from '@amap/amap-jsapi-loader';
	import {MarkerIcon} from '../assets/Base64Markers.js'

    window._AMapSecurityConfig = {
      securityJsCode: '5cac8799cfa688ca3a695a1a23c90480',
    }
	
	
	let AMap = null
	let map = null
	let driving = null;
	
	const SingleTaskType = {
		ReloadMarker: 1,
		ReloadLine: 2,
	}
	
	/**
	 * AMap是异步的，例如加载点位时可能还没加载完，可以先存起来
	 * @type {Map<String, Function()>}
	 */
	let singleAsyncTasks = new Map()
	
	let addedMarkers = []
	let addedLines = []
	
	export default {
		beforeMount() {
		},
		mounted() {
			AMapLoader.load({
            "key": "5bd2fa582964f00d06b3f5e8b7eb0abe",  // 申请好的Web端开发者Key，首次调用 load 时必填
            "version": "2.0",   // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
            "plugins": ['AMap.Driving','AMap.PlaceSearch','AMap.AutoComplete'],           // 需要使用的的插件列表，如比例尺'AMap.Scale'等
            "AMapUI": {             // 是否加载 AMapUI，缺省不加载
                "version": '1.1',   // AMapUI 版本
                "plugins":['overlay/SimpleMarker'],       // 需要加载的 AMapUI ui插件
            },
            "Loca":{                // 是否加载 Loca， 缺省不加载
                "version": '2.0'  // Loca 版本
            },
			}).then((Amap)=>{
					AMap = Amap;
					map = new AMap.Map('a-map-container',{
					//   center: [118.045616, 24.366646],
					resizeEnable: true,
						zoom: 13 //地图显示的缩放级别
					});

			}).catch((e)=>{
					console.error(e);  //加载错误提示
			}); 
		},
		expose:['search','driving','clearDriving','setCenter'],
		methods: {
			setCenter(lng,lat){
				map.setCenter([lng,lat])
			},
			search(str, cb){
				AMap.plugin(["AMap.PlaceSearch"], ()=> {
		        //构造地点查询类
					var placeSearch = new AMap.PlaceSearch({
						pageSize: 5, // 单页显示结果条数
						pageIndex: 1, // 页码
						city: this.$store.state.city.citycode, // 兴趣点城市
						citylimit: true,  //是否强制限制在设置的城市内搜索
						// map: map, // 展现结果的地图实例
						// autoFitView: true // 是否自动调整地图视野使绘制的 Marker点都处于视口的可见范围
					});
					//关键字查询
					placeSearch.search(str, (status, result)=>{
						if(result.info === 'OK'){
							cb && cb(result.poiList)
						}
					});
				});
			},
			clearDriving(){
				if(driving){
					driving.clear()
				}
			},
			driving(startLngLat, endLngLat){
				if(driving){
					driving.clear();
				}else{
					driving = new AMap.Driving({map});
				}
				 
				driving.search(new AMap.LngLat(...startLngLat), new AMap.LngLat(...endLngLat), 
					(status, result) => {
						if (status === 'complete') {
						console.log('绘制驾车路线完成')
						} else {
						console.log('获取驾车数据失败：' + result)
						}
					});
			},
			 //获取用户所在城市信息
			showCityInfo(AMap) {
				//实例化城市查询类
				var citysearch = new AMap.CitySearch();
				//自动获取用户IP，返回当前城市
				citysearch.getLocalCity(function(status, result) {
					if (status === 'complete' && result.info === 'OK') {
						if (result && result.city && result.bounds) {
							var cityinfo = result.city;
							var citybounds = result.bounds;
							document.getElementById('info').innerHTML = '您当前所在城市：'+cityinfo;
							//地图显示当前城市
							map.setBounds(citybounds);
						}
					} else {
						// document.getElementById('info').innerHTML = result.info;
					}
				});
			}
		}
	}
</script>

<style scoped="false">
	#a-map-container {
		width: 100%;
		height: 100%;
	}
	
	>>> .amap-logo,
	>>> .amap-copyright {
		display: none !important;
	}
	
	>>> .amap-info-contentContainer {
		background: #fff;
		border: 2px solid #ddd;
		border-radius: .2em;
		padding: 1em;
	}
	>>> .amap-info-contentContainer .info {
		display: flex;
		flex-direction: column;
	}
	>>> .info__btn {
		margin-top: 4px;
	}
        #panel {
            position: absolute;
            background-color: white;
            max-height: 90%;
            overflow-y: auto;
            top: 10px;
            right: 10px;
            width: 280px;
        }
</style>