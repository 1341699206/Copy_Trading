<script setup>
import { useMarketDataStore } from '@/stores/marketDataStore';
import { computed } from 'vue';
const marketDataStore=useMarketDataStore();
const marketData=computed(()=>marketDataStore.marketData);

const props=defineProps({
  openPositions: {
    type: Object,
  },
});

// 计算每个 position 的 current 值
const positionsWithCurrent = computed(() => {
  return props.openPositions.map(position => {
    const matchingMarketData = marketData.value.find(
      data => data.symbol === position.symbol
    );
    return {
      ...position,
      current: matchingMarketData ? matchingMarketData.current : "--",
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