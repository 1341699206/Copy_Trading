import { createRouter, createWebHistory } from 'vue-router';

import FollowerPage from '@/views/Follower/Follower_Page.vue' //导入主页
import Home from '@/views/Follower/Home/HomePage.vue' //导入主页的HOME
import Trader from '@/views/Follower/Traders/TradersPage.vue' //导入主页的Trader
import Market from '@/views/Follower/Market/MarketPage.vue' //导入主页的Market
import Community from '@/views/Follower/Community/CommunityPage.vue' //导入主页的Community

import LoginsPage from '@/views/Logins/LoginsPage.vue' //导入登录页面
import Login from '@/views/Logins/Login/LoginPage.vue' //导入登录页面的login
import Register from '@/views/Logins/Register/RegisterPage.vue';  // 导入登录页面的register

import TraderDashboard from '@/views/Trader/TraderDashboard.vue';  // 导入Trader的Dashboard页面


import AdminDashboard from '@/views/Admin/AdminDashboard.vue';  // 导入Admin的Dashboard页面
// import { pa } from 'element-plus/es/locale';





const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  //path和component对应关系的位置，
  routes:[
    {
      path:'/',
      component: FollowerPage,
      children:[
        {
          path:'',
          component: Home
        },
        {
          path:'traders',
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
      component: LoginsPage,
      children:[
        {
          path:'',
          component: Login
        },
        {
          path:'/register',
          component: Register
        }
      ]
    },
    {
      path:'/trader',
      component: TraderDashboard
    },
    {
      path:'/admin',
      component: AdminDashboard
    }
  ]
});

export default router;





