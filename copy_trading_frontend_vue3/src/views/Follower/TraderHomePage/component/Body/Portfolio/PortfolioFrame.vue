<script setup>
import PortfolioHeader from '../Portfolio/component/PortfolioNav.vue';
import PortfolioList from '../Portfolio/component/PortfolioList.vue';
import { ref } from "vue";

const selectedItem = ref("Trading History"); // 管理当前选中的模块
</script>

<template>
  <div>
    <portfolio-header
      @update-portfolioContent="selectedItem = $event"
    ></portfolio-header>
    <portfolio-list :portfolioContent="selectedItem"></portfolio-list>
  </div>
</template>