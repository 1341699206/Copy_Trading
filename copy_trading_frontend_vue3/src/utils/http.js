// axios基础封装
import axios from "axios";
import { ElMessage } from "element-plus";

const httpInstance = axios.create({
    baseURL: 'http://localhost:9099',
    timeout: 5000
})

//拦截器

// axios请求拦截器
httpInstance.interceptors.request.use(config =>{
    return config
},e=>Promise.reject(e))

// axios响应拦截器
httpInstance.interceptors.response.use(res=>res.data,e=>{
    //统一错误提示
    ElMessage({
        message:e.response.data
    })

    return Promise.reject(e)
})

export default httpInstance
