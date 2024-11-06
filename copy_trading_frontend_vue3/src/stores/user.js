// userStore.js
import { defineStore } from "pinia";
import { ref } from 'vue';
import { loginAPI } from '@/apis/user';
import { on } from '@/utils/eventBus';

export const useUserStore = defineStore('user', () => {
    const userInfo = ref({
        user: { name: null, email: null, followerId: null, tradingAccounts: [] },
        token: null,
        role: null, // 新增 role 字段用于存储前端选择的角色
    });

    const getUserInfo = async ({ email, password, role }) => {
        try {
            const res = await loginAPI({ email, password, role });
            console.log("API response for getUserInfo:", res.data);

            // 手动设置 role，并将其他返回的数据存储到 userInfo 中
            userInfo.value = {
                ...res.data,
                role: role.toUpperCase() // 保存用户选择的角色，并转成大写
            };
        } catch (error) {
            console.error("Failed to fetch user info:", error);
            userInfo.value = { 
                user: { name: null, email: null, followerId: null, tradingAccounts: [] }, 
                token: null,
                role: null
            };
        }
    };

    const clearUserInfo = () => {
        userInfo.value = { 
            user: { name: null, email: null, followerId: null, tradingAccounts: [] }, 
            token: null,
            role: null
        };
        console.log("User info cleared.");
    };

    on('userInfoUpdate', (data) => {
        if (data) {
            Object.assign(userInfo.value.user, data);
            console.log('User info updated via WebSocket:', data);
        }
    });

    return {
        userInfo,
        getUserInfo,
        clearUserInfo
    };
}, {
    persist: true, // 开启数据持久化
});
