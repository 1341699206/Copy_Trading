<script setup>
import { ref, provide } from "vue";
import MarketHeader from "./component/MarketHeader.vue";
import MarketDivision from "./component/MarketDivision/MarketDivision.vue";
import DisplayBoard from "./component/DisplayBoard.vue";

const selectedItem = ref(null);

// 定义更新选中数据项的函数
const selectItem = (item) => {
  selectedItem.value = item;
};

// 使用 provide 提供 `selectedItem` 和 `selectItem` 方法
provide("selectedItem", selectedItem);
provide("selectItem", selectItem);
</script>

<template>
  <market-header></market-header>
  <div class="market-container">
    <market-division class="left-side"></market-division>
    <div v-if="selectedItem" class="right-side">
      <display-board :item="selectedItem" ></display-board>
    </div>
    <div v-else class="right-side">
      <h2>Please select market data to view details.</h2>
    </div>
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
  border: 2px solid #4caf50; /* 绿色边框 */
  padding: 10px; /* 内边距 */
  margin-right: 20px; /* 增加左右两部分之间的距离 */
  border-radius: 10px; /* 设置圆角边框 */
  box-sizing: border-box; /* 包含内边距和边框 */
}

.right-side {
  flex: 3; /* 右侧占2份宽度 */
  border: 2px solid #2196f3; /* 蓝色边框 */
  padding: 10px; /* 内边距 */
  border-radius: 10px; /* 设置圆角边框 */
  box-sizing: border-box; /* 包含内边距和边框 */
}
</style>