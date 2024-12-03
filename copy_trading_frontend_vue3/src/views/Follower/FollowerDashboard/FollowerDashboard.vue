<script setup>
import { ref, onMounted } from "vue";
import PersonalColumn from "./component/PersonalColumn.vue";
import DashboardBody from "./component/Body/DashboardBody.vue"
import AccountDialog from "./component/AccountDialog.vue";

import { useAccountStore } from "@/stores/account";
import { useUserStore } from "@/stores/user";

const accountStore=useAccountStore();
const userStore=useUserStore();

const showDialog = ref(false);
const closeDialog=()=>{
    showDialog.value=false;
}

//强制创建第一个账户
const createFirstAccount = async() => {
  //检测当前登录是否有账户
  try{
    await accountStore.getAccountInfo(userStore.userInfo.id);
  }catch(error){
    showDialog.value=true;
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
    <dashboard-body></dashboard-body>
  </div>

  <account-dialog :show="showDialog" @close="closeDialog"></account-dialog>
</template>