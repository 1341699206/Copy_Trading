<script setup>
import { defineProps,computed } from "vue";
import { useRouter } from "vue-router";

// 定义组件的 props，接收父组件传递的 `item` 数据
const props =defineProps({
  item: {
    type: Object,
    required: true,
  },
});

// 导向动态的trader界面
const router = useRouter();
const goToTraderPage = () => {
  router.push({ path: `/trader/${props.item.traderId}` });
};

// 收藏逻辑
const collect=()=>{

}

</script>

<template>
  <div @click="goToTraderPage" class="clickable-card">
    <div class="container" @click.stop>
      <div class="avatar"></div>
      <div class="name">{{ item.name }}</div>
      <el-icon class="collection" @click.stop="collect"><Star /></el-icon>
      <div class="information">
        <div class="copiers">copiers: {{ item.followersWhoFollowed ?? 0 }}</div>
        <div class="followers">followers: {{ item.followersWhoFavorited ?? 0 }}</div>
      </div>
      <div class="ROI">ROI: {{ formattedROI }}</div>
      <div class="RIOHistory"></div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  background-color: #fff;
  border-radius: 12px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 20px; // 增加padding以适应更大卡片
  width: 250px; // 增加卡片宽度
  margin: 15px;
  position: relative;

  .avatar {
    width: 60px; // 缩小头像
    height: 60px;
    border-radius: 50%;
    background-color: #f2f2f2;
    margin-bottom: 15px;
  }

  .name {
    font-size: 20px; // 增大名字字体
    font-weight: bold;
    margin-bottom: 10px;
    text-align: center;
  }

  .collection {
    position: absolute;
    top: 15px;
    right: 15px;
    cursor: pointer;
    color: #ffd700; // 金色星星
  }

  .information {
    display: flex;
    justify-content: space-between; // 控制内容在两边分布
    width: 100%;
    margin-top: 10px;

    .copiers,
    .followers {
      font-size: 16px; // 调整文字大小以适应更大的卡片
      color: #666;
    }
  }

  .ROI {
    font-size: 22px; // 调整 ROI 字体大小
    font-weight: bold;
    color: #4caf50; // 绿色 ROI
    margin-top: 10px;
  }

  .RIOHistory {
    width: 100%;
    height: 60px; // 调整历史 ROI 区域大小
    background-color: #e0e0e0;
    margin-top: 10px;
    border-radius: 8px;
  }
}
</style>