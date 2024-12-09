<script setup>
import { getStrategyByTraderId } from "@/apis/strategyManagement";

const props = defineProps({
  id: {
    type: Object,
  },
});

const strategyInfo=getStrategyByTraderId(props.id);
</script>

<template>
  <div>
    <div class="StrategyDescription">
      {{strategyInfo.description}}
    </div>
    <div></div>
  </div>
</template>