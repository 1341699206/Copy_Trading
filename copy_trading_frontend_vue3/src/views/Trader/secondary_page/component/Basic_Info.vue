<template>
  <div class="basic-info-container">
    <!-- Trader头像和账户信息 -->
    <div class="trader-info">
      <img v-if="userLoggedIn" :src="userAvatar" alt="Trader Avatar" class="avatar"/>
      <h2 v-if="userLoggedIn">{{ firstName }} {{ lastName }}</h2>
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
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';

const userLoggedIn = ref(false);
const userAvatar = ref('');
const firstName = ref('');
const lastName = ref('');
const traderAccounts = ref([]);
const selectedAccount = ref(null);
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

const router = useRouter();

// 模拟从后端获取用户信息
onMounted(() => {
  // 假设从后端获取登录状态及用户信息
  const userInfo = getUserInfoFromBackend(); // 模拟API调用
  if (userInfo) {
    userLoggedIn.value = true;
    firstName.value = userInfo.firstName;
    lastName.value = userInfo.lastName;
    userAvatar.value = userInfo.avatar;
    traderAccounts.value = userInfo.accounts;
    selectedAccount.value = userInfo.accounts[0]; // 默认选择第一个账户
    updateStats(selectedAccount.value); // 更新账户的统计数据
  }
});

const handleAccountChange = (event) => {
  const accountId = event.target.value;
  const account = traderAccounts.value.find(acc => acc.id === accountId);
  updateStats(account); // 根据选择的账户更新展示数据
};

const updateStats = (account) => {
  stats.value = [
    { label: 'Amount Following', value: account.amountFollowing || '-' },
    { label: 'Investors', value: account.investors || '0' },
    { label: 'Rank (All)', value: account.rankAll || '-' },
    { label: 'Rank (Europe)', value: account.rankEurope || '-' },
    { label: 'Rank (USA)', value: account.rankUSA || '-' },
    { label: 'Rank (Japan)', value: account.rankJapan || '-' },
    { label: 'Equity', value: `$${account.equity.toFixed(2)}` || '$0.00' },
    { label: 'Balance', value: `$${account.balance.toFixed(2)}` || '$0.00' },
    { label: 'Realized PNL', value: `$${account.realizedPNL.toFixed(2)}` || '$0.00' },
    { label: 'Margin', value: `${account.marginPercent.toFixed(2)}% (${account.margin.toFixed(2)})` || '0.00% (0%)' },
    { label: 'Free Margin', value: `$${account.freeMargin.toFixed(2)}` || '$0.00' },
  ];
};

// 重定向到登录页面
const redirectToLogin = () => {
  router.push('/login');
};

// 模拟从后端获取用户信息的函数
const getUserInfoFromBackend = () => {
  // 这里可以使用 Axios 或 Fetch 发起请求
  // 示例：返回模拟的数据
  return {
    firstName: 'JQ',
    lastName: 'Ethan',
    avatar: 'path_to_avatar',
    accounts: [
      { id: 1, name: 'Demo Account 1', amountFollowing: '-', investors: 0, rankAll: '-', rankEurope: '-', rankUSA: '-', rankJapan: '-', equity: 5000, balance: 5000, realizedPNL: 0, marginPercent: 0, margin: 0, freeMargin: 5000 },
      { id: 2, name: 'Real Account', amountFollowing: 10000, investors: 5, rankAll: 1, rankEurope: 1, rankUSA: 2, rankJapan: 3, equity: 100000, balance: 90000, realizedPNL: 10000, marginPercent: 2, margin: 2000, freeMargin: 98000 }
    ]
  };
};
</script>

<style scoped>
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
</style>
