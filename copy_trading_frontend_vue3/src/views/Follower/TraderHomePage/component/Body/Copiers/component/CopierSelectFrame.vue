<script setup>
import CopierItem from "./CopierItem";
import { getTraderCopiers } from "@/apis/followerManagement"; // 确保使用正确的导入方式
import { onMounted, ref, watch } from "vue";
import { defineEmits } from "vue";
import { useRoute } from "vue-router";

const emit = defineEmits(["sendItemToParent"]);

const route = useRoute();
const traderId = route.params.id;
const copiers = ref([]); // 存储跟随者数据
const currentPage = ref(1); // 当前页
const pageSize = ref(10); // 每页数量，设置默认值为 10
const totalPages = ref(0); // 总页数
const totalItems = ref(0); // 总条数
const hasPrevPage = ref(false); // 是否有上一页
const hasNextPage = ref(false); // 是否有下一页

console.log("正确的启动");
console.log("Trader ID:", traderId);

// 获取交易员的跟随者数据
const fetchTraderCopiers = async () => {
  console.log("开始获取跟随者数据");
  try {
    const response = await getTraderCopiers({
      id: traderId,
      currentPage: currentPage.value,
      pageSize: pageSize.value, // 添加 pageSize 参数
    });
    console.log("API 响应:", response);

    if (response && response.data) {
      // 正确地赋值获取到的数据
      copiers.value = response.data.copiers || [];
      totalPages.value = response.data.totalPages || 0;
      totalItems.value = response.data.totalElements || 0;
      hasPrevPage.value = response.data.isPrePage || false;
      hasNextPage.value = response.data.isNextPage || false;
    } else {
      copiers.value = [];
    }
  } catch (error) {
    // 处理错误
    console.error("Failed to fetch copiers:", error);
    copiers.value = []; // 在错误情况下将 copiers 设为空数组
  }
};

// 监听来自子组件的事件，并向父组件发送事件
function handleItem(item) {
  emit("sendItemToParent", item);
}

// 监听分页变化
watch([currentPage, pageSize], fetchTraderCopiers);

onMounted(fetchTraderCopiers);
</script>

<template>
  <div>
    <!-- 数据条目列表 -->
    <div v-if="copiers.length > 0">
      <copier-item 
        v-for="item in copiers" 
        :key="item.followerId" 
        :item="item" 
        @sendItem="handleItem"
      ></copier-item>
    </div>
    <!-- 如果没有数据，则显示 "Not Found" -->
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
