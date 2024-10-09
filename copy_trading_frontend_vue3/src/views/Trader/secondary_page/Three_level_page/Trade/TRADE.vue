<template>
  <div>
    <!-- 交易卡片展示 -->
    <div class="trade-card-grid">
      <TradeCard
        v-for="asset in availableAssets"
        :key="asset.symbol"
        :asset="asset"
      />
    </div>
  </div>
</template>

<script>
import { useMarketDataStore } from '@/stores/marketDataStore';
import TradeCard from './component/TradeCard.vue';

export default {
  name: 'TRADE',
  components: {
    TradeCard
  },
  setup() {
    const marketDataStore = useMarketDataStore();
    
    // 获取可用资产列表
    const availableAssets = marketDataStore.availableAssets;

    // 在挂载时获取市场数据
    marketDataStore.fetchAvailableMarketData();

    return {
      availableAssets
    };
  },
};
</script>

<style scoped>
.trade-card-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); /* 网格布局，自动填充 */
  gap: 1rem; /* 卡片之间的间距 */
  padding: 1rem; /* 内边距 */
}
</style>
