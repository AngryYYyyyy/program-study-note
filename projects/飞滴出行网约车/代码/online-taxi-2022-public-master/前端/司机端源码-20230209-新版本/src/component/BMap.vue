<template>
    <view  :eventBus="eventBus" :change:eventBus="renderScript.receiveEvent" id="map-container">
    </view>
</template>
 
<script>
  export default {
	data(){
		return {
			eventBus : {},
            searchFn: null
		}
	},
	computed:{
		city (){return this.$store.state.city},
	},
	methods:{
		setCenter (...args) {
			this.eventBus = { name : 'setCenter', args }
		},
		driving (...args) {
			this.eventBus = { name : 'driving', args }
		},
		clearDriving (...args) {
			this.eventBus = { name : 'clearDriving', args }
		},
		search (cb, ...args) {
            this.searchFn = cb;
			this.eventBus = { name : 'search', args, city:this.city }
		},
		searchResult (res) {
            this.searchFn(res);
		}
	}
  };
</script>
 
<script module="renderScript" lang="renderjs">
	import AMapLoader from '@amap/amap-jsapi-loader';
	import {MarkerIcon} from '../plugins/Base64Markers.js';
	import gdMapConf from '../config/gdMapConf';

    window._AMapSecurityConfig = {
      securityJsCode: gdMapConf.securityJsCode,
    }
	
	
	let AMap = null
	let map = null
	let driving = null;

    export default {
      data() {
        return {
        }
      },
      mounted(){
        AMapLoader.load({
            "key": gdMapConf.key,  // 申请好的Web端开发者Key，首次调用 load 时必填
            "version": "2.0",   // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
			// 需要使用的的插件列表，如比例尺'AMap.Scale'等
            "plugins": ['AMap.Driving','AMap.PlaceSearch','AMap.AutoComplete', 'AMap.Geolocation'],           
            "AMapUI": {             // 是否加载 AMapUI，缺省不加载
                "version": '1.1',   // AMapUI 版本
                "plugins":['overlay/SimpleMarker'],       // 需要加载的 AMapUI ui插件
            },
            "Loca":{                // 是否加载 Loca， 缺省不加载
                "version": '2.0'  // Loca 版本
            },
			}).then((Amap)=>{
					AMap = Amap;
					map = new AMap.Map('map-container',{
					resizeEnable: true,
						zoom: 13 //地图显示的缩放级别
					});

			}).catch((e)=>{
					console.error(e);  //加载错误提示
			}); 
      },
      methods: {
        // 接收逻辑层发送的数据
        receiveEvent(newParams, oldValue, ownerVm, vm) {
            let {name, args} = newParams || {};
            switch (name) {
                case 'setCenter':
                    this.setMapCenter(...args)
                    break;
                case 'search':
                    this.mapSearch(newParams.city, ...args);
                    break;
                case 'driving':
                    this.mapDriving(...args);
                    break;
                case 'clearDriving':
                    this.mapClearDriving(...args);
                    break;
            }
        },

			setMapCenter(lng,lat){
				if(!map){
					setTimeout(()=> {
						this.setMapCenter(lng,lat);
					}, 500);
					return false
				}
				map.setCenter([lng,lat])
			},
			mapSearch(city, str, cb){
            // this.$ownerInstance.callMethod('searchResult', 'xxxxxxsssxx')
				AMap.plugin(["AMap.PlaceSearch"], ()=> {
				//构造地点查询类
					var placeSearch = new AMap.PlaceSearch({
						pageSize: 5, // 单页显示结果条数
						pageIndex: 1, // 页码
						city: city.cityCode || city.citycode, // 兴趣点城市
						citylimit: true,  //是否强制限制在设置的城市内搜索
						// map: map, // 展现结果的地图实例
						// autoFitView: true // 是否自动调整地图视野使绘制的 Marker点都处于视口的可见范围
					});

					//关键字查询
					placeSearch.search(str, (status, result)=>{
						if(result.info === 'OK'){
							// cb && cb(result.poiList);
							this.$ownerInstance.callMethod('searchResult', result.poiList)
						}
					});
				});
				
			},
			mapClearDriving(){
				if(driving){
					driving.clear()
				}
			},
			mapDriving(startLngLat, endLngLat){
				if(!AMap){
					setTimeout(()=> {
						this.mapDriving(startLngLat, endLngLat);
					}, 500);
					return false;
				}
				if(driving){
					driving.clear();
				}else{
					driving = new AMap.Driving({map});
				}
				 
				driving.search(new AMap.LngLat(...startLngLat), new AMap.LngLat(...endLngLat));
			},
      }
    };
</script>
 
<style scoped="false">
#map-container {
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
  border-radius: 0.2em;
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
 