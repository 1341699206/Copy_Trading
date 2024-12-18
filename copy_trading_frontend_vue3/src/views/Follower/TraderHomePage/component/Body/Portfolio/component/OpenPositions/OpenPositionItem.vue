<script setup>
import { useMarketDataStore } from "@/stores/marketDataStore";
import { computed } from "vue";

const marketDataStore = useMarketDataStore();
const marketData = computed(() => marketDataStore.marketData);

const props = defineProps({
  openPositions: {
    type: Object,
  },
});

// 假设合同单位为 100,000
const CONTRACT_SIZE = 100000;

const positionsWithCurrent = computed(() => {
  return props.openPositions.map((position) => {
    const matchingMarketData = marketData.value.find(
      (data) => data.symbol === position.symbol
    );
    const currentPrice = matchingMarketData
      ? parseFloat(matchingMarketData.currentPrice)
      : null;

    const profit = currentPrice
      ? (position.type === "Buy"
          ? currentPrice - position.priceOpen
          : position.priceOpen - currentPrice) *
        position.lotSize *
        CONTRACT_SIZE
      : "--";

    return {
      ...position,
      current: currentPrice ? currentPrice.toFixed(3) : "--",
      profit: profit !== "--" ? profit.toFixed(2) : "--",
    };
  });
});

</script>

<template>
  <!-- Open Positions Table -->
  <div class="positions-table">
    <table>
      <thead>
        <tr>
          <th>Trader</th>
          <th>Type</th>
          <th>Std Lots</th>
          <th>Date Opened</th>
          <th>Entry</th>
          <th>Stop</th>
          <th>Limit</th>
          <th>Current</th>
          <th>Profit</th>
        </tr>
      </thead>
      <tbody>
        <tr v-if="openPositions.length === 0">
          <td colspan="10">You don't have any open positions.</td>
        </tr>
        <tr v-for="(position, index) in positionsWithCurrent" :key="index">
          <td>{{ position.symbol }}</td>
          <td>{{ position.type }}</td>
          <td>{{ position.lotSize }}</td>
          <td>{{ position.dateOpen }}</td>
          <td>{{ position.priceOpen.toFixed(2) }}</td>
          <td>{{ position?.stop ?? "--" }}</td>
          <td>{{ position?.limit ?? "--" }}</td>
          <td>{{ position.current }}</td>
          <td>{{ position.profit }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
/* Container for the positions table */
.positions-table {
  width: 100%;
  margin: 20px auto;
  overflow-x: auto;
  font-family: Arial, sans-serif;
}

/* Table styling */
table {
  width: 100%;
  border-collapse: collapse;
}

thead {
  background-color: #f4f4f4;
  text-align: left;
}

th, td {
  padding: 10px;
  border: 1px solid #ddd;
}

th {
  font-weight: bold;
}

tbody tr:nth-child(odd) {
  background-color: #f9f9f9;
}

/* Highlight on hover */
tbody tr:hover {
  background-color: #f1f1f1;
}

/* Button styling */
.close-btn {
  padding: 5px 10px;
  background-color: #ff4d4f;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  transition: background-color 0.3s;
}

.close-btn:hover {
  background-color: #d9363e;
}
</style>