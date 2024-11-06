<script setup>
import TradingHistory from './TradingHistory/TradingHistory.vue'
import OpenPositions from './OpenPositions/OpenPositions.vue'
defineProps({
  portfolioContent: {
    type: String,
    required: true,
  },
});
</script>

<template>
  <div class="portfolio-content">
    <div v-if="portfolioContent === 'Trading History'"><trading-history></trading-history></div>
    <div v-else-if="portfolioContent === 'Open Positions'"><open-positions></open-positions></div>
  </div>
</template>