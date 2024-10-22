<script setup>
import DashboardHeader from "../Body/component/DashboardHeader.vue";
import DashboardDisplayFrame from "../Body/component/DashboardDisplayFrame.vue";
import DashboardSideFrame from "../Body/component/DashboardSideFrame.vue";

import { ref } from "vue";
const selectedItem = ref("portfolio"); // 管理当前选中的模块
</script>

<template>
  <div>
    <dashboard-header
      @update-content="selectedItem = $event"
    ></dashboard-header>
    <div class="frame-container">
      <dashboard-display-frame
        :content="selectedItem"
        class="left-side"
      ></dashboard-display-frame>
      <dashboard-side-frame class="right-side"></dashboard-side-frame>
    </div>
  </div>
</template>

<style scoped>
.frame-container {
  display: flex;
  margin: 0 20px; /* 左右各留出20px的外边距 */
  justify-content: space-between; /* 确保左右两部分分开 */
}

.left-side {
  flex: 7; /* 左侧占3份宽度 */
  height: 430px;
  border: 2px solid #4caf50; /* 绿色边框 */
  padding: 10px; /* 内边距 */
  margin-right: 40px; /* 增加左右两部分之间的距离 */
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