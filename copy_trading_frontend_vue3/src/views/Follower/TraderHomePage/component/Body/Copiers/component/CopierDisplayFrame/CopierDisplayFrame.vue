<script setup>
import getFollowerCopy from "@/apis/followerManagement";
import * as echarts from "echarts";
import { defineProps, ref, watch, onMounted, nextTick } from "vue";
import { useRoute } from "vue-router";

const chartRef = ref(null);
let chart = null;

const route = useRoute();
const traderId = route.params.id;

// 接收父组件传递的 item 对象
const props = defineProps({
  item: {
    type: Object,
    required: true,
  },
});

const followerCopy = ref(null);

// 获取跟随者的详细信息和收益历史
const fetchCopierItem = async () => {
  if (props.item) {
    try {
      const res = await getFollowerCopy({
        traderId: traderId,
        followerId: props.item.followerId,
      });
      followerCopy.value = res.data;

      // 获取 trader 和 copier 的收益历史数据
      const traderProfitHistory = followerCopy.value?.traderProfitHistory || [];
      const copierProfitHistory = followerCopy.value?.copierProfitHistory || [];

      if (chart) {
        const options = {
          xAxis: {
            type: "category",
            data: traderProfitHistory.map((data) => data.timeStamp), // X轴使用 traderProfitHistory 时间戳
          },
          yAxis: {
            type: "value",
          },
          series: [
            {
              name: "Trader Profit",
              data: traderProfitHistory.map((data) => data.profit),
              type: "line",
              smooth: true,
              lineStyle: {
                color: "blue", // 设置为蓝色
              },
              label: {
                show: true,
                position: "top",
                formatter: "{c}", // 显示数值
              },
            },
            {
              name: "Copier Profit",
              data: copierProfitHistory.map((data) => data.profit),
              type: "line",
              smooth: true,
              lineStyle: {
                color: "green", // 设置为绿色
              },
              label: {
                show: true,
                position: "top",
                formatter: "{c}", // 显示数值
              },
            },
          ],
          legend: {
            data: ["Trader Profit", "Copier Profit"], // 添加图例
          },
        };

        chart.setOption(options);
      }
    } catch (error) {
      console.error("Failed to fetch copier item:", error);
    }
  }
};

// 初始化 ECharts 图表
onMounted(async () => {
  await nextTick(); // 等待DOM更新
  if (chartRef.value) {
    chart = echarts.init(chartRef.value); // 确保在 DOM 挂载后初始化图表
    fetchCopierItem(); // 初始化后调用一次以设置图表
  }
});

//监听到item改变，则重新生成展示板。
watch(() => props.item, async () => {
  await nextTick(); // 等待DOM更新以确保图表元素存在
  if (chartRef.value && !chart) {
    chart = echarts.init(chartRef.value); // 初始化图表（如果尚未初始化）
  }
  fetchCopierItem(); // 更新图表数据
});
</script>

<template>
  <div class="container">
    <div v-if="item">
      <div class="name">{{ item.name }}</div>
      <div class="divider"></div>
      <div class="profit">
        <span>PROFIT:</span>
        <span>{{ item.profit }}</span>
      </div>
      <div class="divider"></div>
      <div class="chart" ref="chartRef" style="height: 400px; width: 100%;"></div>
    </div>
    <div v-else>Please select Copier</div>
  </div>
</template>

<style scoped lang="scss">
.divider {
  border-top: 1px solid #dcdcdc;
  margin-top: 15px;
  margin-bottom: 15px;
}

.name {
  font-weight: bold;
  font-size: 1.2rem;
  margin-bottom: 10px;
}

.profit {
  display: flex;
  gap: 10px;
  font-size: 1rem;
}

.chart {
  margin-top: 20px;
}
</style>
