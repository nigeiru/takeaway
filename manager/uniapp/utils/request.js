import apiConfig from '@/config.js'
const baseUrl = process.env.NODE_ENV === "development" ? apiConfig.dev.baseUrl : apiConfig.prod.baseUrl

// 定义一个名为 request 的常量，并赋值为一个箭头函数，接收一个参数 options，默认为空对象
const request = (options = {}) => {
    // 返回一个新的 Promise 对象，用于处理异步操作
    return new Promise((resolve, reject) => {
        // 调用 uni.request 方法发起网络请求
        uni.request({
            // 设置请求的 URL，baseUrl 是基础 URL，拼接上 options.url，如果未定义则使用空字符串
            url: baseUrl + (options.url || ''),
            // 设置请求的方法，默认为 'GET'
            method: options.method || 'GET',
            // 设置请求的数据，默认为空对象
            data: options.data || {},
            // 设置请求的头部信息，默认为包含 Content-Type 和 token 的对象
            header: options.header || {
                "Content-Type": "application/json",
                token: uni.getStorageSync('xm-user')?.token
            }
        }).then(res => {
            // 请求成功后的回调函数，解构出返回的数据
            let { data } = res;
            // 如果返回的状态码是 401，表示未授权，跳转到登录页面
            if (data.code === '401') {
                uni.navigateTo({
                    url: '/pages/login/login'
                });
            }
            // 将返回的数据传递给 resolve 回调函数
            resolve(data);
        }).catch(error => {
            // 请求失败后的回调函数，将错误传递给 reject 回调函数
            reject(error);
        });
    });
}

const get = (url, data, options = {}) => {
    options.method = 'GET'
    options.data = data
    options.url = url
    return request(options)
}

const post = (url, data, options = {}) => {
    options.method = 'POST'
    options.data = data
    options.url = url
    return request(options)
}

const put = (url, data, options = {}) => {
    options.method = 'POST'
    options.data = data
    options.url = url
    return request(options)
}

const del = (url, data, options = {}) => {
    options.method = 'POST'
    options.data = data
    options.url = url
    return request(options)
}

export default  {
    request,
    get,
    post,
	put,
	del
}