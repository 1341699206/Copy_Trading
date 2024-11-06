import { renovateAPI } from "@/apis/user";
import { useUserStore } from "@/stores/user";
import { useRouter } from "vue-router";
import { onMounted, onUnmounted } from 'vue';

export function useTokenRefresh() {
    const userStore = useUserStore();
    const router = useRouter();

    const refreshToken = async () => {
        const token = userStore.userInfo?.token;
        if (!token) {
            console.warn("Token is missing, skipping refresh.");
            return;
        }

        try {
            const response = await renovateAPI();
            const newToken = response.data?.token;
            if (newToken) {
                userStore.setUserInfo({ ...userStore.userInfo, token: newToken });
                console.log("Token refreshed successfully.");
            }
        } catch (error) {
            console.error("Failed to refresh token:", error);
            if (error.response?.status === 401) {
                userStore.clearUserInfo(); 
                router.push('/login'); 
            } else {
                console.warn("An unexpected error occurred during token refresh:", error);
            }
        }
    };

    // 定期刷新token
    onMounted(() => {
        const intervalId = setInterval(refreshToken, 15 * 60 * 1000); // 每15分钟刷新一次
        onUnmounted(() => clearInterval(intervalId)); // 清除定时器
    });

    return { refreshToken };
}
