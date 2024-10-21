// src/store/userStore.js

import { defineStore } from "pinia";
import { ref } from 'vue';
import { loginAPI } from '@/apis/user';
import { on } from '@/utils/eventBus'; // 引入事件总线用于监听 WebSocket 消息

export const useUserStore = defineStore('user', () => {
    // 定义管理用户数据的 state
    const userInfo = ref({});

    // 定义获取接口数据的 action 函数
    const getUserInfo = async ({ email, password, role }) => {
        const res = await loginAPI({ email, password, role });
        userInfo.value = res.data;
    };

    // 退出登录时，清除用户信息
    const clearUserInfo = () => {
        userInfo.value = {};
    };

    // 注册 WebSocket 监听器，用于监听用户相关的 WebSocket 消息
    on('userInfoUpdate', (data) => {
        userInfo.value.user = data; // 当 WebSocket 收到用户信息更新时，更新 userInfo
        console.log('User info updated via WebSocket:', data);
    });

    // 返回 state 和 action
    return {
        userInfo,
        getUserInfo,
        clearUserInfo
    };
}, {
    persist: true, // 数据持久化
});
