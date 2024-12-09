<script setup>
import { computed, defineProps, ref, onMounted } from "vue";
import { generateAvatar } from "@/utils/avatar";
import { follow, unfollow,checkIsFollow } from "@/apis/followerManagement";
import { getAccountDetails } from "@/apis/accountManagement";
import { useAccountStore } from "@/stores/account";

const props = defineProps({
  traderBasicInf: {
    type: Object,
    default: null,
  },
});

const accountStore = useAccountStore();
const followerAccountId = accountStore.accountInfo.id;

const traderAccountId=ref();

const avatar = computed(() => generateAvatar(props.traderBasicInf.user.username));

// 控制 follow 按钮的显示状态
const isFollow = ref(false);
const showDialog = ref(false); // 控制对话框的显示状态

onMounted(async () => {
  traderAccountId.value= (await getAccountDetails(props.traderBasicInf.traderId)).id;
  if(await checkIsFollow({followerAccountId:followerAccountId,traderAccountId:traderAccountId.value})){
    isFollow.value = true; // 根据实际逻辑更新
  }
});

const doFollow = async () => {
  if (!followerAccountId) {
    showDialog.value = true; // 弹出提示对话框
    return;
  }
  try {
    await follow({ followerAccountId: followerAccountId, traderAccountId: traderAccountId.value });
    isFollow.value = true;
  } catch (error) {
    console.error("关注失败", error);
  }
};

const doUnfollow = async () => {
  try {
    await unfollow({ followerAccountId: followerAccountId, traderAccountId: traderAccountId.value });
    isFollow.value = false;
  } catch (error) {
    console.error("取消关注失败", error);
  }
};
</script>

<template>
  <div class="container">
    <div class="avatar">
      <div v-if="traderBasicInf" class="avatar-content">
        <img :src="avatar" alt="Trader Avatar" />
      </div>
    </div>
    <div class="name">
      {{ traderBasicInf ? traderBasicInf.user.username : "Trader" }}
    </div>
    <div class="button">
      <el-button
        v-if="!isFollow"
        type="primary"
        @click="doFollow"
        class="action-button"
      >
        Follow
      </el-button>
      <el-button
        v-else
        type="danger"
        @click="doUnfollow"
        class="action-button"
      >
        Unfollow
      </el-button>
    </div>

    <!-- 未登录提示弹窗 -->
    <el-dialog
      title="Tip"
      v-model:visible="showDialog"
      width="30%"
      center
    >
      <p>Please log in before operating!！</p>
      <template #footer>
        <el-button type="primary" @click="showDialog = false">confirm</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  align-items: center;
  justify-content: space-between; /* 按钮固定在右侧 */
  padding: 0 1%;
}

.avatar {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  overflow: hidden;
  background-color: #f2f2f2;
  display: flex;
  justify-content: center;
  align-items: center;
}

.avatar-content {
  width: 100%;
  height: 100%;
  background-color: #e0e0e0;
}

.name {
  font-size: 20px;
  font-weight: bold;
  margin-left: 1%;
  flex-grow: 1; /* 自动扩展以占用剩余空间 */
}

.button {
  flex-shrink: 0; /* 固定宽度 */
}

.action-button {
  width: 100px; /* 按钮宽度 */
  text-align: center;
}
</style>
