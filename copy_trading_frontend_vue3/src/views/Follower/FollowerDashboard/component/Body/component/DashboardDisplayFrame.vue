<script setup>
import PortfolioFrame from '../Portfolio/PortfolioFrame.vue';
import OverviewFrame from '../Overview/OverviewFrame.vue';
import AdvancedStatsFrame from '../AdvancedStats/AdvancedStatsFrame.vue';
import CopiersFrame from '../Copiers/CopiersFrame.vue';

defineProps({
  content: {
    type: String,
    required: true,
  },
});
</script>

<template>
  <div class="frame-content">
    <div v-if="content === 'portfolio'"><portfolio-frame></portfolio-frame></div>
    <div v-else-if="content === 'overview'"><overview-frame></overview-frame></div>
    <div v-else-if="content === 'advancedStats'"><advanced-stats-frame></advanced-stats-frame></div>
    <div v-else-if="content === 'copiers'"><copiers-frame></copiers-frame></div>
  </div>
</template>