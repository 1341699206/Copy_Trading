<script setup>
import { ref, onMounted } from "vue";
import { getTraderDetailInf } from "@/apis/follower";
import timeSelector from "@/views/Follower/TraderHomePage/component/Body/component/timeSelector.vue";
import * as echarts from "echarts";

const traderHistoryInfo = ref([]);
const traderDetailInfo = ref(null);
const chartRef = ref(null);
let chart;
const chartHeight = ref(400); // 设置图表的默认高度

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
  // 检查 traderBasicInf 是否存在
  if (!props.traderBasicInf) return;

  const res = await getTraderDetailInf({
    id: props.traderBasicInf.traderId,
    timePeriod: selectedDays.value,
  });
  traderDetailInfo.value = res.data;
  traderHistoryInfo.value = traderDetailInfo.value.traderHistoryData;
  console.log(traderHistoryInfo.value)
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
  <div>
    <div class="over">
      <ul class="info-list">
        <li>
          <div class="roi">
            <span class="value green">
              {{ traderBasicInf?.ROI ? traderBasicInf.ROI.toFixed(3) : "N/A" }}
            </span>
            <span class="label">ROI</span>
          </div>
        </li>
        <li>
          <div class="winRate">
            <span class="value black">
              {{
                traderBasicInf?.winRate
                  ? traderBasicInf.winRate.toFixed(3)
                  : "N/A"
              }}%
            </span>
            <span class="label">WIN RATE</span>
          </div>
        </li>
        <li>
          <div class="copiers">
            <span class="value black">{{ traderBasicInf?.copiers ?? 0 }}</span>
            <span class="label">COPIERS</span>
          </div>
        </li>
      </ul>
    </div>
    <div class="chart" ref="chartRef" :style="{ height: chartHeight + 'px' }"></div>
    <div class="timeSelector">
      <time-selector @update:days="updateDays"></time-selector>
    </div>
    <div class="data">
      <div class="title">STATISTICS - OVERALL</div>
      <div class="data-grid">
        <div class="data-item profit">
          <span class="label">PROFIT</span>
          <span class="value green">{{
            traderDetailInfo?.profit ? traderDetailInfo.profit.toFixed(3) : 0
          }}</span>
        </div>
        <div class="data-item trades">
          <span class="label">TRADES</span>
          <span class="value black">{{ traderDetailInfo?.trades ?? 0 }}</span>
        </div>
        <div class="data-item maxOpenTrades">
          <span class="label">MAX OPEN TRADES</span>
          <span class="value black">{{
            traderDetailInfo?.maxOpenTrades ?? 0
          }}</span>
        </div>
        <div class="data-item avgProfit">
          <span class="label">AVG PROFIT</span>
          <span class="value black">{{
            traderDetailInfo?.avgProfit
              ? traderDetailInfo.avgProfit.toFixed(3)
              : 0
          }}</span>
        </div>
        <div class="data-item winTrades">
          <span class="label">WIN TRADES</span>
          <span class="value black">{{
            traderDetailInfo?.winTrades ?? 0
          }}</span>
        </div>
        <div class="data-item recommendedMinInvestment">
          <span class="label">RECOMMENDED MIN INVESTMENT</span>
          <span class="value black">{{
            traderDetailInfo?.recommendedMinInvestment ?? 0
          }}</span>
        </div>
        <div class="data-item maxDrawDown">
          <span class="label">MAX DRAWDOWN</span>
          <span class="value black">{{
            traderDetailInfo?.maxDrawDown ?? 0
          }}</span>
        </div>
        <div class="data-item avgPips">
          <span class="label">AVG PIPS</span>
          <span class="value black">{{ traderDetailInfo?.avgPips ?? 0 }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.over {
  display: flex;
  justify-content: flex-start; /* 内容左对齐 */
  padding: 10px;
}

.info-list {
  display: flex;
  list-style: none;
  padding: 0;
  margin: 0;
}

li {
  display: flex;
  flex-direction: column;
  align-items: flex-start; /* 让每项左对齐 */
  margin: 0 20px;
}

.value {
  font-size: 20px;
  font-weight: bold;
}

.label {
  font-size: 12px;
  color: gray;
}

.green {
  color: green;
}

.black {
  color: black;
}
.chart {
  width: 100%;  /* 根据需要调整宽度 */
  min-height: 200px;  /* 保证至少有 200px 的高度 */
  background-color: #f5f5f5;  /* 可选：当没有数据时，给图表背景添加颜色，便于区分 */
}

.data {
  margin-top: 20px;

  .title {
    font-size: 1.2rem;
    font-weight: bold;
    margin-bottom: 10px;
    text-align: left; /* Title 左对齐 */
  }

  .data-grid {
    display: grid;
    grid-template-columns: repeat(4, 1fr); // 每行 4 列
    gap: 20px; // 单元格之间的间距

    .data-item {
      display: flex;
      flex-direction: column;
      align-items: flex-start; /* 左对齐 */
      text-align: left; /* 让内容左对齐 */
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