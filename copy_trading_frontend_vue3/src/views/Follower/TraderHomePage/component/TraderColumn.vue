<script setup>
import { defineProps } from "vue";

defineProps({
  traderBasicInf: {
    type: Object,
    default: null,
  },
});
</script>

<template>
  <div class="container">
    <div class="avatar">
      <!-- 头像区域，设置背景色以确保结构框架 -->
      <div v-if="traderBasicInf" class="avatar-content">
        <!-- <img :src="traderBasicInf.avatarUrl" alt="Trader Avatar" /> -->
      </div>
    </div>
    <div class="name">
      {{ traderBasicInf ? traderBasicInf.name : "Null" }}
    </div>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  align-items: center; /* 垂直居中 */
  padding-left: 1%;
}

.avatar {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  overflow: hidden;
  background-color: #f2f2f2;
  display: flex;
  justify-content: center;
  align-items: center;
}

.avatar-content {
  width: 100%;
  height: 100%;
  background-color: #e0e0e0; /* 默认背景色，如果没有头像，提供占位效果 */
}

.name {
  font-size: 20px;
  font-weight: bold;
  margin-left: 1%; /* 设置头像和名称的间距 */
}
</style>