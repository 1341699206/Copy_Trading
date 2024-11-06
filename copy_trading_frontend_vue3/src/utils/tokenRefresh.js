import { renovateAPI } from "@/apis/user";
import { useUserStore } from "@/stores/user";
import { useRouter } from "vue-router";

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
            // 如果 token 存在，则尝试刷新
            await renovateAPI();
            console.log("Token refreshed successfully.");
        } catch (error) {
            console.error("Failed to refresh token:", error);
            // 处理 token 过期，清除用户信息并重定向到登录页面
            userStore.clearUserInfo(); // 清除用户信息
            router.push('/login'); // 重定向到登录页面
        }
    };

    return { refreshToken };
}
