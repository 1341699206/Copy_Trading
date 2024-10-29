import { defineStore } from 'pinia';
import { ref } from 'vue';
import { fetchMarketDataAPI } from '@/apis/marketData'; // 从刚刚创建的 API 文件中导入函数

export const useMarketDataStore = defineStore('marketData', () => {
  const availableAssets = ref([]);
  const selectedAssets = ref([]);
  const intervalId = ref(null);

  // 获取所有可用市场数据的动作
  const fetchAvailableMarketData = async () => {
    try {
      const response = await fetchMarketDataAPI();
      if (response.data && response.data.code === 1) {
        availableAssets.value = response.data.data;
        //console.log('Fetched available market data:', availableAssets.value);
      } else {
        console.warn('No market data found or unexpected response format:', response.data);
      }
    } catch (error) {
      console.error('Failed to fetch available market data:', error);
    }
  };

  // 启动定时任务，每分钟获取一次市场数据
  const startAutoUpdate = () => {
    if (intervalId.value) {
      stopAutoUpdate();
    }
    fetchAvailableMarketData();
    // intervalId.value = setInterval(() => {
    //   fetchAvailableMarketData();
    // }, 1000);
  };

  // 停止定时任务
  const stopAutoUpdate = () => {
    if (intervalId.value) {
      clearInterval(intervalId.value);
      intervalId.value = null;
    }
  };

  // 切换某个资产的选中状态
  const toggleAssetSelection = (symbol) => {
    if (selectedAssets.value.includes(symbol)) {
      selectedAssets.value = selectedAssets.value.filter(item => item !== symbol);
    } else {
      selectedAssets.value.push(symbol);
    }
  };

  return {
    availableAssets,
    selectedAssets,
    fetchAvailableMarketData,
    startAutoUpdate,
    stopAutoUpdate,
    toggleAssetSelection
  };
});
