<script setup>
    import MarketHeader from './component/MarketHeader.vue'
    import MarketDivision from './component/MarketDivision/MarketDivision.vue'
    import DisplayBoard from './component/DisplayBoard.vue'
</script>

<template>
  <market-header></market-header>
  <div class="market-container">
    <market-division class="left-side"></market-division>
    <display-board class="right-side"></display-board>
  </div>
</template>

<style scoped>
.market-container {
  display: flex;
  margin: 0 20px; /* 左右各留出20px的外边距 */
  justify-content: space-between; /* 确保左右两部分分开 */
}

.left-side {
  flex: 2; /* 左侧占1份宽度 */
  border: 2px solid #4CAF50; /* 绿色边框 */
  padding: 10px; /* 内边距 */
  margin-right: 20px; /* 增加左右两部分之间的距离 */
  border-radius: 10px; /* 设置圆角边框 */
  box-sizing: border-box; /* 包含内边距和边框 */
}

.right-side {
  flex: 3; /* 右侧占2份宽度 */
  border: 2px solid #2196F3; /* 蓝色边框 */
  padding: 10px; /* 内边距 */
  border-radius: 10px; /* 设置圆角边框 */
  box-sizing: border-box; /* 包含内边距和边框 */
}
</style>