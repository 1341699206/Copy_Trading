<script setup>
import { defineProps } from "vue";

// 定义组件的 props，接收父组件传递的 `item` 数据
defineProps({
  item: {
    type: Object,
    required: true,
  },
});

const collect=()=>{

}

</script>

<template>
  <div class="container">
    <div class="avatar"></div>
    <div class="name">{{item.name}}</div>
    <el-icon class="collection" @click="collect"><Star /></el-icon>
    <div class="information">
      <div class="copiers">{{item.trades}}</div>
      <div class="followers">{{item.followers}}</div>
    </div>
    <div class="ROI">{{item.ROI}}</div>
    <div class="RIOHistory"></div>
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
  padding: 20px;
  width: 300px;
  margin: 15px;
  position: relative;

  .avatar {
    width: 80px;
    height: 80px;
    border-radius: 50%;
    background-color: #f2f2f2;
    margin-bottom: 15px;
  }

  .name {
    font-size: 18px;
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
      font-size: 14px;
      color: #666;
    }
  }

  .ROI {
    font-size: 20px;
    font-weight: bold;
    color: #4caf50; // 绿色 ROI
    margin-top: 10px;
  }

  .RIOHistory {
    width: 100%;
    height: 50px;
    background-color: #e0e0e0;
    margin-top: 10px;
    border-radius: 8px;
  }
}
</style>
