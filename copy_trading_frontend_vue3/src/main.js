import { createApp } from 'vue';
import App from './App.vue';
import axios from 'axios';

// 设置 axios 的基础 URL 为后端服务器地址
axios.defaults.baseURL = 'http://localhost:9099';

// 将 axios 添加为全局实例
const app = createApp(App);
app.config.globalProperties.$axios = axios;

app.mount('#app');
