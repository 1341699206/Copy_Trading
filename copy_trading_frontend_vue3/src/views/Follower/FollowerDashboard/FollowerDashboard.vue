<script setup>
import { ref, onMounted } from "vue";
import DashboardHeader from "./component/Body/component/DashboardHeader.vue";
import PersonalColumn from "./component/PersonalColumn.vue";
import AccountDialog from "./component/AccountDialog.vue";

import { useUserStore } from "@/stores/user";
const userStore = useUserStore();

const showDialog = ref(true);
const closeDialog=()=>{
    showDialog.value=false;
}

//强制创建第一个账户
const createFirstAccount = () => {
  //检测当前登录是否有账户
  if (userStore.userInfo.user.tradingAccounts === []) {
    showDialog.value = true;
  }
};
// 启动函数
onMounted(() => {
  createFirstAccount();
});
</script>

<template>
  <div>
    <personal-column></personal-column>
    <dashboard-header></dashboard-header>
  </div>

  <account-dialog :show="showDialog" @close="closeDialog"></account-dialog>
</template>