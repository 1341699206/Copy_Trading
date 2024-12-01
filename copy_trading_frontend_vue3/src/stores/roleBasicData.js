import { defineStore } from "pinia";
import { ref } from 'vue';
import { getRoleBasicInfo } from '';

export const useRoleStore = defineStore('role', () => {
    const roleInfo = ref({})

    const getRoleInfo = async (id) => {
        try {
            const res = await getRoleBasicInfo(id);
            // 将其他返回的数据存储到 userInfo 中
            roleInfo.value = res;
        } catch (error) {
            console.error("Failed to fetch user info:", error);
        }
    }

    return {
        roleInfo,
        getRoleInfo
    };
}, {
    persist: {
        key: 'role', // 自定义存储的键名
        storage: sessionStorage // 指定存储方式为 sessionStorage
    }
})