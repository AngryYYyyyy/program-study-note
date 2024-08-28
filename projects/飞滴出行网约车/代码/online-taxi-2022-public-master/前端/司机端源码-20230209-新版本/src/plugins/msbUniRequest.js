/*
 * @Author: ch
 * @Date: 2022-03-17 16:36:59
 * @LastEditors: ch
 * @LastEditTime: 2022-12-28 10:17:52
 * @Description: 针对uniapp request请求做了一次封装，使用思维参考axios
 * 
 * 
 * 方法
 * method(option) 自定义请求，同uni.request
 * get(url, params, header) url 请求地址  params 请求参数 
 * 		header 请求头会针对当前请求头设置特定的请求头。传了此参数request拦截器会失效
 * post(url, data, header) 同上
 * put(url, data, header) 同上
 * delete(url, data, header) 同上
 * use(hookName, callback) 注入hook拦截器   hookName 拦截器名（request/response/error） callback拦截器具体见拦截器说明
 * 
 * 属性
 * baseUrl 请求地址前缀
 * 
 * 拦截器
 * request 请求前拦截，在这可统一设置请求头，请求体等参数。uni.request的第一个参数option都可以重置
 * success 请求成功结果拦截 
 * error 请求错误拦截
 * 
 * 示例
 * const myReq = new MsbUniRequest();
 * myReq.baseUrl = 'xxxx'
 * myReq.use('request', (option)=>{
 *    // option 返回请求配置
 *    .....这里可以对option做一系列操作
 *   return option //最后返回一个正确的请求配置
 * })
 * myReq.use('success', (res)=>{
 * 		//res 返回请求结果
 *      let newRes = ..... //这里可以对请求结果做统一处理
 * 		return newRes 
 * })
 * 
 * myReq.use('error', (error)=>{
 * 		//error 返回错误结果
 *      let newError = ..... //这里可以对请求错误做统一处理
 * 		return newError 
 * })
 */
class MsbUniRequest {
	constructor (option){
		this.baseUrl = '';
		this.header = {
			repeat : true
		}
		this.hook = {
			request : null,
			success : null,
			error : null
		}
		
	}
	method(option){
		option.header = {...this.header,...option.header};
		option.url = this.baseUrl ?  this.baseUrl + option.url : option.url;
		if(this.hook.request){
			option = this.hook.request(option);
		};

		if(!option){
			throw new Error('没有请求配置，或是request拦截未做return');
		}
		if(option.constructor === Promise){
			return option
		}
		return new Promise((resolve, reject)=>{
			uni.request({
				...option,
				success: res => {
					const response = res || res[1];
					// 200 - 399状态为正常
					if(response.statusCode >= 200 && response.statusCode < 400){
						if(!this.hook.success){
							resolve(response);
						}else{
							let newRes = this.hook.success(response, option);
							// 业务结果处理可能为一个Promise对象，根据结果调用错误或正确状态
							if(newRes && newRes.constructor === Promise){
								newRes.then(res => {
									resolve(res);
								}, error =>{
									reject(error);
								})
							}else{
								resolve(newRes);
							}
						}
						return false;
					}
					reject(this.hook.error ? this.hook.error(response, option) : response);

				},
				fail: error =>{
					reject(this.hook.error ? this.hook.error(error, option) : error);

				}
			});
		});
	}
	use(hookName, cb){
		this.hook[hookName] = cb;
	}
	get(url, data, header){
		return this.method({method : 'GET', url, data, header});
	}
	post(url, data, header){
		return this.method({method : 'POST', url, data, header});
	}
	put(url, data, header){
		return this.method({method : 'PUT', url, data, header});
	}
	delete(url, data, header){
		return this.method({method : 'DELETE', url, data, header});
	}
}

export default  MsbUniRequest;