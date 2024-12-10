<script setup>
import { computed } from "vue";
import { useUserStore } from "@/stores/user";
import { useRoleStore } from "@/stores/roleBasicData";
import { useAccountStore } from "@/stores/account";
import { generateAvatar } from "@/utils/avatar";

// 加载 stores
const userStore = useUserStore();
const roleStore = useRoleStore();
const accountStore = useAccountStore();

// 计算属性
const user = computed(() => userStore.userInfo);
const role = computed(() => roleStore.roleInfo);
const account = computed(() => accountStore.accountInfo);

// 定义 followerInfo 为动态计算属性
const followerInfo = computed(() => ({
  id: user.value.id || null,
  username: user.value.username || "User",
  value: account.value.balance || 0,
  profitLoss: role.value.totalProfit || 0,
  copying: role.value.totalTrades || 0,
  following: role.value.totalFollowedTraders || 0,
}));

const userAvatar = computed(() => generateAvatar(followerInfo.value.username));
</script>

<template>
  <div class="container">
    <div class="left-section">
      <div class="user_information">
        <img class="avatar" :src="userAvatar" alt="avatar" />
        <div class="name">{{ followerInfo.username }}</div>
      </div>
      <div class="trade_information">
        <ul>
          <li>
            Total portfolio value: <span class="highlight">{{ followerInfo.value }}</span>
          </li>
          <li>
            Total PnL: <span class="highlight">{{ followerInfo.profitLoss }}</span>
          </li>
        </ul>
      </div>
    </div>

    <div class="right-section">
      <div class="trades">
        <ul>
          <li>
            Copying: <span class="highlight">{{ followerInfo.copying }}</span>
          </li>
          <li>
            Following: <span class="highlight">{{ followerInfo.following }}</span>
          </li>
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
