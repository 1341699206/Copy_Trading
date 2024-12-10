<script setup>
import { reactive, ref, computed } from "vue";
import { useAccountStore } from "@/stores/account";
import { useTradeStore } from "@/stores/tradeData";
import { useStrategyStore } from "@/stores/strategy";
import { ElMessage } from "element-plus";
import "element-plus/theme-chalk/el-message.css";

const account = useAccountStore().accountInfo;
const tradeStore = useTradeStore();
const strategy = useStrategyStore().strategyInfo;

const props = defineProps({
  show: {
    type: Boolean,
    default: false,
  },
  symbol: {
    type: String,
  },
  type: {
    type: String,
  },
});
const emit = defineEmits(["close"]);

// 账户信息
const tradeInfo = reactive({
  accountId: computed(() => account.id),
  strategyId: computed(() => strategy.id),
  symbol: computed(() => props.symbol),
  type: computed(() => props.type),
  lotSize: 0,
});

// 表单验证规则
const rules = {
  lotSize: [
    { required: true, message: "Lot size cannot be empty." },
    {
      type: "number",
      min: 0,
      max: account.balance,
      message: "The number of lots needs to be between 0 and account balance.",
    },
  ],
};

const formRef = ref(null);

// 执行开仓操作
const doOpenTrade = () => {
  if (strategy.id)
    formRef.value.validate(async (valid) => {
      if (valid) {
        try {
          await tradeStore.openATrade({
            accountId: tradeInfo.accountId,
            strategyId: tradeInfo.strategyId,
            symbol: tradeInfo.symbol,
            type: tradeInfo.type,
            lotSize: tradeInfo.lotSize,
          });
          ElMessage({ type: "success", message: "Trade opened successfully!" });
          emit("close");
          formRef.value?.resetFields();
        } catch (error) {
          ElMessage({ type: "error", message: "Failed to open trade!" });
        }
      } else {
        ElMessage({
          type: "error",
          message: "Please fill out the form correctly!",
        });
      }
    });
  else
    ElMessage({ type: "warn", message: "Please create strategy first!" });
};
</script>

<template>
  <el-dialog
    :model-value="show"
    title="New Trade"
    width="500"
    @close="emit('close')"
  >
    <el-form
      ref="formRef"
      :model="tradeInfo"
      :rules="rules"
      label-width="100px"
    >
      <el-form-item label="Symbol">
        <el-input v-model="tradeInfo.symbol" disabled></el-input>
      </el-form-item>
      <el-form-item label="Type">
        <el-input v-model="tradeInfo.type" disabled></el-input>
      </el-form-item>
      <el-form-item label="Lot Size" prop="lotSize">
        <el-input-number v-model="tradeInfo.lotSize" :step="100" :min="0" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="emit('close')">Cancel</el-button>
        <el-button type="primary" @click="doOpenTrade">Open</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped>
.dialog-footer {
  display: flex;
  justify-content: flex-end;
}
</style>
