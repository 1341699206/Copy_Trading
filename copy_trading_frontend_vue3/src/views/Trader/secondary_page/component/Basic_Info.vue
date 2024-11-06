<template>
  <div class="basic-info-container">
    <!-- Trader头像和账户信息 -->
    <div class="trader-info">
      <img v-if="userLoggedIn" :src="userAvatar" alt="Trader Avatar" class="avatar" />
      <h2 v-if="userLoggedIn">{{ name }}</h2>
      <select v-if="userLoggedIn" class="trader-type" @change="handleAccountChange">
        <option v-for="account in traderAccounts" :key="account.id" :value="account.id">{{ account.name }}</option>
      </select>

      <!-- 如果用户未登录，显示登录按钮 -->
      <button v-if="!userLoggedIn" class="login-button" @click="redirectToLogin">Login</button>
    </div>

    <!-- Trader的资金和状态信息，仅在登录状态下显示 -->
    <div class="trader-stats" v-if="userLoggedIn">
      <!-- 动态展示账户信息 -->
      <div class="stat-item" v-for="stat in stats" :key="stat.label">
        <p>{{ stat.label }}</p>
        <h3>{{ stat.value }}</h3>
      </div>
      <button class="funds-button">Funds</button>

      <!-- 新增的“创建交易账户”按钮 -->
      <button class="create-account-button" @click="openDialog">创建交易账户</button>
    </div>

    <!-- 账户创建对话框 -->
    <AccountDialog :show="showDialog" @close="closeDialog" />
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import AccountDialog from "./AccountDialog.vue"; // 使用同级目录路径导入组件
import { useUserStore } from '@/stores/user';

const userStore = useUserStore();
const router = useRouter();

const userLoggedIn = computed(() => !!userStore.userInfo.user);
const userAvatar = computed(() => generateAvatar(userStore.userInfo.user?.name || 'User'));
const name = computed(() => userStore.userInfo.user?.name || 'N/A');
const traderAccounts = computed(() => userStore.userInfo.user?.tradingAccounts || []);
const showDialog = ref(false); // 控制弹窗显示

const stats = ref([
  { label: 'Amount Following', value: '-' },
  { label: 'Investors', value: '0' },
  { label: 'Rank (All)', value: '-' },
  { label: 'Rank (Europe)', value: '-' },
  { label: 'Rank (USA)', value: '-' },
  { label: 'Rank (Japan)', value: '-' },
  { label: 'Equity', value: '$0.00' },
  { label: 'Balance', value: '$0.00' },
  { label: 'Realized PNL', value: '$0.00' },
  { label: 'Margin', value: '0.00% (0%)' },
  { label: 'Free Margin', value: '$0.00' },
]);

// 获取用户基础信息并检查账户
const fetchUserInfo = async () => {
  try {
    await userStore.getUserInfo(); // 从userStore获取用户信息
    console.log("Fetched user info:", userStore.userInfo);

    // 检查是否需要弹出账户创建弹窗
    if (traderAccounts.value.length === 0) {
      showDialog.value = true; // 显示弹窗
    } else {
      // 如果有账户，更新第一个账户的信息
      updateStats(traderAccounts.value[0]);
    }
  } catch (error) {
    console.error("Failed to fetch user info:", error);
  }
};

// 打开创建账户对话框
const openDialog = () => {
  showDialog.value = true;
};

// 关闭对话框
const closeDialog = () => {
  showDialog.value = false;
};

// 头像生成逻辑
const generateAvatar = (userName) => {
  const firstChar = userName.charAt(0).toUpperCase();
  return `data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="80" height="80"><rect width="100%" height="100%" fill="white"/><text x="50%" y="50%" dominant-baseline="middle" text-anchor="middle" font-size="36" fill="black">${firstChar}</text></svg>`;
};

// 账户选择变化处理
const handleAccountChange = (event) => {
  const accountId = event.target.value;
  const account = traderAccounts.value.find(acc => acc.id === accountId);
  if (account) updateStats(account);
};

// 更新统计数据
const updateStats = (account) => {
  stats.value = [
    { label: 'Amount Following', value: account.amountFollowing || '-' },
    { label: 'Investors', value: account.investors || '0' },
    { label: 'Rank (All)', value: account.rankAll || '-' },
    { label: 'Rank (Europe)', value: account.rankEurope || '-' },
    { label: 'Rank (USA)', value: account.rankUSA || '-' },
    { label: 'Rank (Japan)', value: account.rankJapan || '-' },
    { label: 'Equity', value: `$${account.equity?.toFixed(2) || '0.00'}` },
    { label: 'Balance', value: `$${account.balance?.toFixed(2) || '0.00'}` },
    { label: 'Realized PNL', value: `$${account.realizedPNL?.toFixed(2) || '0.00'}` },
    { label: 'Margin', value: `${account.marginPercent?.toFixed(2) || '0.00'}% (${account.margin?.toFixed(2) || '0'})` },
    { label: 'Free Margin', value: `$${account.freeMargin?.toFixed(2) || '0.00'}` },
  ];
};

// 重定向到登录页面
const redirectToLogin = () => {
  router.push('/login');
};

// 生命周期钩子
onMounted(() => {
  fetchUserInfo(); // 页面加载时获取用户信息并检查是否需要显示创建账户弹窗
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
