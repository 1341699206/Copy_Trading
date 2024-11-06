<script setup>
import { computed, defineProps } from "vue";
import OverviewChart from "../Overview/component/OverviewChart.vue";
import TraderIntroduce from "../Overview/component/TraderIntroduce.vue";

const props = defineProps({
  traderBasicInf: {
    type: Object,
    default: null,
  },
});
// 提取 trader 的 id
const traderId = computed(() => props.traderBasicInf?.traderId);
</script>

<template>
  <div v-if="traderBasicInf">
    <trader-introduce :id="traderId"></trader-introduce>
    <overview-chart :trader-basic-inf="traderBasicInf"></overview-chart>
  </div>
</template>