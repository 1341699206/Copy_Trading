import { createRouter, createWebHistory } from 'vue-router';
import LoginPage from '@/views/LoginPage.vue';  // 导入登录页面
import TraderDashboard from '@/views/TraderDashboard.vue';  // 导入Trader的Dashboard页面
import FollowerDashboard from '@/views/FollowerDashboard.vue';  // 导入Follower的Dashboard页面
import AdminDashboard from '@/views/AdminDashboard.vue';  // 导入Admin的Dashboard页面
import RegisterPage from '@/views/RegisterPage.vue';  // 导入注册页面

const routes = [
  { path: '/', redirect: '/login' },  // 根路径重定向到登录页面
  { path: '/login', component: LoginPage },  // 登录页面路由
  { path: '/register', component: RegisterPage },  // 注册页面路由
  { path: '/trader-dashboard', component: TraderDashboard },  // Trader的Dashboard页面路由
  { path: '/follower-dashboard', component: FollowerDashboard },  // Follower的Dashboard页面路由
  { path: '/admin-dashboard', component: AdminDashboard },  // Admin的Dashboard页面路由
  // 其他路由
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
