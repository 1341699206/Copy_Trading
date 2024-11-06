<script setup>
import { onMounted, reactive, ref } from "vue";
import { getCurrencyAPI, createTradingAccount } from "@/apis/follower";
import { ElMessage } from "element-plus";
import "element-plus/theme-chalk/el-message.css";

import { useUserStore } from "@/stores/user";
const userStore = useUserStore();

defineProps({
  show: {
    type: Boolean,
    default: false,
  },
});

const emit = defineEmits(["close"]);

//账户信息
const accountInfo = reactive({
  id: userStore.userInfo.user.followerId,
  role: "FOLLOWER",
  accountNumber: "",
  accountType: "",
  currency: "",
  balance: 0,
  leverage: 1,
  status: "Active",
});

//限制规则
const rules = {
  accountNumber: [
    { required: true, message: "Account number cannot be empty." },
  ],
  accountType: [{ required: true, message: "Account type cannot be empty." }],
  currency: [{ required: true, message: "Currency cannot be empty." }],
  balance: [
    { required: true, message: "Simulation amount cannot be empty." },
    {
      type: 'number',
      min: 200,
      max: 10000,
      message: "The simulation amount needs to be between 200 and 10000."
    },
  ],
  leverage: [
    { required: true, message: "Leverage cannot be empty." },
    {
      type: 'number',
      min: 1,
      max: 100,
      message: "Leverage must be between 1 and 100."
    },
  ],
};

// 从后端获取币种
const currencies = ref([]);
const getCurrency = async () => {
  try {
    const res = await getCurrencyAPI();
    currencies.value = res.data;
  } catch {
    //token过期处理
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
        // 调用 createTradingAccount
        await createTradingAccount(accountInfo);
        //创建成功提示
        ElMessage({ type: "success", message: "Create successful!" });
        //关闭弹窗
        emit("close");
        //清空表单
        formRef.value?.resetFields();
      } catch (error) {
        // 处理创建失败的情况
        ElMessage({ type: "error", message: "Account creation failed!" });
      }
    } else {
      ElMessage({
        type: "error",
        message: "Please fill out the form correctly!",
         trigger: 'blur'
      });
    }
  });
};
</script>


<template>
  <el-dialog :model-value="show" title="New Account" width="500">
    <el-form ref="formRef" :model="accountInfo" :rules="rules">
      <!-- 账户编号 -->
      <el-form-item label="Account Name" prop="accountNumber">
        <el-input v-model="accountInfo.accountNumber" autocomplete="off" />
      </el-form-item>

      <!-- 账户类型 -->
      <el-form-item label="Account Type" prop="accountType">
        <el-select
          v-model="accountInfo.accountType"
          placeholder="Please set account type"
        >
          <el-option label="Simulated Account" value="Simulated Account"/>
          <el-option label="Real Account" value="Real Account"/>
        </el-select>
      </el-form-item>

      <!-- 如果是模拟账户，则可以选择金额 -->
      <template v-if="accountInfo.accountType === 'Simulated Account'">
        <el-form-item label="Balance" prop="balance">
          <el-input-number v-model="accountInfo.balance" :step="100" />
        </el-form-item>
      </template>

      <!-- 账户币种 -->
      <el-form-item prop="currency" label="Currency">
        <el-select
          v-model="accountInfo.currency"
          placeholder="Please set currency"
        >
          <el-option
            v-for="currency in currencies"
            :key="currency"
            :value="currency"
          >
            {{ currency }}
          </el-option>
        </el-select>
      </el-form-item>

      <!-- 杠杆比例 -->
      <el-form-item label="Leverage" prop="leverage">
        <el-input-number v-model="accountInfo.leverage" :min="1" :max="100" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="emit('close')">Cancel</el-button>
        <el-button type="primary" @click="doCreateAccount">Create</el-button>
      </div>
    </template>
  </el-dialog>
</template>
