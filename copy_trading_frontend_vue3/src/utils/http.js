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
    //统一错误提示
    ElMessage({
        type:'warning',
        message: e.response.data.msg
    })

    //token失效处理
    if(e.response.status === 401){
        userStore.clearUserInfo()
        router.push('/login')
    }

    return Promise.reject(e);
})

export default httpInstance
