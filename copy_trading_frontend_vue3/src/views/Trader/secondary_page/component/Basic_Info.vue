<template>
  <div class="basic-info-container">
    <!-- Trader头像和账户信息 -->
    <div class="trader-info">
      <img
        v-if="userLoggedIn"
        :src="userAvatar"
        alt="Trader Avatar"
        class="avatar"
      />
      <h2 v-if="userLoggedIn">{{ username }}</h2>
    </div>

    <!-- Trader的资金和状态信息，仅在登录状态下显示 -->
    <div class="trader-stats" v-if="userLoggedIn">
      <!-- 动态展示账户信息 -->
      <div class="stat-item" v-for="stat in stats" :key="stat.label">
        <p>{{ stat.label }}</p>
        <h3>{{ stat.value }}</h3>
      </div>
      <button class="funds-button">Funds</button>
    </div>

    <!-- 账户创建对话框 -->
    <AccountDialog :show="showDialog" @close="closeDialog" />
  </div>
</template>

<script setup>
import { ref, onMounted, computed, watch } from "vue";
import AccountDialog from "./AccountDialog.vue"; // 使用同级目录路径导入组件
import { useUserStore } from "@/stores/user";
import { useAccountStore } from "@/stores/account";
import { generateAvatar } from "@/utils/avatar";

const userStore = useUserStore();
const accountStore = useAccountStore();
const accountInfo = accountStore.accountInfo; // 引用 accountStore 的响应式数据

// 计算属性
const userLoggedIn = computed(() => !!userStore.userInfo);
const userAvatar = computed(() =>
  generateAvatar(userStore.userInfo.username || "User")
);
const username = computed(() => userStore.userInfo.username || "N/A");

// 弹窗控制
const showDialog = ref(false);

// 定义 stats 数据，直接使用响应式对象
const stats = ref([
  { label: "Amount Following", value: 0 },
  { label: "Followers", value: 0 },
  { label: "Equity", value: 0 },
  { label: "Balance", value: 0 },
  { label: "Realized PNL", value: 0 },
  { label: "Margin", value: 0 },
  { label: "Free Margin", value: 0 },
]);

// 创建账户或检测账户
const createFirstAccount = async () => {
  try {
    await accountStore.getAccountInfo(userStore.userInfo.id); // 拉取账户信息
  } catch (error) {
    showDialog.value = true; // 如果没有账户，显示弹窗
  }
};

// 监听 accountInfo 的变化，同步更新 stats
watch(
  accountInfo,
  (newAccountInfo) => {
    stats.value = [
      { label: "Amount Following", value: newAccountInfo.amountFollowing || 0 },
      { label: "Followers", value: newAccountInfo.followers || 0 },
      { label: "Equity", value: newAccountInfo.equity || 0 },
      { label: "Balance", value: newAccountInfo.balance || 0 },
      { label: "Realized PNL", value: newAccountInfo.winRate || 0 },
      { label: "Margin", value: newAccountInfo.margin || 0 },
      { label: "Free Margin", value: newAccountInfo.freeMargin || 0 },
    ];
  },
  { immediate: true, deep: true } // 立即执行一次并深度监听
);

// 生命周期钩子
onMounted(() => {
  createFirstAccount(); // 页面加载时检查账户
});
</script>


<style scoped>
/* 样式保持不变 */
.basic-info-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
}

.trader-info {
  display: flex;
  align-items: center;
}

.avatar {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  margin-right: 20px;
}

.trader-details h2 {
  margin: 0;
  font-size: 24px;
}

.trader-details p {
  margin: 5px 0;
  font-size: 18px;
}

.trader-type {
  margin-top: 5px;
}

.trader-stats {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  align-items: center;
}

.stat-item {
  text-align: center;
}

.stat-item h3 {
  font-size: 18px;
  margin: 0;
}

.funds-button {
  background-color: #ff9f00;
  color: white;
  border: none;
  padding: 10px 15px;
  cursor: pointer;
  border-radius: 5px;
}

.funds-button:hover {
  background-color: #ff8500;
}

.login-button {
  background-color: #3498db;
  color: white;
  border: none;
  padding: 10px 15px;
  cursor: pointer;
  border-radius: 5px;
}

.login-button:hover {
  background-color: #2980b9;
}

.create-account-button {
  background-color: #28a745;
  color: white;
  border: none;
  padding: 10px 15px;
  cursor: pointer;
  border-radius: 5px;
  margin-top: 10px;
}

.create-account-button:hover {
  background-color: #218838;
}
</style>
