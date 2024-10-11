<script setup>
const emit = defineEmits(["update-content"]);

const updateContent = (item) => {
  emit("update-content", item); // 通过 emit 向父组件发送事件
};
</script>
<template>
    <div class="index">
        <ul class="nav">
            <li>
                <el-button text @click="updateContent('portfolio')">Portfolio</el-button>
            </li>
            <li>
                <el-button text @click="updateContent('overview')">Overview</el-button>
            </li>
            <li>
                <el-button text @click="updateContent('advancedStats')">Advanced Stats</el-button>
            </li>
            <li>
                <el-button text @click="updateContent('copiers')">Copiers</el-button>
            </li>
        </ul>
    </div>
</template>

<style lang="scss" scoped>
.index {
  width: 100%; /* 横向占满父容器宽度 */
  padding: 10px; /* 上下内边距 */

  .nav {
    display: flex; /* 横排显示 */
    list-style: none; /* 去掉点 */
    padding: 0; /* 去掉内边距 */
    margin: 0; /* 去掉外边距 */
    justify-content: flex-start; /* 从左侧开始排列 */

    /* 添加左右内边距以保持与父容器的距离 */
    padding-left: 0px; /* 左侧内边距 */

    li {
      margin-right: 20px; /* 按钮之间的间距 */
    }

    /* 修改按钮中的字体大小 */
    :deep .el-button {
      font-size: 18px; /* 增大字体大小 */
      transition: background-color 0.3s, color 0.3s; /* 添加过渡效果 */
    }

    /* 按钮聚焦状态下的背景色和文字颜色 */
    :deep .el-button:focus {
      background-color: $xtxColor; /* 改变背景色 */
      color: #fff; /* 改变文字颜色为白色 */
    }

    /* 按钮失去焦点时恢复背景色 */
    :deep .el-button:not(:focus) {
      background-color: transparent; /* 恢复为透明背景 */
      color: #333; /* 恢复为默认文字颜色 */
    }
  }
}
</style>