<script setup>
import { useMarketDataStore } from "@/stores/marketDataStore";
import { ref,onMounted, onBeforeUnmount, computed } from "vue"; // 引入生命周期钩子和 computed
import MarketItem from "../component/MarketItem.vue";

const marketDataStore = useMarketDataStore();

// 每个组件独立维护自己的 selectedAssets
const selectedAssets = ref([]);

// 使用 computed 获取 availableAssets
const marketData = computed(() => marketDataStore.availableAssets);

// 添加一个函数，用于选定多个 symbols
const selectAssets = (symbols) => {
  selectedAssets.value = marketData.value.filter(item =>
    symbols.includes(item.symbol)
  );
};

// 在组件挂载时启动定时任务
onMounted(() => {
  console.log("Component mounted, starting auto update");
  marketDataStore.startAutoUpdate(); // 开启定时任务
  selectAssets(['Major FX']);
});

// 在组件卸载时停止定时任务
onBeforeUnmount(() => {
  console.log("Component before unmount, stopping auto update");
  marketDataStore.stopAutoUpdate(); // 停止定时任务，防止内存泄漏
});
</script>


<template>
  <el-scrollbar>
    <div v-if="selectedAssets.length > 0">
      <market-item
        v-for="item in selectedAssets"
        :key="item.instrument"
        :item="item"
      ></market-item>
    </div>
    <!-- 如果没有数据，则显示 "Not Found" -->
    <div v-else class="not-found">
      Not Found
    </div>
  </el-scrollbar>
</template>

<style>
.el-scrollbar {
  height: 500px;
}

/* 中心对齐 "Not Found" */
.not-found {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%; /* 让它填满父容器 */
  font-size: 20px;
  color: #999;
}
</style>
