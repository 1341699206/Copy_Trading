import axios from 'axios';

// 获取所有可用市场数据
export const fetchMarketDataAPI = () => {
  return axios.get('/api/market-data/available');
};

// 其他与市场数据相关的 API 调用可以放在这里
// export const anotherMarketDataAPI = () => { ... };
