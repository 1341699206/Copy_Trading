<script setup>
import { useTradeStore } from "@/stores/tradeData";
import { useMarketDataStore } from "@/stores/marketDataStore";
import OpenPositionItem from "./OpenPositionItem.vue";
import { computed, onMounted, onUnmounted } from "vue";
//import orderPosition from './component/orderPosition';

const marketDataStore = useMarketDataStore();

const tradeStore = useTradeStore();
const openTrades = computed(() => tradeStore.openedTradeInfo);

onMounted(() => {
  marketDataStore.startListening();
});

onUnmounted(() => {
  marketDataStore.stopListening();
});
</script>

<template>
  <div class="positions-page">
      <open-position-item :openPositions="openTrades"></open-position-item>
  </div>
</template>

<style scoped>
.positions-page {
  padding: -5px;
}

</style>