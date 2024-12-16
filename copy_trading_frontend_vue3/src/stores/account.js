import { defineStore } from "pinia";
import { ref } from 'vue';
import { updateAccount, getAccountDetails, createAccount } from "@/apis/accountManagement";
import webSocketManager from '@/utils/webSocketManager';  // 导入自定义的 WebSocket 管理器

// 定义 WebSocket 的基础 URL 和处理器路径
const wsUrl = 'ws://localhost:9099/ws';  // 基础 WebSocket 地址
const handler = '/account';       // WebSocket 的处理器路径（具体业务）

export const useAccountStore = defineStore('account', () => {
    const accountInfo = ref({})

    const getAccountInfo = async (id) => {
        try {
            const res = await getAccountDetails(id);
            if (!res) throw new Error("Account need to be created!")
            // 将其他返回的数据存储到 accountInfo 中
            accountInfo.value = res;
        } catch (error) {
            console.error("Failed to fetch account info:", error);
            throw error;
        }
    }

    const createAAccount = async ({userId,initialBalance}) => {
        try {
            const res = await createAccount({userId,initialBalance})
            accountInfo.value = res
        } catch (error) {
            console.error("Failed to create account:", error);
        }
    }

    const updateAccountInfo = async (account) => {
        try {
            const res = await updateAccount(account)
            accountInfo.value = res
        } catch (error) {
            console.error("Failed to update account info:", error);
        }
    }

    // 更新 account 的方法
    const updateAccountData = (data) => {
        accountInfo.value = data; // 更新 marketData 数据
    };

    /**
     * 启动 WebSocket 监听
     * 该方法调用 webSocketManager.addDynamicListener 来连接 WebSocket 并开始接收数据
     */
    const startListening = (userId) => {
        // 调用 webSocketManager 的 addDynamicListener 方法，建立 WebSocket 连接并开始监听
        webSocketManager.addDynamicListener(
            wsUrl,               // WebSocket 服务器的 URL
            handler+'?userId='+userId,             // WebSocket 的处理器路径（指定订阅的主题）
            updateAccountData    // 接收到的数据会通过这个回调函数传递给 store
        );
    };

    /**
    * 停止 WebSocket 监听
    * 该方法调用 webSocketManager.removeDynamicListener 来移除监听器，停止接收数据
    */
    const stopListening = () => {
        // 调用 webSocketManager 的 removeDynamicListener 方法，停止监听
        webSocketManager.removeDynamicListener(
            handler,            // WebSocket 的处理器路径（取消订阅的主题）
            updateAccountData    // 停止接收并更新 marketData 的回调
        );
    };

    return {
        accountInfo,
        getAccountInfo,
        createAAccount,
        updateAccountInfo,
        startListening,
        stopListening
    };
}, {
    persist: {
        key: 'account', // 自定义存储的键名
        storage: sessionStorage // 指定存储方式为 sessionStorage
    }
})