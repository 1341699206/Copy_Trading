<script setup>
import { ref, computed, onMounted } from "vue";
import { useUserStore } from "@/stores/user";
import { useStrategyStore } from "@/stores/strategy";

const userStore = useUserStore();
const strategyStore = useStrategyStore();

// 策略信息
const strategy = computed(() => strategyStore.strategyInfo);
onMounted(async () => {
  await strategyStore.getStrategyInfo(strategyInfo.value.id);
});

const strategyInfo = ref({
  id: strategy.value?.id || 0,
  name: strategy.value?.name || "",
  description: strategy.value?.description || "",
  scriptContent: strategy.value?.scriptContent || "",
  isActive: strategy.value?.isActive ?? true,
});

const isStrategyEmpty = computed(() => !strategy.value?.name);

// 表单验证规则
const rules = {
  name: [
    { required: true, message: "Name cannot be empty" },
    { min: 4, max: 16, message: "Name length required 4-16 characters" },
  ],
  description: [{ required: true, message: "Description cannot be empty" }],
};

const formRef = ref(null);

const doCreate = async () => {
  await strategyStore.createStrategy({
    traderId: userStore.userInfo.id,
    name: strategyInfo.value.name,
    description: strategyInfo.value.description,
    scriptContent: strategyInfo.value.scriptContent,
  });
};

const doUpdate = async () => {
  await strategyStore.updateStrategy(strategyInfo.value);
};

const doDelete = async () => {
  await strategyStore.deleteStrategy(strategyInfo.value.id);
  // 清空数据
  strategyInfo.value = {
    id: 0,
    name: "",
    description: "",
    scriptContent: "",
    isActive: true,
  };
};
</script>

<template>
  <div class="page-container">
    <div class="form-card">
      <el-form
        ref="formRef"
        :model="strategyInfo"
        :rules="rules"
        label-position="top"
        class="strategy-form"
      >
        <!-- Name -->
        <el-form-item label="Name" prop="name">
          <el-input
            v-model="strategyInfo.name"
            placeholder="Enter strategy name"
          />
        </el-form-item>

        <!-- Description -->
        <el-form-item label="Description" prop="description">
          <el-input
            type="textarea"
            v-model="strategyInfo.description"
            placeholder="Enter description"
          />
        </el-form-item>

        <!-- Script Content -->
        <el-form-item label="Script Content" prop="scriptContent">
          <el-input
            type="textarea"
            v-model="strategyInfo.scriptContent"
            placeholder="Enter script content"
          />
        </el-form-item>

        <!-- isActive 选项 -->
        <el-form-item v-if="!isStrategyEmpty" label="Active">
          <el-switch
            v-model="strategyInfo.isActive"
            active-text="Yes"
            inactive-text="No"
          />
        </el-form-item>

        <!-- 按钮 -->
        <div class="button-group">
          <el-button
            type="primary"
            size="large"
            @click="isStrategyEmpty ? doCreate() : doUpdate()"
          >
            {{ isStrategyEmpty ? "Create" : "Update" }}
          </el-button>
          <el-button type="danger" size="large" @click="doDelete"
            >Delete</el-button
          >
        </div>
      </el-form>
    </div>
  </div>
</template>

<style scoped>
.page-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 20px;
}

.form-card {
  background: white;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  max-width: 600px;
  width: 100%;
}

.strategy-form {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.button-group {
  display: flex;
  justify-content: space-between;
  gap: 10px;
}

.footer-text {
  margin-top: 20px;
  text-align: center;
  color: #6b7280;
  font-size: 14px;
}
</style>
