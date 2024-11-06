<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import TraderColumn from './component/TraderColumn'
import TraderHomeBody from './component/Body/TraderHomeBody'
import { getTraderBasicInf } from "@/apis/follower";

const route = useRoute();
const traderId = route.params.id;  // 获取路由中的 id 参数

const traderBasicInf = ref(null);  // 用 ref 保存 API 数据
// 在组件挂载时获取 trader 数据
onMounted(async () => {
  try {
    traderBasicInf.value = await getTraderBasicInf(traderId);
  } catch (error) {
    //处理错误
  }
});
</script>

<template>
  <!-- 将获取到的数据传递给子组件 -->
  <trader-column :trader-basic-inf="traderBasicInf"></trader-column>
  <trader-home-body :trader-basic-inf="traderBasicInf"></trader-home-body>
</template>