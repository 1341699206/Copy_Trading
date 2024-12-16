import { defineStore } from "pinia";
import { ref } from 'vue';
import { openTrade, closeTrade, getOpenTrades, getTradesByStrategy } from '@/apis/tradeManagement';
import webSocketManager from '@/utils/webSocketManager';  // 导入自定义的 WebSocket 管理器

// 定义 WebSocket 的基础 URL 和处理器路径
const wsUrl = 'ws://localhost:9099/ws';  // 基础 WebSocket 地址
const handler = '/trades';       // WebSocket 的处理器路径（具体业务）

export const useTradeStore = defineStore('trade', () => {
    const tradeInfo = ref([])
    const openedTradeInfo = ref([])

    const getTradesInfo = async (strategyId) => {
        try {
            const res = await getTradesByStrategy(strategyId);
            // 将其他返回的数据存储到 tradeInfo 中
            tradeInfo.value = res;
        } catch (error) {
            console.error("Failed to fetch trade info:", error);
        }
    }

    const getOpenedTradeInfo = async (accountId) => {
        try {
            const res = await getOpenTrades(accountId);
            // 将其他返回的数据存储到 tradeInfo 中
            openedTradeInfo.value = res;
        } catch (error) {
            console.error("Failed to fetch opened trade info:", error);
        }
    }

    // 平仓
    const closeATrade = async (tradeId) => {
        try {
            await closeTrade(tradeId);
        } catch (error) {
            console.error("Failed to close the position:", error);
        }
    }

    // 开仓
    const openATrade = async ({ accountId, strategyId, symbol, type, lotSize }) => {
        try {
            await openTrade({ accountId, strategyId, symbol, type, lotSize })
        } catch (error) {
            console.error("Failed to open the position:", error)
        }
    }

    // 更新 trade 的方法
    const updateTradeData = (data) => {
        // 动态更新 tradeInfo
        if (data.closed === false) {
            tradeInfo.value.push(data);
        } else if (data.closed === true) {
            // 找到 tradeInfo 中的对应条目
            const tradeIndex = tradeInfo.value.findIndex(trade => trade.id === data.id);
            // 更新现有数据
            tradeInfo.value[tradeIndex] = { ...tradeInfo.value[tradeIndex], ...data };
        }

        // 根据 closed 字段处理 openedTradeInfo
        if (data.closed === false) {
            // 如果是未平仓数据，添加到 openedTradeInfo
            openedTradeInfo.value.push(data);
        } else if (data.closed === true) {
            // 如果是已平仓数据，从 openedTradeInfo 移除
            openedTradeInfo.value = openedTradeInfo.value.filter(trade => trade.id !== data.id);
        }
    };

    /**
     * 启动 WebSocket 监听
     * 该方法调用 webSocketManager.addDynamicListener 来连接 WebSocket 并开始接收数据
     */
    const startListening = (accountId) => {

        // 调用 webSocketManager 的 addDynamicListener 方法，建立 WebSocket 连接并开始监听
        webSocketManager.addDynamicListener(
            wsUrl,               // WebSocket 服务器的 URL
            handler + '/' + accountId,             // WebSocket 的处理器路径（指定订阅的主题）
            updateTradeData    // 接收到的数据会通过这个回调函数传递给 store
        );
    };

    /**
    * 停止 WebSocket 监听
    * 该方法调用 webSocketManager.removeDynamicListener 来移除监听器，停止接收数据
    */
    const stopListening = (accountId) => {
        // 调用 webSocketManager 的 removeDynamicListener 方法，停止监听
        webSocketManager.removeDynamicListener(
            handler + '/' + accountId,            // WebSocket 的处理器路径（取消订阅的主题）
            updateTradeData    // 停止接收并更新 marketData 的回调
        );
    };

    return {
        tradeInfo,
        openedTradeInfo,
        getTradesInfo,
        getOpenedTradeInfo,
        startListening,
        stopListening,
        closeATrade,
        openATrade
    };
}, {
    persist: {
        key: 'trade', // 自定义存储的键名
        storage: sessionStorage // 指定存储方式为 sessionStorage
    }
})