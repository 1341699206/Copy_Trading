<script setup>
import { defineProps, inject,computed } from "vue";

// 定义组件的 props，接收父组件传递的 `item` 数据
const props = defineProps({
  item: {
    type: Object,
    required: true,
  },
});

// 可以定义按钮点击时触发的事件（例如，购买和出售的逻辑）
const handleBuy = () => {
  console.log("Buy clicked for", props.item);
  // 你可以在这里添加实际的购买逻辑
};

const handleSell = () => {
  console.log("Sell clicked for", props.item);
  // 你可以在这里添加实际的出售逻辑
};

// 从顶层父组件中注入 `selectItem` 方法
const selectItem = inject("selectItem");

// 点击条目触发事件，将数据传递给顶层父组件
const handleClick = () => {
  selectItem(props.item); // 传递选中的数据项
};

// 计算百分比格式的 ROI
const change = computed(() => {
  const c = (props.item.currentPrice - props.item.openPrice)/props.item.openPrice
  return `${(c * 100).toFixed(2)}%`; // 转换为百分比并保留两位小数
});
</script>

<template>
  <div class="container" @click="handleClick">
    <!-- 头像部分 -->
    <div class="avatar">
      <!-- <img :src="props.item.avatarUrl" alt="avatar" /> -->
    </div>

    <!-- 名称部分 -->
    <div class="name">
      {{ props.item.symbol }}
    </div>

    <!-- 数据展示部分 -->
    <div class="value">
      <ul>
        <!-- 当前值 -->
        <li class="curValue">
          <strong>Current:</strong> {{ item.currentPrice.toFixed(3) }}
        </li>
        <!-- 变化百分比 -->
        <li class="change"><strong>Change:</strong> {{ change }}</li>
      </ul>
    </div>

    <!-- 购买和出售按钮 -->
    <el-button @click="handleBuy">Buy</el-button>
    <el-button @click="handleSell">Sell</el-button>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: row; /* 水平排列 */
  padding: 5px; /* 减少padding，整体紧凑 */
  border: 1px solid #ccc;
  border-radius: 5px;
  margin: 0 auto; /* 左右自动居中，使得左侧和右侧的间距一致 */
  margin-bottom: 5px; /* 减少容器底部间距 */
  width: 100%; /* 占满父容器宽度 */
  justify-content: flex-start; /* 左对齐 */
  align-items: center; /* 垂直居中 */
  box-sizing: border-box; /* 确保padding和border包含在宽度内 */
}

.avatar {
  width: 40px; /* 缩小头像尺寸 */
  height: 40px;
  border-radius: 50%;
  overflow: hidden;
  margin-right: 8px; /* 减少右边距 */
}

.avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.name {
  font-size: 14px; /* 缩小字体 */
  font-weight: bold;
  margin-right: 10px; /* 缩小与数据展示部分的间距 */
}

.value {
  display: flex;
  flex-direction: row;
  margin-right: 10px; /* 减少右侧间距 */
  flex-grow: 1; /* 让 value 区域占满剩余空间 */
}

.value ul {
  list-style: none;
  padding: 0;
  display: flex;
  flex-direction: row;
  margin: 0; /* 去掉默认的margin */
  flex-grow: 1; /* 让数据展示占满可用空间 */
}

.value li {
  font-size: 12px; /* 缩小字体 */
  margin-right: 10px; /* 缩小数据项之间的间距 */
}

.el-button {
  margin-left: 5px; /* 按钮之间的间距减小 */
  padding: 3px 8px; /* 缩小按钮的内边距 */
}
</style>
