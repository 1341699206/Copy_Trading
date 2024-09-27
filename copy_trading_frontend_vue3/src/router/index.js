import { createRouter, createWebHistory } from 'vue-router';


import FollowerPage from '@/views/Follower/Follower_Page.vue';  // 导入主页
import Home from '@/views/Follower/Home/HomePage.vue';  // 导入主页的HOME
import Trader from '@/views/Follower/Traders/TradersPage.vue';  // 导入主页的Trader
import Market from '@/views/Follower/Market/MarketPage.vue';  // 导入主页的Market
import Community from '@/views/Follower/Community/CommunityPage.vue';  // 导入主页的Community

import LoginPage from '@/views/Login/LoginPage.vue';  // 导入登录页面
import RegisterPage from '@/views/Login/RegisterPage.vue';  // 导入注册页面

import TraderDashboard from '@/views/Trader/TraderDashboard.vue';  // 导入Trader的Dashboard页面
import TraderBasicInfo from '@/views/Trader/secondary_page/trader_basic_information.vue';  // 导入Trader的基本信息页面

// 导入trader的三级页面的组件
import Positions from '@/views/Trader/secondary_page/Three_level_page/Positions/POSITIONS.vue';
import Account from '@/views/Trader/secondary_page/Three_level_page/Account/ACCOUNT.vue';
import Widgets from '@/views/Trader/secondary_page/Three_level_page/Widgets/WIDGETS.vue';
import History from '@/views/Trader/secondary_page/Three_level_page/History/HISTORY.vue';
import Invite from '@/views/Trader/secondary_page/Three_level_page/Invite/INVITE.vue';
import Revenue from '@/views/Trader/secondary_page/Three_level_page/Revenue/REVENUE.vue';
import Settings from '@/views/Trader/secondary_page/Three_level_page/Settings/SETTINGS.vue';
import SocialFeed from '@/views/Trader/secondary_page/Three_level_page/Socialfeed/SOCIAL_FEED.vue';
import Trade from '@/views/Trader/secondary_page/Three_level_page/Trade/TRADE.vue';
import Ztp from '@/views/Trader/secondary_page/Three_level_page/Ztp/ZTP.vue';

import AdminDashboard from '@/views/Admin/AdminDashboard.vue';  // 导入Admin的Dashboard页面

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: FollowerPage,
      children: [
        {
          path: '',
          component: Home,
        },
        {
          path: 'trader',
          component: Trader,
        },
        {
          path: 'market',
          component: Market,
        },
        {
          path: 'community',
          component: Community,
        },
      ],
    },
    {
      path: '/login',
      component: LoginPage,
    },
    {
      path: '/register',
      component: RegisterPage,
    },
    {
      path: '/trader_page',
      component: TraderDashboard,
      children: [
        {
          path: '',  // Trader的基本信息页面作为二级路由
          component: TraderBasicInfo,
          children: [
            {
              path: 'positions',  // 三级路由 - Positions 页面
              component: Positions,
            },
            {
              path: '',  // 三级路由 - Account 页面
              component: Account,
            },
            {
              path: 'widgets',  // 三级路由 - Widgets 页面
              component: Widgets,
            },
            {
              path: 'history',  // 三级路由 - History 页面
              component: History,
            },
            {
              path: 'invite',  // 三级路由 - Invite 页面
              component: Invite,
            },
            {
              path: 'revenue',  // 三级路由 - Revenue 页面
              component: Revenue,
            },
            {
              path: 'settings',  // 三级路由 - Setting 页面
              component: Settings,
            },
            {
              path: 'social-feed',  // 三级路由 - SocialFeed 页面
              component: SocialFeed,
            },
            {
              path: 'trade',  // 三级路由 - Trade 页面
              component: Trade,
            },
            {
              path: 'ztp',  // 三级路由 - Ztp 页面
              component: Ztp,
            },
          ],
        },
      ],
    },
    {
      path: '/admin',
      component: AdminDashboard,
    },
  ],
});

export default router;
