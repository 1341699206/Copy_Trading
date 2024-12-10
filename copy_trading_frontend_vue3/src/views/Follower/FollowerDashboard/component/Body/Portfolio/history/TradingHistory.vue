<script setup>
import TradeHistoryItem from "./TradeHistoryItem.vue";
import { useAccountStore } from "@/stores/account";
import { computed } from "vue";

const account = useAccountStore().accountInfo;
const tradesHistory = computed(() => account.trades);

</script>

<template>
  <div>
    <!-- 标签栏 -->
    <div class="table-header">
      <div class="header-item">Name</div>
      <div class="header-item">Date Closed</div>
      <div class="header-item">STD LOTS</div>
      <div class="header-item">OPEN / CLOSE</div>
      <div class="header-item">HIGH</div>
      <div class="header-item">LOW</div>
      <div class="header-item">ROLL</div>
      <div class="header-item">PROFIT</div>
      <div class="header-item">TOTAL</div>
    </div>

    <!-- 数据条目列表 -->
    <div v-if="tradesHistory && tradesHistory.length > 0">
      <trade-history-item
        v-for="item in traderTradesHistory"
        :key="item.tradeId"
        :item="item"
      ></trade-history-item>
    </div>
    <div v-else class="not-found">Not Found</div>
  </div>
</template>

<style scoped lang="scss">
.table-header {
  display: grid;
  grid-template-columns: 2fr 2fr 1fr 2fr 1fr 1fr 1fr 2fr 2fr;
  gap: 1rem;
  padding: 1rem 0;
  border-bottom: 2px solid #f0f0f0;
  font-weight: bold;
  color: #666;
  background-color: #f9f9f9;
}

.header-item {
  text-align: center;
  font-size: 0.8rem;
  color: #333;
}

.pagination-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20px;
}

.page-size-select {
  margin-left: 10px;
}
</style>
