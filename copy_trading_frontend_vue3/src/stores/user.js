// userStore.js
import { defineStore } from "pinia";
import { ref } from 'vue';
import { login } from '@/apis/authentication';

export const useUserStore = defineStore('user', () => {
    const userInfo = ref({
        id: 0,
        username: null,
        email: null,
        role: null,
        createdAt: null,
        token: null
    });

    const getUserInfo = async ({ username, password }) => {
        try {
            const res = await login({ username, password, });
            // 将其他返回的数据存储到 userInfo 中
            userInfo.value = res.data;
        } catch (error) {
            console.error("Failed to fetch user info:", error);
        }
    };

    const clearUserInfo = () => {
        userInfo.value = {
            id: 0,
            username: null,
            email: null,
            role: null,
            createdAt: null,
            token: null
        };
        console.log("User info cleared.");
    };

    return {
        userInfo,
        getUserInfo,
        clearUserInfo
    };
}, {
    persist: true, // 开启数据持久化
    strategies: [
        {
            key: 'user', // 自定义存储的键名
            storage: sessionStorage // 指定存储方式为 sessionStorage
        }
    ]
});
