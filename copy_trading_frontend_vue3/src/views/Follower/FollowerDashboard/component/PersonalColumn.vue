<script setup>
import { reactive, watch } from "vue";
import { useUserStore } from "@/stores/user";

const userStore = useUserStore();

// 定义 followerInfo，同时监听 userInfo 的变化
const followerInfo = reactive({
  id: userStore.userInfo?.user?.followerId || null,
  name: userStore.userInfo?.user?.name || "N/A",
  value: userStore.userInfo?.user?.totalInvestment || 0,
  profitLoss: userStore.userInfo?.user?.profitLoss || 0,
  copying: 0,
  following: userStore.userInfo?.user?.followingTraders ? Object.keys(userStore.userInfo.user.followingTraders).length : 0,
});

// 如果 userInfo 发生变化，动态更新 followerInfo 的值
watch(
  () => userStore.userInfo,
  (newUserInfo) => {
    followerInfo.id = newUserInfo?.user?.followerId || null;
    followerInfo.name = newUserInfo?.user?.name || "N/A";
    followerInfo.value = newUserInfo?.user?.totalInvestment || 0;
    followerInfo.profitLoss = newUserInfo?.user?.profitLoss || 0;
    followerInfo.following = newUserInfo?.user?.followingTraders ? Object.keys(newUserInfo.user.followingTraders).length : 0;
  },
  { immediate: true }
);
</script>

<template>
  <div class="container">
    <div class="left-section">
      <div class="user_information">
        <div class="avatar"></div>
        <div class="name">{{ followerInfo.name }}</div>
      </div>
      <div class="trade_information">
        <ul>
          <li>Total portfolio value: <span class="highlight">{{ followerInfo.value }}</span></li>
          <li>Total PnL: <span class="highlight">{{ followerInfo.profitLoss }}</span></li>
        </ul>
      </div>
    </div>

    <div class="right-section">
      <div class="trades">
        <ul>
          <li>Copying: <span class="highlight">{{ followerInfo.copying }}</span></li>
          <li>Following: <span class="highlight">{{ followerInfo.following }}</span></li>
        </ul>
      </div>
    </div>
  </div>
  <hr class="separator" />
</template>

<style scoped>
/* 样式保持不变 */
.container {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  padding: 10px;
  padding-bottom: 20px;
}

.left-section {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
}

.right-section {
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: flex-end;
  flex: 1;
}

.user_information {
  display: flex;
  align-items: center;
}

.avatar {
  width: 50px;
  height: 50px;
  background-color: #ccc;
  border-radius: 50%;
  margin-right: 10px;
}

.trade_information {
  margin-top: 2px;
  margin-bottom: 0;
  padding-bottom: 0;
}

.trade_information ul {
  display: flex;
  list-style-type: none;
  padding: 0;
}

.trade_information li {
  margin-right: 20px;
}

.highlight {
  color: orange;
}

.trades ul {
  display: flex;
  list-style-type: none;
  padding: 0;
}

.trades li {
  margin-left: 20px;
}

.separator {
  margin-top: -25px;
  border: 1px solid #ccc;
  width: 100%;
  margin-left: auto;
  margin-right: auto;
}
</style>
