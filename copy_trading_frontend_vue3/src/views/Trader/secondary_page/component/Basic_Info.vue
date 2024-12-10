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
  </div>
</template>

<script setup>
import { computed } from "vue";
import { useUserStore } from "@/stores/user";
import { useRoleStore } from "@/stores/roleBasicData";
import { useAccountStore } from "@/stores/account";
import { generateAvatar } from "@/utils/avatar";

const userStore = useUserStore();

// 加载 accountStore 和 roleStore
const accountStore = useAccountStore();
const roleStore = useRoleStore();

// 获取响应式数据
const accountInfo = computed(() => accountStore.accountInfo);
const roleInfo = computed(() => roleStore.roleInfo);

// 计算属性
const userLoggedIn = computed(() => !!userStore.userInfo);
const userAvatar = computed(() =>
  generateAvatar(userStore.userInfo.username || "User")
);
const username = computed(() => userStore.userInfo.username || "N/A");

// 动态生成 stats 数据
const stats = computed(() => [
  { label: "Amount Following", value: accountInfo.value.amountFollowing || 0 },
  { label: "Followers", value: roleInfo.value.totalFollowers || 0 },
  { label: "Equity", value: accountInfo.value.equity || 0 },
  { label: "Balance", value: accountInfo.value.balance || 0 },
  { label: "Realized PNL", value: accountInfo.value.winRate || 0 },
  { label: "Margin", value: accountInfo.value.margin || 0 },
  { label: "Free Margin", value: accountInfo.value.freeMargin || 0 },
]);
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
