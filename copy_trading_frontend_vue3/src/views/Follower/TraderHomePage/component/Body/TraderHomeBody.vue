<script setup>
import TraderHomeNav from "../Body/component/TraderHomeNav.vue";
import TraderDisplayFrame from "../Body/component/TraderDisplayFrame.vue";
import TraderSideFrame from "../Body/component/TraderSideFrame.vue";

import { ref } from "vue";
const selectedItem = ref("overview"); // 管理当前选中的模块

defineProps({
  traderBasicInf: {
    type: Object,
    default: null,
  }
});
</script>

<template>
  <div>
    <trader-home-nav
      @update-content="selectedItem = $event"
    ></trader-home-nav>
    <div class="frame-container">
      <trader-display-frame
        :content="selectedItem"
        :trader-basic-inf="traderBasicInf" 
        class="left-side"
      ></trader-display-frame>
      <trader-side-frame class="right-side"></trader-side-frame>
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