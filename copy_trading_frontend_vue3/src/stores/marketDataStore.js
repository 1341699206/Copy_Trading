import { defineStore } from 'pinia';       // 导入 Pinia 用于定义 store
import { ref } from 'vue';                 // 从 Vue 导入 ref，用于响应式数据管理
import webSocketManager from '@/utils/webSocketManager';  // 导入自定义的 WebSocket 管理器

// 定义 WebSocket 的基础 URL 和处理器路径
const wsUrl = 'http://localhost:9099/ws';  // 基础 WebSocket 地址
const handler = '/topic/market-data';       // WebSocket 的处理器路径（具体业务）

// 使用 Pinia 定义一个名为 marketData 的 store
export const useMarketDataStore = defineStore('marketData', () => {
  // 声明一个响应式变量 marketData，用于存储市场数据
  const marketData = ref([]); // 存储市场数据的数组

  // 更新 marketData 的方法
  const updateMarketData = (data) => {
    marketData.value = data; // 更新 marketData 数据
  };

  /**
   * 启动 WebSocket 监听
   * 该方法调用 webSocketManager.addDynamicListener 来连接 WebSocket 并开始接收数据
   */
  const startListening = () => {
    // 调用 webSocketManager 的 addDynamicListener 方法，建立 WebSocket 连接并开始监听
    webSocketManager.addDynamicListener(
      wsUrl,               // WebSocket 服务器的 URL
      handler,             // WebSocket 的处理器路径（指定订阅的主题）
      updateMarketData    // 接收到的数据会通过这个回调函数传递给 store
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
      updateMarketData    // 停止接收并更新 marketData 的回调
    );
  };

  // 返回 store 中的状态和方法，供外部组件使用
  return {
    marketData,         // 响应式的市场数据
    startListening,     // 启动 WebSocket 监听的方法
    stopListening,      // 停止 WebSocket 监听的方法
  };
});
