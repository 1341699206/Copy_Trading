<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import PersonalColumn from "./component/PersonalColumn.vue";
import DashboardBody from "./component/Body/DashboardBody.vue";
import AccountDialog from "./component/AccountDialog.vue";

import { useAccountStore } from "@/stores/account";
import { useUserStore } from "@/stores/user";
import { useRoleStore } from "@/stores/roleBasicData";
import { useTradeStore } from "@/stores/tradeData";

const accountStore = useAccountStore();
const userStore = useUserStore();
const tradeStore = useTradeStore();

const roleStore = useRoleStore();
roleStore.getRoleInfo({
  role: userStore.userInfo.role,
  id: userStore.userInfo.id,
});

const showDialog = ref(false);
const closeDialog = () => {
  showDialog.value = false;
};

//强制创建第一个账户
const createFirstAccount = async () => {
  //检测当前登录是否有账户
  try {
    await accountStore.getAccountInfo(userStore.userInfo.id);
  } catch (error) {
    showDialog.value = true;
  }
};
// 启动函数
onMounted(async () => {
  await createFirstAccount();
  if (accountStore.accountInfo.id){
    tradeStore.startListening(accountStore.accountInfo.id); //对trade进行连接
    await tradeStore.getOpenedTradeInfo(accountStore.accountInfo.id);
    tradeStore.tradeInfo=accountStore.accountInfo.trades;
  }
  accountStore.startListening(userStore.userInfo.id); //对account进行连接
});

onUnmounted(() => {
  accountStore.stopListening(); //停止account连接
  tradeStore.stopListening(accountStore.accountInfo.id); //停止trade连接
});
</script>

<template>
  <div>
    <personal-column></personal-column>
    <dashboard-body></dashboard-body>
  </div>
  <account-dialog :show="showDialog" @close="closeDialog"></account-dialog>
</template>