<template>
  <div class="trade-page">
    <h1>交易页面</h1>
    <market-data-list />
    <asset-details />
  </div>
</template>

<script>
import MarketDataList from './component/MarketDataList.vue';
import AssetDetails from './component/AssetDetails.vue';

export default {
  components: {
    MarketDataList,
    AssetDetails
  }
};
</script>

<style scoped>
.trade-page {
  padding: 20px;
}
</style>