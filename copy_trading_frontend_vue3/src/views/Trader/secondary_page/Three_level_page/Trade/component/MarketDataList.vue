<template>
  <div class="market-data-list">
    <h2>可用市场数据</h2>
    <ul>
      <li v-for="asset in marketDataStore.availableAssets" :key="asset.symbol" 
          @click="toggleSelection(asset.symbol)"
          :class="{ selected: marketDataStore.selectedAssets.includes(asset.symbol) }">
        <span>{{ asset.instrument }}</span> - 当前价格: {{ asset.currentPrice }}
      </li>
    </ul>
  </div>
</template>

<script>
import { useMarketDataStore } from '@/stores/marketDataStore';
import { onMounted } from 'vue';

export default {
  setup() {
    const marketDataStore = useMarketDataStore();

    // 在组件挂载时启动数据加载
    onMounted(() => {
      marketDataStore.fetchAvailableMarketData();
      marketDataStore.startAutoUpdate(); // 启动自动更新
    });

    // 切换选中状态
    const toggleSelection = (symbol) => {
      marketDataStore.toggleAssetSelection(symbol);
    };

    return {
      marketDataStore,
      toggleSelection,
    };
  }
};
</script>

<style scoped>
.selected {
  background-color: #f0f8ff; /* 高亮选中的资产 */
}
</style>