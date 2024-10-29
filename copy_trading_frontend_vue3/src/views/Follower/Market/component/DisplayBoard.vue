<script setup>
import { getMarketDataAPI } from "@/apis/marketData";
import { inject, onMounted, ref } from "vue";
import * as echarts from "echarts"; //导入制图组件

const item = inject("selectedItem").value; // 注入选中项的数据
const marketInfo = ref([]); // 初始化 marketInfo 为数组，便于存储列表数据
const chartRef = ref(null); // 用于引用 chart 容器

onMounted(async () => {
  const res = await getMarketDataAPI({ instrument: item.instrument });
  marketInfo.value = res.data;
  // 初始化 ECharts 实例
  const chart = echarts.init(chartRef.value);

  // 设置图表选项
  const options = {
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
  <div class="container">
    <div class="header">
      <div class="avatar"></div>
      <div class="title">{{ item.instrument }}</div>
    </div>
    <div class="information">
      <ul>
        <li class="open">
          <span class="label">Open</span>
          <span class="value black">{{
            item.openPrice ? item.openPrice.toFixed(3) : "N/A"
          }}</span>
        </li>
        <li class="high">
          <span class="label">High</span>
          <span class="value green">{{ item.highPrice.toFixed(3) }}</span>
        </li>
        <li class="low">
          <span class="label">Low</span>
          <span class="value red">{{ item.lowPrice.toFixed(3) }}</span>
        </li>
        <li class="now">
          <span class="label">Current</span>
          <span class="value current">{{ item.currentPrice.toFixed(3) }}</span>
        </li>
      </ul>
    </div>
    <div class="chart" ref="chartRef"></div>
  </div>
</template>

<style scoped lang="scss">
.container {
  display: flex;
  flex-direction: column;
  height: 100%;

  .header {
    display: flex;
    align-items: center;
    padding: 10px;

    .avatar {
      width: 40px; // 根据需要调整头像的宽度
      height: 40px; // 根据需要调整头像的高度
      border-radius: 50%;
      background-color: #ccc; // 示例背景色
      margin-right: 10px;
    }

    .title {
      font-size: 20px; // 根据需要调整标题字体大小
      font-weight: bold; // 加粗字体
    }
  }

  .information {
    padding: 10px;
    margin-top: -25px;

    ul {
      display: flex;
      list-style: none;
      padding: 0;

      li {
        margin-right: 20px; // 调整各个li之间的间距
        font-size: 14px; // 调整Open, High, Low的整体大小
        display: flex; // 使li为flex容器
        align-items: flex-end; // 底边对齐

        .label {
          color: gray; // 提示词颜色
          margin-right: 5px; // 提示词和数字之间的间距
        }

        .value {
          font-size: 18px; // 一般数字的字体大小

          &.current {
            margin-top: -10px;
            font-size: 30px; // 放大Current的数字字体
            font-weight: bold; // 加粗字体
          }

          &.black {
            color: black;
          }

          &.green {
            color: green;
          }

          &.red {
            color: red;
          }
        }
      }
    }
  }

  .chart {
    flex-grow: 1;
    width: 100%;
    min-height: 400px;
    height: 90%;
    margin-top: -15px;
  }
}
</style>