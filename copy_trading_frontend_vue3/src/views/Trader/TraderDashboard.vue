<script setup>
import TraderHeader from "./component/Trader_Header.vue";
import TraderFooter from "./component/Trader_Footer.vue";
import TraderBasicLoad from "./component/traderBasicLoad.vue"
</script>


<template>
  <div>
    <TraderHeader></TraderHeader>
    <trader-basic-load></trader-basic-load>
    <!-- 二级路由出口 -->
    <router-view></router-view>
    <TraderFooter></TraderFooter>
  </div>
</template>

