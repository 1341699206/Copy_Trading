<script setup>
import { getMarketDataAPI } from "@/apis/marketData";
import { inject, onMounted, ref } from "vue";
import * as echarts from "echarts"; //导入制图组件

const item = inject("selectedItem").value; // 注入选中项的数据
const marketInfo = ref([]); // 初始化 marketInfo 为数组，便于存储列表数据
const chartRef = ref(null); // 用于引用 chart 容器

onMounted(async () => {

  const res =await getMarketDataAPI({ instrument: item.instrument });
  marketInfo.value = res.data;
  // 初始化 ECharts 实例
  const chart = echarts.init(chartRef.value);

  // 设置图表选项
  const options = {
    title: {
      text: `${item.instrument} 历史数据曲线`,
    },
    xAxis: {
      type: "category",
      data: marketInfo.value.map((data) => data.timestamp), // 假设数据包含时间字段
    },
    yAxis: {
      type: "value",
    },
    series: [
      {
        data: marketInfo.value.map((data) => data.price), // 假设数据包含价格字段
        type: "line",
        smooth: true,
      },
    ],
  };

  // 应用选项到图表
  chart.setOption(options);
});
</script>

<template>
  <div>
    <div class="avatar"></div>
    <div class="title">{{ item.instrument }}</div>
    <div class="information">
      <ul>
        <li class="open">Open {{ item.openPrice }}</li>
        <li class="high">High {{ item.highPrice }}</li>
        <li class="low">Low {{ item.lowPrice }}</li>
        <li class="now">{{ item.currentPrice }}</li>
      </ul>
    </div>
    <div class="chart" ref="chartRef"></div>
  </div>
</template>

<style scoped lang="scss">
.chart {
  width: 100%;
  height: 400px;
}
</style>