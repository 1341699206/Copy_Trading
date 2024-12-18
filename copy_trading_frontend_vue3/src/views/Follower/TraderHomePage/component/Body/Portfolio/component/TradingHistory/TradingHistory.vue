<script setup>
import { computed } from "vue";
import { useTradeStore } from "@/stores/tradeData";

const tradeStore = useTradeStore();
const trades = computed(() => tradeStore.tradeInfo);
</script>

<template>
  <div class="history" v-if="trades && trades.length > 0">
    <div class="trade-table-container">
      <table class="trade-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Symbol</th>
            <th>Type</th>
            <th>Price Open</th>
            <th>Price Close</th>
            <th>Profit</th>
            <th>Date Open</th>
            <th>Date Close</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="trade in trades"
            :key="trade.id"
            :class="{ closed: trade.closed }"
          >
            <td>{{ trade.id }}</td>
            <td>{{ trade.symbol }}</td>
            <td>{{ trade.type }}</td>
            <td>{{ trade.priceOpen !== undefined ? trade.priceOpen.toFixed(2) : "--" }}</td>
            <td>{{ trade.priceClose !== undefined ? trade.priceClose.toFixed(2) : "--" }}</td>
            <td :class="{ profit: trade.profit >= 0, loss: trade.profit < 0 }">
              {{ trade.profit !== undefined ? trade.profit.toFixed(2) : "--" }}
            </td>
            <td>{{ trade.dateOpen }}</td>
            <td>{{ trade.dateClose || "--" }}</td>
            <td>{{ trade.closed ? "Closed" : "Open" }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <div v-else>
    <p>Loading trade data...</p>
  </div>
</template>

<style scoped>
.history {
  padding: -5px;
}

.trade-table-container {
  max-height: 400px; /* 设置容器最大高度 */
  overflow-y: auto; /* 启用垂直滚动条 */
  border: 1px solid #ddd; /* 添加边框方便识别滚动区域 */
}

.trade-table {
  width: 100%;
  border-collapse: collapse;
  margin: 0 auto;
}

.trade-table th,
.trade-table td {
  padding: 10px;
  text-align: center;
  border: 1px solid #ddd;
}

.trade-table thead {
  background-color: #f4f4f4;
  font-weight: bold;
}

.trade-table tbody tr:nth-child(odd) {
  background-color: #f9f9f9;
}

.trade-table tbody tr.closed {
  background-color: #e8f5e9;
}

.trade-table tbody tr:hover {
  background-color: #f1f1f1;
}

.trade-table td.profit {
  color: green;
}

.trade-table td.loss {
  color: red;
}
</style>

