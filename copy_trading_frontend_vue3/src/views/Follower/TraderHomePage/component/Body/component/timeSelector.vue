<script setup>
import { ref } from "vue";

const daysOptions = [
  { label: '1D', value: 1 },
  { label: '3D', value: 3 },
  { label: '7D', value: 7 },
  { label: '1M', value: 30 },
  { label: '3M', value: 90 },
  { label: '6M', value: 180 },
  { label: '1Y', value: 365 },
  { label: 'Overall', value: 10000 } // 用 10000 代表整体
];

const selectedDays = ref(1); // 默认选中的天数

// 发出选中的天数到父组件
const emit = defineEmits(["update:days"]);

const selectDays = (days) => {
  selectedDays.value = days;
  emit("update:days", days); // 传递选中的天数给父组件
};
</script>

<template>
  <div class="container">
    <ul>
      <li v-for="option in daysOptions" :key="option.value">
        <el-button
          :type="selectedDays === option.value ? 'primary' : 'default'"
          @click="selectDays(option.value)"
        >
          {{ option.label }}
        </el-button>
      </li>
    </ul>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  justify-content: center; /* 水平居中对齐 */
  padding: 10px; /* 增加一些内边距 */
}

.button-list {
  display: flex;
  list-style: none;
  padding: 0;
  margin: 0;
  justify-content: space-between; /* 均匀分布 */
  width: 100%; /* 使其占满父容器 */
}

li {
  flex: 1; /* 每个按钮占据相同的空间 */
  margin: 0 5px; /* 按钮之间的间隔 */
}
</style>