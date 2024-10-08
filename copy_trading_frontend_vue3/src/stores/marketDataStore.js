import { defineStore } from 'pinia';
import axios from 'axios';

export const useMarketDataStore = defineStore('marketData', {
  state: () => ({
    availableAssets: [], // 存储从后端获取的所有可用市场数据
    selectedAssets: [],  // 存储当前选中的资产（用于展示）
    intervalId: null,    // 存储定时器 ID 以便于清除
  }),
  actions: {
    // 获取所有可用市场数据的动作
    async fetchAvailableMarketData() {
      try {
        const response = await axios.get('/api/market-data/available');
        console.log('Full response:', response); // 添加日志检查完整响应结构
        
        // 增加对 response.data 和 response.data.code 的检查
        if (response.data && response.data.code === 1) {
          this.availableAssets = response.data.data; // 提取返回的数据部分
          console.log('Fetched available market data:', this.availableAssets); // 添加日志检查是否成功获取数据
        } else {
          console.warn('No market data found or unexpected response format:', response.data);
        }

      } catch (error) {
        console.error('Failed to fetch available market data:', error);
      }
    },

    // 启动定时任务，每分钟获取一次市场数据
    startAutoUpdate() {
      // 如果已经有定时器，先停止之
      if (this.intervalId) {
        this.stopAutoUpdate();
      }
      // 启动定时器
      this.intervalId = setInterval(() => {
        this.fetchAvailableMarketData();
      }, 60000); // 每 60000 毫秒（即 1 分钟）执行一次
    },

    // 停止定时任务
    stopAutoUpdate() {
      if (this.intervalId) {
        clearInterval(this.intervalId);
        this.intervalId = null;
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
