<script setup>
import { defineProps } from "vue";

defineProps({
  traderBasicInf: {
    type: Object,
    default: null,
  }
});
</script>

<template>
  <div v-if="traderBasicInf" class="avatar">
    <!-- <img :src="traderBasicInf.avatarUrl" alt="Trader Avatar" /> -->
  </div>
  <div v-if="traderBasicInf" class="name">{{ traderBasicInf.name }}</div>
</template>

<style scoped>
.avatar {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  overflow: hidden;
  background-color: #f2f2f2;
}

.avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.name {
  font-size: 20px;
  font-weight: bold;
  margin-top: 10px;
  text-align: center;
}
</style>