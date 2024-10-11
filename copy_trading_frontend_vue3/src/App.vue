<template>
  <div id="app">
    <!-- 一级路由出口 -->
    <router-view></router-view>  <!-- 渲染当前路由组件 -->
  </div>
</template>

<script>
import { onMounted } from 'vue';
import { renovateAPI } from '@/apis/user';
export default {
  name: 'App',
};
// 启动函数
onMounted(() => {
  renovateAPI()
});

</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 0px;
}
</style>
