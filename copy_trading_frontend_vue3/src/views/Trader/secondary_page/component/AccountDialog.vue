<script setup>
import { onMounted, reactive, ref, watch } from "vue";
import { getCurrencyAPI, createTraderAccountAPI } from "@/apis/follower";
import { ElMessage } from "element-plus";
import "element-plus/theme-chalk/el-message.css";

import { useUserStore } from "@/stores/user";
const userStore = useUserStore();

const props = defineProps({
  show: {
    type: Boolean,
    default: false,
  },
});

const emit = defineEmits(["close"]);

// 本地变量，控制对话框显示状态
const localShow = ref(props.show);

// 监听 props.show 的变化，同步更新 localShow
watch(
  () => props.show,
  (newVal) => {
    localShow.value = newVal;
  }
);

// 关闭对话框时触发 close 事件
const closeDialog = () => {
  localShow.value = false;
  emit("close");
};

// 账户信息
const accountInfo = reactive({
  traderId: userStore.userInfo.user?.traderId || null,
  accountNumber: "",
  accountType: "",
  currency: "",
  balance: 0,
  equity: 0,
  realisedPNL: 0,
  margin: 0,
  freeMargin: 0,
  status: "Active",
});

// 限制规则
const rules = {
  accountNumber: [
    { required: true, message: "Account number cannot be empty." },
  ],
  accountType: [{ required: true, message: "Account type cannot be empty." }],
  currency: [{ required: true, message: "Currency cannot be empty." }],
  balance: [
    { required: true, message: "Simulation amount cannot be empty." },
    {
      type: "number",
      min: 200,
      max: 10000,
      message: "The simulation amount needs to be between 200 and 10000.",
    },
  ],
};

// 从后端获取币种
const currencies = ref([]);
const getCurrency = async () => {
  try {
    const res = await getCurrencyAPI();
    currencies.value = res.data || [];
  } catch (error) {
    console.error("Failed to fetch currencies:", error);
  }
};

// 启动获取货币函数
onMounted(() => {
  getCurrency();
});

const formRef = ref(null);

// 执行账户创建
const doCreateAccount = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        const response = await createTraderAccountAPI(accountInfo);
        if (response.status === 200) {
          ElMessage({ type: "success", message: "Create successful!" });
          closeDialog();
          formRef.value?.resetFields();
        } else {
          throw new Error("Failed to create account");
        }
      } catch (error) {
        console.error("Account creation failed:", error);
        ElMessage({
          type: "error",
          message: "Account creation failed!",
        });
      }
    } else {
      ElMessage({
        type: "error",
        message: "Please fill out the form correctly!",
        trigger: "blur",
      });
    }
  });
};
</script>

<template>
  <el-dialog v-model="localShow" title="New Trader Account" width="500" @close="closeDialog">
    <el-form ref="formRef" :model="accountInfo" :rules="rules">
      <!-- 账户编号 -->
      <el-form-item label="Account Number" prop="accountNumber">
        <el-input v-model="accountInfo.accountNumber" autocomplete="off" />
      </el-form-item>

      <!-- 账户类型 -->
      <el-form-item label="Account Type" prop="accountType">
        <el-select v-model="accountInfo.accountType" placeholder="Please set account type">
          <el-option label="Simulated Account" value="Simulated Account" />
          <el-option label="Real Account" value="Real Account" />
        </el-select>
      </el-form-item>

      <!-- 模拟账户时可以选择余额 -->
      <template v-if="accountInfo.accountType === 'Simulated Account'">
        <el-form-item label="Balance" prop="balance">
          <el-input-number v-model="accountInfo.balance" :step="100" />
        </el-form-item>
      </template>

      <!-- 账户币种 -->
      <el-form-item prop="currency" label="Currency">
        <el-select v-model="accountInfo.currency" placeholder="Please set currency">
          <el-option v-for="currency in currencies" :key="currency" :value="currency">
            {{ currency }}
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="closeDialog">Cancel</el-button>
        <el-button type="primary" @click="doCreateAccount">Create</el-button>
      </div>
    </template>
  </el-dialog>
</template>
