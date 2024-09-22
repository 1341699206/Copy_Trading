import { createRouter, createWebHistory } from 'vue-router';
import LoginPage from '../views/LoginPage.vue';  // 更新这里
import RegisterPage from '../views/RegisterPage.vue';  // 更新这里

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: LoginPage
  },
  {
    path: '/register',
    name: 'Register',
    component: RegisterPage
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

export default router;
