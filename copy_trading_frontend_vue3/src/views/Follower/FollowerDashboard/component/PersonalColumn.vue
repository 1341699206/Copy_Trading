<script setup>
import { reactive } from "vue";
import { useUserStore } from "@/stores/user";

const userStore = useUserStore();

const followerInfo = reactive({
  id: userStore.userInfo.user.followerId,
  name: userStore.userInfo.user.name,
  value: userStore.userInfo.user.totalInvestment,
  profitLoss: userStore.userInfo.user.profitLoss,
  copying: 0,
  following: Object.keys(userStore.userInfo.user.followingTraders).length,
});
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
.container {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  padding: 10px; /* Reduce padding to decrease container height */
  padding-bottom: 20px; /* Add bottom padding to create space for the separator */
}

.left-section {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: flex-start; /* Align content at the top */
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
  margin-top: 2px; /* Reduce top margin to control spacing */
  margin-bottom: 0; /* Ensure no margin below the content */
  padding-bottom: 0; /* Remove padding that might cause extra space */
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
  color: orange; /* Set text color to orange */
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
  margin-top: -25px; /* Add space above the separator */
  border: 1px solid #ccc;
  width: 100%; /* Set width to 100% */
  margin-left: auto; /* Align separator to center */
  margin-right: auto; /* Align separator to center */
}
</style>