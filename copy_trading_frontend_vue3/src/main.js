import { createApp } from 'vue';
import App from './App.vue';
import router from './router'; // 引入 router
import axios from 'axios';
import { createPinia } from 'pinia';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

// 设置 axios 的基础 URL 为后端服务器地址
axios.defaults.baseURL = 'http://localhost:9099';

// 创建 Vue 应用实例
const app = createApp(App);

// 配置全局 axios
app.config.globalProperties.$axios = axios;

// 使用 router
app.use(router);

//启用pinia
const pinia =createPinia()
pinia.use(piniaPluginPersistedstate)
app.use(pinia)

// 挂载 Vue 应用
app.mount('#app');
