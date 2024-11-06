<script setup>
import { ref, onMounted } from "vue";
import { getTraderDetailInf } from "@/apis/follower";
import timeSelector from "@/views/Follower/TraderHomePage/component/Body/component/timeSelector.vue";
import * as echarts from "echarts";

const traderHistoryInfo = ref([]);
const traderDetailInfo = ref(null);
const chartRef = ref(null);
let chart;

const selectedDays = ref(180); // 默认选中的天数

// 接收天数信息
const updateDays = (days) => {
  selectedDays.value = days;
  fetchTraderData(); // 重新获取数据并更新图表
};

const props = defineProps({
  traderBasicInf: {
    type: Object,
    default: null,
  },
});

// 获取对应的traders数据并更新图表
const fetchTraderData = async () => {
  // 检查 item 是否存在
  const res = await getTraderDetailInf({
    id: props.traderBasicInf.value.traderId,
    timePeriod: selectedDays,
  });
  traderDetailInfo.value = res.data;
  traderHistoryInfo.value = traderDetailInfo.value.traderHistoryData;

  const options = {
    xAxis: {
      type: "category",
      data: traderHistoryInfo.value.map((data) => data.timestamp),
    },
    yAxis: {
      type: "value",
    },
    series: [
      {
        data: traderHistoryInfo.value.map((data) => data.ROI),
        type: "line",
        smooth: true,
      },
    ],
  };

  chart.setOption(options);
};

// 初始化 ECharts 图表
onMounted(async () => {
  chart = echarts.init(chartRef.value);
  await fetchTraderData();
});
</script>

<template>
  <div class="over">
    <ul>
      <li>
        <div class="roi">
          <span class="value green">{{
            traderBasicInf.ROI ? traderBasicInf.ROI.toFixed(3) : "N/A"
          }}</span>
          <span class="label">ROI</span>
        </div>
      </li>
      <li>
        <div class="winRate">
          <span class="value black"
            >{{
              traderBasicInf.winRate
                ? traderBasicInf.winRate.toFixed(3)
                : "N/A"
            }}%</span
          >
          <span class="label">WIN RATE</span>
        </div>
      </li>
      <li>
        <div class="copiers">
          <span class="value black">{{ traderBasicInf.copiers }}</span>
          <span class="label">COPIERS</span>
        </div>
      </li>
    </ul>
  </div>
  <div class="chart" ref="chartRef"></div>
  <div class="timeSelector">
    <time-selector @update:days="updateDays"></time-selector>
  </div>
<div class="data">
  <div class="title">STATISTICS - OVERALL</div>
  <div class="data-grid">
    <div class="data-item profit">
      <span class="label">PROFIT</span>
      <span class="value green">{{ traderDetailInfo.profit ? traderBasicInf.profit.toFixed(3) : 0 }}</span>
    </div>
    <div class="data-item trades">
      <span class="label">TRADES</span>
      <span class="value black">{{ traderDetailInfo.trades ? traderBasicInf.trades.toFixed(3) : 0 }}</span>
    </div>
    <div class="data-item maxOpenTrades">
      <span class="label">MAX OPEN TRADES</span>
      <span class="value black">{{ traderDetailInfo.maxOpenTrades ? traderBasicInf.maxOpenTrades.toFixed(3) : 0 }}</span>
    </div>
    <div class="data-item avgProfit">
      <span class="label">AVG PROFIT</span>
      <span class="value black">{{ traderDetailInfo.avgProfit ? traderBasicInf.avgProfit.toFixed(3) : 0 }}</span>
    </div>
    <div class="data-item winTrades">
      <span class="label">WIN TRADES</span>
      <span class="value black">{{ traderDetailInfo.winTrades ? traderBasicInf.winTrades : 0 }}</span>
    </div>
    <div class="data-item recommendedMinInvestment">
      <span class="label">RECOMMENDED MIN INVESTMENT</span>
      <span class="value black">{{ traderDetailInfo.recommendedMinInvestment ? traderBasicInf.recommendedMinInvestment : 0 }}</span>
    </div>
    <div class="data-item maxDrawDown">
      <span class="label">MAX DRAWDOWN</span>
      <span class="value black">{{ traderDetailInfo.maxDrawDown ? traderBasicInf.maxDrawDown : 0 }}</span>
    </div>
    <div class="data-item avgPips">
      <span class="label">AVG PIPS</span>
      <span class="value black">{{ traderDetailInfo.avgPips ? traderBasicInf.avgPips : 0 }}</span>
    </div>
  </div>
</div>
</template>

<style lang="scss" scoped>
.data {
  margin-top: 20px;

  .title {
    font-size: 1.2rem;
    font-weight: bold;
    margin-bottom: 10px;
  }

  .data-grid {
    display: grid;
    grid-template-columns: repeat(4, 1fr); // 每行 4 列
    gap: 20px; // 单元格之间的间距

    .data-item {
      display: flex;
      flex-direction: column;
      align-items: center;
      text-align: center;
      padding: 10px;
      border: 1px solid #ddd;
      border-radius: 8px;

      .label {
        font-size: 0.9rem;
        color: #666;
        margin-bottom: 5px;
      }

      .value {
        font-size: 1.1rem;
        font-weight: bold;

        &.green {
          color: #4caf50;
        }

        &.black {
          color: #333;
        }
      }
    }
  }
}
</style>