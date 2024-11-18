<script setup>
import { getTraderTradesHistory } from "@/apis/follower";
import TradeHistoryItem from "./TradeHistoryItem.vue";
import { onMounted, ref, watch } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
const traderId = route.params.id;
const traderTradesHistory = ref([]);
const currentPage = ref(1); // 当前页
const pageSize = ref(10); // 每页数量
const totalPages = ref(0); // 总页数
const totalItems = ref(0); // 总条数
const hasPrevPage = ref(false); // 是否有上一页
const hasNextPage = ref(false); // 是否有下一页

// 获取交易历史数据
const fetchTraderTradesHistory = async () => {
  try {
    const response = await getTraderTradesHistory({
      id: traderId,
      pageSize: pageSize.value,
      currentPage: currentPage.value,
    });

    // 确保数据为数组的类型
    traderTradesHistory.value = Array.isArray(response.data.tradeHistory) ? response.data.tradeHistory : [];
    totalPages.value = response.data.totalPages || 0;
    totalItems.value = response.data.totalElements || 0;
    hasPrevPage.value = response.data.isPrePage || currentPage.value > 1;
    hasNextPage.value = response.data.isNextPage || currentPage.value < totalPages.value;
  } catch (error) {
    // 处理错误
    console.error("Failed to fetch trade history:", error);
  }
};

// 监听分页变化
watch([currentPage, pageSize], fetchTraderTradesHistory);

onMounted(fetchTraderTradesHistory);
</script>

<template>
  <div>
    <!-- 标签栏 -->
    <div class="table-header">
      <div class="header-item">Name</div>
      <div class="header-item">Date Closed</div>
      <div class="header-item">STD LOTS</div>
      <div class="header-item">OPEN / CLOSE</div>
      <div class="header-item">HIGH</div>
      <div class="header-item">LOW</div>
      <div class="header-item">ROLL</div>
      <div class="header-item">PROFIT</div>
      <div class="header-item">TOTAL</div>
    </div>

    <!-- 数据条目列表 -->
    <div v-if="traderTradesHistory && traderTradesHistory.length > 0">
      <trade-history-item
        v-for="item in traderTradesHistory"
        :key="item.tradeId"
        :item="item"
      ></trade-history-item>
    </div>
    <div v-else class="not-found">Not Found</div>

    <!-- 分页容器 -->
    <div class="pagination-container">
      <!-- 每页数量控制 -->
      <el-select
        v-model="pageSize"
        size="small"
        class="page-size-select"
        @change="currentPage = 1"
        style="max-width: 5%;"
      >
        <el-option label="10" value="10"></el-option>
        <el-option label="20" value="20"></el-option>
        <el-option label="30" value="30"></el-option>
      </el-select>

      <!-- 分页选项 -->
      <el-pagination
        size="small"
        background
        layout="prev, pager, next"
        :total="totalItems"
        v-model:current-page="currentPage"
        :page-size="pageSize"
        :disabled-prev="!hasPrevPage"
        :disabled-next="!hasNextPage"
        @current-change="currentPage = $event"
        class="mt-4"
      />
    </div>
  </div>
</template>

<style scoped lang="scss">
.table-header {
  display: grid;
  grid-template-columns: 2fr 2fr 1fr 2fr 1fr 1fr 1fr 2fr 2fr;
  gap: 1rem;
  padding: 1rem 0;
  border-bottom: 2px solid #f0f0f0;
  font-weight: bold;
  color: #666;
  background-color: #f9f9f9;
}

.header-item {
  text-align: center;
  font-size: 0.8rem;
  color: #333;
}

.pagination-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20px;
}

.page-size-select {
  margin-left: 10px;
}
</style>
