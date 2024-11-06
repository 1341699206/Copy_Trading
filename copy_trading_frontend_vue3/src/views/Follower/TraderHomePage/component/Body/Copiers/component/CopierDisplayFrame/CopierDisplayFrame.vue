<script setup>
import getFollowerCopy from "@/apis/follower";
import * as echarts from "echarts";
import { defineProps, ref, watch, onMounted } from "vue";
import { useRoute } from "vue-router";

const chartRef = ref(null);
let chart;

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

const fetchCopierItem = async () => {
  if (props.item) {
    try {
      const res = await getFollowerCopy({
        traderId: traderId,
        followerId: props.item.followerId,
      });
      followerCopy.value = res.data;

      // 获取 trader 和 copier 的收益历史数据
      const traderProfitHistory = followerCopy.value.traderProfitHistory;
      const copierProfitHistory = followerCopy.value.copierProfitHistory;

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
    } catch (error) {
      //处理问题
    }
  }
};

// 初始化 ECharts 图表
onMounted(async () => {
  chart = echarts.init(chartRef.value);
});

//监听到item改变，则重新生成展示板。
watch(props.item, fetchCopierItem);
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
      <div class="chart"></div>
      <div class="chart" ref="chartRef"></div>
    </div>
    <div v-else>Please select Copier</div>
  </div>
</template>

<style scoped lang="scss">
.divider {
  border-top: 1px solid #dcdcdc;
  margin-top: 15px;
}
</style>