<script setup>
import TradingHistory from "../history/TradingHistory.vue";
import openPositions from "../open/OpenPositions";

defineProps({
  portfolioContent: {
    type: String,
    required: true,
  },
});
</script>

<template>
  <div class="portfolio-content">
    <div v-if="portfolioContent === 'open'">
      <open-positions></open-positions>
    </div>
    <div v-else-if="portfolioContent === 'pending'"></div>
    <div v-else-if="portfolioContent === 'history'">
      <trading-history></trading-history>
    </div>
  </div>
</template>