<script setup>
import AccountDialog from "@/views/Trader/secondary_page/component/AccountDialog.vue"; // 使用同级目录路径导入组件
import { useTradeStore } from "@/stores/tradeData";
import { useUserStore } from "@/stores/user";
import { useRoleStore } from "@/stores/roleBasicData";
import { useAccountStore } from "@/stores/account";
import { useStrategyStore } from "@/stores/strategy";
import { ref, onMounted, onUnmounted } from "vue";

const userStore = useUserStore();

//加载strategy
const strategyStore = useStrategyStore();

//加载trade
const tradeStore = useTradeStore();

//加载account
const accountStore = useAccountStore();

//加载roleStore
const roleStore = useRoleStore();

// 弹窗控制
const showDialog = ref(false);

// 创建账户或检测账户
const createFirstAccount = async () => {
  try {
    await accountStore.getAccountInfo(userStore.userInfo.id); // 拉取账户信息
  } catch (error) {
    showDialog.value = true; // 如果没有账户，显示弹窗
  }
};

// 关闭对话框
const closeDialog = () => {
  showDialog.value = false;
};

onMounted(async () => {
  await createFirstAccount(); //检测创建第一个账户
  await strategyStore.getStrategyByTrader(userStore.userInfo.id);
  tradeStore.startListening(accountStore.accountInfo.id); //对trade进行连接
  if (strategyStore.strategyInfo.id)
    await tradeStore.getTradesInfo(strategyStore.strategyInfo.id); //策略存在时，获取trade数据并进行存储
  await roleStore.getRoleInfo({
    role: userStore.userInfo.role,
    id: userStore.userInfo.id,
  });
});

onUnmounted(() => {
  accountStore.stopListening();
  tradeStore.stopListening(accountStore.accountInfo.id);
});
</script>

<template>
  <!-- 账户创建对话框 -->
  <AccountDialog :show="showDialog" @close="closeDialog" />
</template>
