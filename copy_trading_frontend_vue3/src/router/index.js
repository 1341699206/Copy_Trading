import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from "@/stores/user"

// 导入页面组件
import FollowerPage from '@/views/Follower/Follower_Page.vue'
import FollowerDashboard from '@/views/Follower/FollowerDashboard/FollowerDashboard.vue'
import Home from '@/views/Follower/Home/HomePage.vue';  // 导入主页的HOME
import Trader from '@/views/Follower/Traders/TradersPage.vue';  // 导入主页的Traders
import Market from '@/views/Follower/Market/MarketPage.vue';  // 导入主页的Market
import Community from '@/views/Follower/Community/CommunityPage.vue';  // 导入主页的Community
import TraderHomePage from '@/views/Follower/TraderHomePage/TraderHomePage.vue'; //导入主页的TraderHomePage

import LoginsPage from '@/views/Logins/LoginsPage.vue' //导入登录页面
import Login from '@/views/Logins/Login/LoginPage.vue' //导入登录页面的login
import Register from '@/views/Logins/Register/RegisterPage.vue';  // 导入登录页面的register

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
// import { pa } from 'element-plus/es/locale';




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
          path:'/followerDashboard',
          component:FollowerDashboard
        },
        {
          path: 'traders',
          component: Trader,
        },
        {
          path: 'market',
          component: Market,
        },
        {
          path:'community',
          component: Community
        },
        {
          path:'trader/:id',
          component: TraderHomePage,
        }
      ]
    },
    {
      path: '/login',
      component: LoginsPage,
      children: [
        { path: '', component: Login },
        { path: '/register', component: Register },
      ],
    },
    {
      path: '/trader_page',
      component: TraderDashboard,
      meta: { requiresAuth: true, role: 'TRADER' },
      children: [
        {
          path: '',
          component: TraderBasicInfo,
          children: [
            { path: 'positions', component: Positions },
            { path: '', component: Account },
            { path: 'widgets', component: Widgets },
            { path: 'history', component: History },
            { path: 'invite', component: Invite },
            { path: 'revenue', component: Revenue },
            { path: 'settings', component: Settings },
            { path: 'social-feed', component: SocialFeed },
            { path: 'trade', component: Trade },
            { path: 'ztp', component: Ztp },
          ],
        },
      ],
    },
    {
      path: '/admin',
      component: AdminDashboard,
      meta: { requiresAuth: true, role: 'ADMIN' },
    },
  ],
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  const isLoggedIn = !!userStore.userInfo.token
  const userRole = userStore.userInfo.role

  console.log("Route Guard - User Role:", userRole)
  console.log("Route Guard - Target Role:", to.meta.role)

  // 检查是否需要身份验证
  if (to.meta.requiresAuth && !isLoggedIn) {
    return next('/login')
  }

  // 检查角色是否匹配
  if (to.meta.role && to.meta.role !== userRole) {
    console.warn("无权访问该页面，请检查您的权限。")
    return next('/login')
  }

  next()
})

export default router
