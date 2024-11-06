<script setup>
import CopierItem from "./CopierItem";
import getTraderCopiers from "@/apis/follower";
import { onMounted, ref, watch } from "vue";
import { defineEmits } from "vue";
import { useRoute } from "vue-router";

const emit = defineEmits(["sendItemToParent"]);

const route = useRoute();
const traderId = route.params.id;
const copiers = ref([]);
const currentPage = ref(1); // 当前页
const totalPages = ref(0); // 总页数
const totalItems = ref(0); // 总条数
const hasPrevPage = ref(false); // 是否有上一页
const hasNextPage = ref(false); // 是否有下一页

const fetchTraderCopiers = async () => {
  try {
    const response = await getTraderCopiers({
      id: traderId,
      currentPage: currentPage.value,
    });
    copiers.value = totalPages.value = response.data.totalPages;
    totalItems.value = response.data.totalElements;
    hasPrevPage.value = response.data.isPrePage;
    hasNextPage.value = response.data.isNextPage;
  } catch (error) {
    //处理错误
  }
};

// 监听来自子组件的事件，并向父父组件发送事件
function handleItem(item) {
  emit("sendItemToParent", item);
}

// 监听分页变化
watch(currentPage, fetchTraderCopiers);

onMounted(fetchTraderCopiers);
</script>

<template>
    <div v-if="traderTradesHistory.length > 0">
      <copier-item @sendItem="handleItem"
        v-for="item in copier"
        :key="item.followerId"
        :item="item"
      ></copier-item>
    </div>
    <!-- 如果没有数据，则显示 "Not Found" -->
    <div v-else class="not-found">Not Found</div>

    <div class="pagination-container">
      <!-- 分页选项 -->
      <el-pagination
        size="small"
        background
        layout="prev, pager, next"
        :total="totalItems"
        v-model:current-page="currentPage"
        :disabled-prev="!hasPrevPage"
        :disabled-next="!hasNextPage"
        @current-change="currentPage = $event"
        class="mt-4"
      />
    </div>
</template>