<script setup>
import { reactive } from "vue";

import { useUserStore } from "@/stores/user";
const userStore = useUserStore();

const followerInfo = reactive({
  id: userStore.userInfo.user.followerId,
  name: userStore.userInfo.user.name,
  value: 0,
  profitLoss: userStore.userInfo.user.profitLoss,
  copying: 0,
  following: Object.keys(userStore.userInfo.user.followingTraders),
});
</script>

<template>
  <div class="user_information">
    <div class="avatar">{{}}</div>
    <div class="name">
      {{ followerInfo.name }}
    </div>
  </div>
  <div class="trade_information">
    <div class="profit">
      <ul>
        <li>Total portfolio value: {{ followerInfo.value }}</li>
        <li>Total PnL: {{ followerInfo.profitLoss }}</li>
      </ul>
    </div>
    <div class="trades">
      <ul>
        <li>Copying: {{ followerInfo.copying }}</li>
        <li>Following: {{ followerInfo.following }}</li>
      </ul>
    </div>
  </div>
</template>