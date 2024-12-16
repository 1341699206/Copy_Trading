<script setup>
import { computed } from "vue";
import { useTradeStore } from "@/stores/tradeData";
import { useAccountStore } from '@/stores/account';
import openPosition from "./component/openPosition";
//import orderPosition from './component/orderPosition';

const accountStore=useAccountStore();

const tradeStore=useTradeStore();
const openTrades=tradeStore.getOpenedTradeInfo(accountStore.accountInfo.id);

// 计算总利润
const totalProfit = computed(() =>
  openTrades
    .reduce((acc, position) => acc + position.profit, 0)
    .toFixed(2)
);

// 计算总手数
const totalLots = computed(() =>
  openTrades.reduce((acc, position) => acc + position.stdLots, 0)
);

// 关闭所有仓位
const closeAllPositions = () => {
  alert("All open positions have been closed.");
};

// 取消所有订单
const cancelAllOrders = () => {
  alert("All pending orders have been cancelled.");
};
</script>

<template>
  <div class="positions-page">
    <!-- Title -->
    <h1>Positions</h1>
    <div class="overview">
      <p>
        <strong>${{ totalProfit }}</strong> PROFIT
      </p>
      <p>{{ tradeTrade.length }} OPEN POSITIONS</p>
      <p>{{ totalLots }} STD LOTS</p>
    </div>
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
