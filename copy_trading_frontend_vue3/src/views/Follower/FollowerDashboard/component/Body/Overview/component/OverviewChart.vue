<script setup>
import { ref, watch, onMounted, computed } from "vue";
import * as echarts from "echarts";
import { useAccountStore } from "@/stores/account";
import { useRoleStore } from '@/stores/roleBasicData'

const accountStore=useAccountStore();

const roleStore=useRoleStore();
const roleBasicData=computed(()=>roleStore.roleInfo);

const chartRef = ref(null); // 图表容器引用
const chartInstance = ref(null); // ECharts 实例
const chartHeight = ref(400); // 图表高度

// trades 数据
const trades = ref([]);

// 页面挂载时获取数据
onMounted(async () => {
    trades.value = accountStore.accountInfo.trades || [];
    updateChart();
});

// 数据处理函数：生成累进盈亏和日期数组
const processData = () => {
  if (!trades.value.length) return { dates: [], cumulativeProfit: [] };

  // 按开仓时间排序
  const sortedTrades = trades.value.sort((a, b) => new Date(a.dateOpen) - new Date(b.dateOpen));
  const dates = [];
  const cumulativeProfit = [];
  let totalProfit = 0;

  // 累计盈亏计算
  for (const trade of sortedTrades) {
    dates.push(trade.dateOpen);
    totalProfit += trade.profit || 0;
    cumulativeProfit.push(totalProfit);
  }

  return { dates, cumulativeProfit };
};

// 更新图表
const updateChart = () => {
  const { dates, cumulativeProfit } = processData();

  if (!chartInstance.value) {
    chartInstance.value = echarts.init(chartRef.value);
  }

  const option = {
    title: {
      text: "Cumulative Profit Over Time",
      left: "center",
    },
    tooltip: {
      trigger: "axis",
    },
    xAxis: {
      type: "category",
      data: dates,
      name: "Date Open",
      axisLabel: {
        rotate: 45, // 旋转轴标签
      },
    },
    yAxis: {
      type: "value",
      name: "Cumulative Profit",
    },
    series: [
      {
        name: "Cumulative Profit",
        type: "line",
        data: cumulativeProfit,
        smooth: true, // 平滑曲线
        lineStyle: {
          width: 2,
        },
        areaStyle: {
          color: "rgba(120, 180, 255, 0.2)", // 区域填充颜色
        },
      },
    ],
  };

  chartInstance.value.setOption(option);
};

// 监听 trades 数据变化以更新图表
watch(trades, updateChart);

</script>

<template>
  <div>
    <div class="over">
      <ul class="info-list">
        <li>
          <div class="roi">
            <span class="value green">
              {{ roleBasicData?.ROI ? roleBasicData.ROI.toFixed(3) : "N/A" }}
            </span>
            <span class="label">ROI</span>
          </div>
        </li>
        <li>
          <div class="winRate">
            <span class="value black">
              {{
                roleBasicData?.winRate
                  ? roleBasicData.winRate.toFixed(3)
                  : "N/A"
              }}%
            </span>
            <span class="label">WIN RATE</span>
          </div>
        </li>
      </ul>
    </div>
    <div
      class="chart"
      ref="chartRef"
      :style="{ height: chartHeight + 'px' }"
    ></div>
    <div class="timeSelector">
      <time-selector @update:days="updateDays"></time-selector>
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
  width: 100%; /* 根据需要调整宽度 */
  min-height: 200px; /* 保证至少有 200px 的高度 */
  background-color: #f5f5f5; /* 可选：当没有数据时，给图表背景添加颜色，便于区分 */
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