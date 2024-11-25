<script setup>
import { reactive, ref } from "vue";
import { createAccount } from "@/apis/accountManagement";
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
  id: userStore.userInfo.userId,
  balance: 0,
});

//限制规则
const rules = {
  balance: [
    { required: true, message: "Simulation amount cannot be empty." },
    {
      type: 'number',
      min: 200,
      max: 10000,
      message: "The simulation amount needs to be between 200 and 10000."
    },
  ]
};

const formRef = ref(null);

// 执行账户创建
const doCreateAccount = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        // 调用 createTradingAccount
        await createAccount(accountInfo);
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

      <!-- 选择金额 -->
      <template>
        <el-form-item label="Balance" prop="balance">
          <el-input-number v-model="accountInfo.balance" :step="100" />
        </el-form-item>
      </template>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="emit('close')">Cancel</el-button>
        <el-button type="primary" @click="doCreateAccount">Create</el-button>
      </div>
    </template>
  </el-dialog>
</template>
