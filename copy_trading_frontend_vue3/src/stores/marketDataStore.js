import { defineStore } from 'pinia';
import { ref } from 'vue';
import webSocketManager from '@/utils/webSocketManager';

let wsUrl='ws://localhost:9099/ws/market-data';

export const useMarketDataStore = defineStore('marketData', () => {
  const marketData = ref([]);

  const updateMarketData = (data) => {
    marketData.value = data; // 更新市场数据
  };

  const startListening = () => {
    webSocketManager.addDynamicListener(
      'marketData',
      wsUrl, // 替换为实际 WebSocket 地址
      updateMarketData
    );
  };

  const stopListening = () => {
    webSocketManager.removeDynamicListener('marketData', updateMarketData);
  };

  return {
    marketData,
    startListening,
    stopListening,
  };
});

