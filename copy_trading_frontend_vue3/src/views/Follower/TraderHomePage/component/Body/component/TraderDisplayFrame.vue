<script setup>
import { computed, defineProps } from "vue";
import PortfolioFrame from '../Portfolio/PortfolioFrame.vue';
import OverviewFrame from '../Overview/OverviewFrame.vue';
import AdvancedStatsFrame from '../AdvancedStats/AdvancedStatsFrame.vue';
import CopiersFrame from '../Copiers/CopiersFrame.vue';

const props = defineProps({
  content: {
    type: String,
    required: true,
    default: "overview",
  },
  traderBasicInf: {
    type: Object,
    default: null,
  }
});
// 提取 trader 的 id
const traderId = computed(() => props.traderBasicInf?.traderId);
</script>

<template>
  <div class="frame-content">
    <div v-if="content === 'portfolio'"><portfolio-frame :id="traderId"></portfolio-frame></div>
    <div v-else-if="content === 'overview'"><overview-frame :trader-basic-inf="traderBasicInf" ></overview-frame></div>
    <div v-else-if="content === 'advancedStats'"><advanced-stats-frame :id="traderId"></advanced-stats-frame></div>
    <div v-else-if="content === 'copiers'"><copiers-frame :id="traderId"></copiers-frame></div>
  </div>
</template>