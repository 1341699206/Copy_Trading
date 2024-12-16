<script setup>
import { useTradeStore } from "@/stores/tradeData";
import { useMarketDataStore } from '@/stores/marketDataStore';
import openPosition from "./component/openPosition";
import { computed, onMounted, onUnmounted } from "vue";
//import orderPosition from './component/orderPosition';

const marketDataStore=useMarketDataStore();

const tradeStore=useTradeStore();
const openTrades=computed(()=>tradeStore.openedTradeInfo);

// 关闭所有仓位
const closeAllPositions = () => {
  alert("All open positions have been closed.");
};

// 取消所有订单
const cancelAllOrders = () => {
  alert("All pending orders have been cancelled.");
};

onMounted(()=>{
  marketDataStore.startListening();
})

onUnmounted(()=>{
  marketDataStore.stopListening();
})
</script>

<template>
  <div class="positions-page">
    <!-- Title -->
    <h1>Positions</h1>
    <div>
      <open-position :openPositions="openTrades"></open-position>
    </div>
    <button @click="closeAllPositions" class="close-button">
      Close All Open Positions
    </button>
    <button @click="cancelAllOrders" class="cancel-button">
      Cancel All Pending Orders
    </button>
  </div>
</template>

<style scoped>
.positions-page {
  padding: 20px;
}

.close-button,
.cancel-button {
  margin-top: 20px;
  padding: 10px 20px;
  background-color: #d9534f;
  color: white;
  border: none;
  cursor: pointer;
}

.close-button:hover,
.cancel-button:hover {
  background-color: #c9302c;
}
</style>
