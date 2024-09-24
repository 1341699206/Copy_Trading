import { createRouter, createWebHistory } from 'vue-router';

import LayoutPage from '@/views/Layout/LayoutPage.vue' //导入主页
import Home from '@/views/Layout/Home/HomePage.vue' //导入主页的HOME
import Trader from '@/views/Layout/Traders/TradersPage.vue' //导入主页的Trader
import Market from '@/views/Layout/Market/MarketPage.vue' //导入主页的Market
import Community from '@/views/Layout/Community/CommunityPage.vue' //导入主页的Community

import LoginPage from '@/views/Login/LoginPage.vue';  // 导入登录页面
import RegisterPage from '@/views/Login/RegisterPage.vue';  // 导入注册页面

import TraderDashboard from '@/views/Trader/TraderDashboard.vue';  // 导入Trader的Dashboard页面

import FollowerDashboard from '@/views/Follower/FollowerDashboard.vue';  // 导入Follower的Dashboard页面

import AdminDashboard from '@/views/Admin/AdminDashboard.vue';  // 导入Admin的Dashboard页面


const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  //path和component对应关系的位置，
  routes:[
    {
      path:'/',
      component: LayoutPage,
      children:[
        {
          path:'',
          component: Home
        },
        {
          path:'trader',
          component: Trader
        },
        {
          path:'market',
          component: Market
        },
        {
          path:'community',
          component: Community
        }
      ]
    },
    {
      path:'/login',
      component: LoginPage,
    },
    {
      path: "/register",
      component: RegisterPage
    },
    {
      path:'/trader',
      component: TraderDashboard
    },
    {
      path:'/follower',
      component: FollowerDashboard
    },
    {
      path:'/admin',
      component: AdminDashboard
    }
  ]
});

export default router;
