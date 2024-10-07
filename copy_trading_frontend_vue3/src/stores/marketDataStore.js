import { defineStore } from 'pinia';
import axios from 'axios';

export const useMarketDataStore = defineStore('marketData', {
  state: () => ({
    availableAssets: [], // 存储从后端获取的所有可用市场数据
    selectedAssets: [],  // 存储当前选中的资产（用于展示）
  }),
  actions: {
    // 获取所有可用市场数据的动作
    async fetchAvailableMarketData() {
      try {
        const response = await axios.get('/api/market-data/available');
        this.availableAssets = response.data.data; // 假设返回的数据在 data 字段内
      } catch (error) {
        console.error('Failed to fetch available market data:', error);
      }
    },
    // 切换某个资产的选中状态
    toggleAssetSelection(symbol) {
      if (this.selectedAssets.includes(symbol)) {
        // 如果已选中，则移除
        this.selectedAssets = this.selectedAssets.filter(item => item !== symbol);
      } else {
        // 如果未选中，则添加
        this.selectedAssets.push(symbol);
      }
    },
  },
});
