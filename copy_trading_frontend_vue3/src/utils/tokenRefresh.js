import { renovateAPI } from "@/apis/user";
import { useUserStore } from "@/stores/user";

export function useTokenRefresh() {
    const userStore = useUserStore();

    const refreshToken = async () => {
        const token = userStore.userInfo.token;
        if (token) {
            try {
                await renovateAPI();
            } catch (error) {
                //处理token过期报错
            }
        }
    }

    return { refreshToken };
}
