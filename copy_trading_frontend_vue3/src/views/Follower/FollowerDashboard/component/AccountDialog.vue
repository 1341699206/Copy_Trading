<script setup>
import { onMounted, reactive, ref } from "vue";
import { getCurrencyAPI, createAccountAPI } from "@/apis/follower";
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
});
console.info();
//限制规则
const rules = {
  accountNumber: [
    { required: true, message: " account number cannot be empty. " },
  ],
  accountType: [{ required: true, message: " account type cannot be empty. " }],
  currency: [{ required: true, message: " currency cannot be empty. " }],
  balance: [
    { required: true, message: " Simulation amount cannot be empty. " },
    {
      type: 'number',
      min: 200,
      max: 10000,
      message: " The simulation amount needs to be between 200 and 10000. ",
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
        // 调用 createAccountAPI
        await createAccountAPI(accountInfo);
        //创建成功提示
        ElMessage({ type: "success", message: "Create successful!" });
        //关闭弹窗
        emit("close");
        //清空表单
        formRef.value?.resetFields();
      } catch (error) {
        // 处理创建失败的情况
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
      <el-form-item label="account number" prop="accountNumber">
        <el-input v-model="accountInfo.accountNumber" autocomplete="off" />
      </el-form-item>
      <!-- 账户类型 -->
      <el-form-item label="account type" prop="accountType">
        <el-select
          v-model="accountInfo.accountType"
          placeholder="Please set account type"
        >
          <el-option label="Simulated account" value="Simulated account"/>
          <el-option label="Real account" value="Real account"/>
        </el-select>
      </el-form-item>
      <!-- 如果是模拟账户，则可以选择金额 -->
      <template v-if="accountInfo.accountType === 'Simulated account'">
        <el-form-item label="balance" prop="balance">
          <el-input-number v-model="accountInfo.balance" :step="100" />
        </el-form-item>
      </template>
      <!-- 账户币种 -->
      <el-form-item prop="currency" label="currency">
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
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="emit('close')">Cancel</el-button>
        <el-button type="primary" @click="doCreateAccount">Create</el-button>
      </div>
    </template>
  </el-dialog>
</template>