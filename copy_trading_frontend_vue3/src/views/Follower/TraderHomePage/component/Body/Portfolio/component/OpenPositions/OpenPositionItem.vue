<script setup>
import { defineProps } from "vue";
// 定义组件的 props，接收父组件传递的 `item` 数据
defineProps({
  item: {
    type: Object,
    required: true,
  },
});
</script>

<template>
  <div class="data-entry">
    <div class="name">{{ item.currency }}</div>
    <div class="type">{{ item.type }}</div>
    <div class="standardLots">{{ item.standardLots }}</div>
    <div class="DateOpen">{{ item.dateOpen }}</div>
    <div class="Open">{{ item.priceOpen }}</div>
    <div class="stop">{{ item.stop }}</div>
    <div class="limit">{{ item.limit }}</div>
    <div class="current">{{ item.PriceCurrent }}</div>
    <div :class="['profit', { negative: item.profit < 0 }]">
      {{ item.profit > 0 ? '$' + item.profit : '-$' + Math.abs(item.profit) }}
    </div>
  </div>
</template>

<style scoped lang="scss">
.data-entry {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr;
  align-items: center;
  gap: 1rem;
  padding: 1rem 0;
  border-bottom: 1px solid #f0f0f0;

  .name,
  .type,
  .standardLots,
  .DateOpen,
  .Open,
  .stop,
  .limit,
  .current,
  .profit {
    font-size: 0.8rem;
    color: #333;
    text-align: center;
  }

  .DateOpen {
    white-space: nowrap;
    color: #666;
  }

  .profit {
    font-weight: bold;
    color: #0f9d58; // Green color for positive profit
    &.negative {
      color: #d93025; // Red color for negative profit
    }
  }
}
</style>