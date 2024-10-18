<script setup>
import MarketDivisionHeader from "./component/MarketDivisionHeader.vue";
import MarketList from "./component/MarketList.vue";
import { ref } from "vue";

const selectedItem = ref("forex"); // 管理当前选中的模块
</script>

<template>
  <div>
    <market-division-header
      @update-content="selectedItem = $event"
    ></market-division-header>
    <market-list :content="selectedItem"></market-list>
  </div>
</template>
