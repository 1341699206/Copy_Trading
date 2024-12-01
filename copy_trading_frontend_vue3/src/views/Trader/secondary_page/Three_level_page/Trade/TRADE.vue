<template>
  <div>
    <!-- 市场数据展示 -->
    <div class="trade-card-grid">
      <TradeCard
        v-for="asset in availableAssets"
        :key="asset.symbol"
        :asset="asset"
      />
    </div>
  </div>
</template>

<script setup>
import { useMarketDataStore } from '@/stores/marketDataStore'; // 引入新的 marketDataStore
import TradeCard from './component/TradeCard.vue'; // 引入 TradeCard 组件
import { computed, onMounted, onBeforeUnmount } from 'vue'; // Vue 钩子和计算属性

// 使用 store 获取市场数据
const marketDataStore = useMarketDataStore();
const availableAssets = computed(() => marketDataStore.marketData); // 响应式的市场数据

// 组件挂载时启动监听，卸载时停止监听
onMounted(() => {
  console.log('Component mounted, starting WebSocket connection');
  marketDataStore.startListening();
});

onBeforeUnmount(() => {
  console.log('Component before unmount, stopping WebSocket connection');
  marketDataStore.stopListening();
});
</script>

<style scoped>
.trade-card-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); /* 响应式网格布局 */
  gap: 1rem; /* 卡片之间的间距 */
  padding: 1rem; /* 容器内边距 */
}
</style>
