<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import TraderColumn from "./component/TraderColumn";
import TraderHomeBody from "./component/Body/TraderHomeBody";
import { getTraderBasicInf } from "@/apis/follower";

const route = useRoute();
const traderId = route.params.id; // 获取路由中的 id 参数
const traderBasicInf = ref(null); // 用 ref 保存 API 数据
// 在组件挂载时获取 trader 数据
onMounted(async () => {
  try {
    traderBasicInf.value = await (await getTraderBasicInf(traderId)).data;
  } catch (error) {
    //处理错误
  }
});
</script>

<template>
  <!-- 将获取到的数据传递给子组件 -->
  <div v-if="traderBasicInf" class="container">
    <trader-column
      :trader-basic-inf="traderBasicInf"
      class="trader-column"
    ></trader-column>
    <div class="divider"></div>
    <trader-home-body :trader-basic-inf="traderBasicInf"></trader-home-body>
  </div>
</template>

<style scoped>
.trader-column {
  height: 11vh; /* 设置 trader-column 占页面高度的 10% */
}

trader-home-body {
  flex: 1; /* 使用剩余空间 */
}
.divider {
  border-top: 1px solid #dcdcdc;
  margin-top: 15px;
}
</style>