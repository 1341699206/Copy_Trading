<script setup>
defineProps({
  openPositions: {
    type: Object,
  },
});
</script>

<template>
  <!-- Open Positions Table -->
  <div class="positions-table">
    <table>
      <thead>
        <tr>
          <th>Trader</th>
          <th>Currency</th>
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
        <tr v-for="(position, index) in openPositions" :key="index">
          <td>{{ position.trader }}</td>
          <td>{{ position.currency }}</td>
          <td>{{ position.type }}</td>
          <td>{{ position.stdLots }}</td>
          <td>{{ position.dateOpened }}</td>
          <td>{{ position.entry }}</td>
          <td>{{ position.stop }}</td>
          <td>{{ position.limit }}</td>
          <td>{{ position.current }}</td>
          <td>{{ position.profit }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>