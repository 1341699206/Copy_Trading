<template>
  <div class="asset-details" v-if="selectedAsset">
    <h2>资产详情: {{ selectedAsset.instrument }}</h2>
    <p>当前价格: {{ selectedAsset.currentPrice }}</p>
    <p>最高价格: {{ selectedAsset.highPrice }}</p>
    <p>最低价格: {{ selectedAsset.lowPrice }}</p>
    <p>波动率: {{ selectedAsset.volatility }}</p>
  </div>
</template>

<script>
import { useMarketDataStore } from '@/stores/marketDataStore';
import { computed } from 'vue';

export default {
  setup() {
    const marketDataStore = useMarketDataStore();

    // 计算属性，获取选中的第一个资产（假设一次只能选一个）
    const selectedAsset = computed(() => {
      if (marketDataStore.selectedAssets.length > 0) {
        return marketDataStore.availableAssets.find(
          asset => asset.symbol === marketDataStore.selectedAssets[0]
        );
      }
      return null;
    });

    return {
      selectedAsset,
    };
  }
};
</script>

<style scoped>
.asset-details {
  margin-top: 20px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}
</style>