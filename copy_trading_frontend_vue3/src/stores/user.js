//管理用户数据相关
import { defineStore } from "pinia";
import {ref} from 'vue'
import { loginAPI } from '@/apis/user'
export const useUserStore =defineStore('user',()=>{
    //定义管理用户数据的state
    const userInfo=ref({})
    //定义获取接口数据的action函数
    const getUserInfo =async ({email,password,role})=>{
        const res = await loginAPI({email,password,role})
        userInfo.value=res.data
    }

    //退出登录时，清除用户信息
    const clearUserInfo=()=>{
        userInfo.value={}
    }

    //以对象格式吧state和action return
    return{
        userInfo,
        getUserInfo,
        clearUserInfo
    }
},{
    persist: true,
})