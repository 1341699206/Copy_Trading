// axios基础封装
import axios from "axios";
import { ElMessage } from "element-plus";
import { useUserStore } from "@/stores/user";
import router from '@/router'

const httpInstance = axios.create({
    baseURL: 'http://localhost:9099',
    timeout: 5000
})

//拦截器
// axios请求拦截器
httpInstance.interceptors.request.use(config =>{
    //从pinia获取token数据
    const userStore =useUserStore()
    //拼接token数据
    const token = userStore.userInfo.token
    if(token){
        config.headers.Authorization= `Bearer ${token}`
    }
    return config
},e=>Promise.reject(e))

// axios响应拦截器
httpInstance.interceptors.response.use(res=>res.data,e=>{
    const userStore =useUserStore()

    // 优先从后端返回的 Result 中提取 msg 字段作为错误提示
    const errorMessage = e.response?.data?.msg || e.response?.data?.error || 'Unknown error';

    //统一错误提示
    ElMessage({
        type:'warning',
        message: errorMessage
    })

    //token失效处理
    if(e.response.status === 401){
        userStore.clearUserInfo()
        router.push('/login')
    }

    return Promise.reject(e);
})

export default httpInstance
