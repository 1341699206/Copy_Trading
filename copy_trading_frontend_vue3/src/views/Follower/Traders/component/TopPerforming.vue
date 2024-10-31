<script setup>
import { useTradersDataStore } from "@/stores/tradersData";
import { onMounted,computed } from "vue";
import TraderCard from "../component/TraderCard.vue";

const tradersDataStore = useTradersDataStore();

const traders=computed(() => tradersDataStore.tradersData);
//检索的数量
const quantity = 10;

onMounted(() => {
  tradersDataStore.getTopTradersData(quantity);
  console.log(traders.value)
});

const scrollList = (direction) => {
  const container = document.querySelector(".list");
  const scrollAmount = 300; // 滚动的距离，根据卡片宽度调整
  if (direction === "left") {
    container.scrollLeft -= scrollAmount;
  } else {
    container.scrollLeft += scrollAmount;
  }
};
</script>


<template>
  <div class="title">Top Performing</div>
  <div class="carousel-container">
    <button @click="scrollList('left')" class="scroll-btn left">‹</button>
    <div class="list">
      <trader-card
        v-for="item in traders"
        :key="item.traderId"
        :item="item"
      ></trader-card>
    </div>
    <button @click="scrollList('right')" class="scroll-btn right">›</button>
  </div>
</template>

<style lang="scss" scoped>
.title {
  font-size: 24px; /* 设置较大的字体大小 */
  text-align: left; /* 内容左对齐 */
  margin-top: 20px; /* 添加上方间距 */
  margin-bottom: 20px; /* 添加下方间距 */
  padding-left: 40px; /* 添加左侧内边距 */
}
.carousel-container {
  display: flex;
  align-items: center;
  position: relative;
}

.list {
  display: flex;
  overflow-x: hidden;
  scroll-behavior: smooth;
  width: 95%; /* 设置一个合适的宽度以便展示卡片 */
}

.scroll-btn {
  background-color: #fff;
  border: none;
  font-size: 24px;
  cursor: pointer;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  z-index: 10;

  &.left {
    left: 10px;
  }

  &.right {
    right: 10px;
  }
}
</style>