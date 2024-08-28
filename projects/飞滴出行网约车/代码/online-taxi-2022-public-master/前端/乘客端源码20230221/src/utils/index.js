/*
 * @Author: ch cwl_ch@163.com
 * @Date: 2022-12-19 10:44:39
 * @LastEditors: ch
 * @LastEditTime: 2022-12-27 15:47:26
 * @Description: file content
 */
const HandleApiError = (error, name) =>{
    let result = false;
    if (error) {
        const tip = name ? `${name}错误：` : '';
        ShowToast(error.message ? `${tip}${error.message}` : `请求失败: ${error}`);
        result = true;
    }
    return result;
}
const ShowToast = (str) =>{
    uni.showToast({title:str,duration:3000, icon:'none'});
}
export {
    HandleApiError,
    ShowToast
}