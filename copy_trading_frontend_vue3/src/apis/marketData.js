import axios from 'axios';
import request from "@/utils/http";

// 获取所有可用市场数据
export const fetchMarketDataAPI = () => {
  return axios.get('/api/market-data/available');
};

// 其他与市场数据相关的 API 调用可以放在这里
// export const anotherMarketDataAPI = () => { ... };

// 获取指定market的数据
export const getMarketDataAPI=({instrument,timePeriod})=>{
  // 动态构建请求 URL
  const url = `/api/market-data/market?id=${instrument}` + (timePeriod ? `&timePeriod=${timePeriod}` : '');
  return request({
    url: url
  })
}

// 卖出操作
export const sellAPI=({id,value})=>{
  return request({
    url: `api/market-data/sell`,
    method:"PATCH",
    data:{
      id,
      value,
    }
  })
}

// 买入操作
export const buyAPI=({id,value})=>{
  return request({
    url: `api/market-data/buy`,
    method:"PATCH",
    data:{
      id,
      value,
    }
  })
}
