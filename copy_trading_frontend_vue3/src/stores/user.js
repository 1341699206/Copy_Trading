import { defineStore } from "pinia";
import { ref } from 'vue';
import { loginAPI } from '@/apis/user';
import { on } from '@/utils/eventBus';

export const useUserStore = defineStore('user', () => {
    // 定义默认的 userInfo 结构，确保各字段存在
    const userInfo = ref({
        user: { name: null, email: null, followerId: null, tradingAccounts: [] },
        token: null,
    });

    // 登录并获取用户信息
    const getUserInfo = async ({ email, password, role }) => {
        try {
            const res = await loginAPI({ email, password, role });
            console.log("API response for getUserInfo:", res.data);
            userInfo.value = res.data; // 设置获取到的用户数据
        } catch (error) {
            console.error("Failed to fetch user info:", error);
            // 如果获取用户信息失败，将 userInfo 重置为默认结构
            userInfo.value = { user: { name: null, email: null, followerId: null, tradingAccounts: [] }, token: null };
        }
    };

    // 清除用户信息
    const clearUserInfo = () => {
        userInfo.value = { user: { name: null, email: null, followerId: null, tradingAccounts: [] }, token: null };
        console.log("User info cleared.");
    };

    // 监听 WebSocket 消息更新用户信息
    on('userInfoUpdate', (data) => {
        if (data) {
            // 更新 userInfo 中的 user 数据，而不影响 token
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
