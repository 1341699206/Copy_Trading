<script setup>
import { reactive, ref } from "vue";
import { ElMessage } from "element-plus";
import "element-plus/theme-chalk/el-message.css";

import { useUserStore } from "@/stores/user";
const userStore = useUserStore();

import { useAccountStore } from "@/stores/account";
const accountStore=useAccountStore();

defineProps({
  show: {
    type: Boolean,
    default: false,
  },
});

const emit = defineEmits(["close"]);

//账户信息
const accountInfo = reactive({
  id: userStore.userInfo.id,
  balance: 0,
});

//限制规则
const rules = {
  balance: [
    { required: true, message: "Simulation amount cannot be empty." },
    {
      type: "number",
      min: 1000,
      max: 100000,
      message: "The simulation amount needs to be between 1000 and 100000.",
    },
  ],
};

const formRef = ref(null);

// 执行账户创建
const doCreateAccount = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        // 调用 createAccount
        await accountStore.createAAccount({userId:accountInfo.id,initialBalance:accountInfo.balance});
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
        trigger: "blur",
      });
    }
  });
};
</script>

<template>
  <el-dialog :model-value="show" title="New Account" width="500">
    <el-form ref="formRef" :model="accountInfo" :rules="rules">
      <!-- 选择金额 -->
      <el-form-item label="Balance" prop="balance">
        <el-input-number v-model="accountInfo.balance" :step="1000" />
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
