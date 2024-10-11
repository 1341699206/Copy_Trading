<script setup>

import CommoditiesList from "../Commodities/CommoditiesList.vue";
import CyptoList from "../CyptoCFD/CyptoCFDList.vue";
import ForexList from "../Forex/ForexList.vue";
import IndicesList from "../Indices/IndicesList.vue";
import StocksList from "../Stocks/StocksList.vue";


defineProps({
  content: {
    type: String,
    required: true,
  },
});
</script>

<template>
  <div class="list-content">
    <div v-if="content === 'forex'"><forex-list></forex-list></div>
    <div v-else-if="content === 'stocks'"><stocks-list></stocks-list></div>
    <div v-else-if="content === 'crypto'"><cypto-list></cypto-list></div>
    <div v-else-if="content === 'indices'"><indices-list></indices-list></div>
    <div v-else-if="content === 'commodities'"><commodities-list></commodities-list></div>
  </div>
</template>