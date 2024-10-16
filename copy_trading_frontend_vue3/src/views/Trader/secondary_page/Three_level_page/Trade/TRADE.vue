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
import { onMounted, onBeforeUnmount, computed } from 'vue'; // 引入生命周期钩子和 computed

export default {
  name: 'TRADE',
  components: {
    TradeCard
  },
  setup() {
    const marketDataStore = useMarketDataStore();
    
    // 使用 computed 确保 availableAssets 的响应性
    const availableAssets = computed(() => marketDataStore.availableAssets);

    // 在组件挂载时启动定时任务
    onMounted(() => {
      console.log('Component mounted, starting auto update');
      marketDataStore.startAutoUpdate(); // 开启定时任务
    });

    // 在组件卸载时停止定时任务
    onBeforeUnmount(() => {
      console.log('Component before unmount, stopping auto update');
      marketDataStore.stopAutoUpdate(); // 停止定时任务，防止内存泄漏
    });

    return {
      availableAssets
    };
  }
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
