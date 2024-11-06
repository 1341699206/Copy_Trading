import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from "@/stores/user"

// 导入页面组件
import FollowerPage from '@/views/Follower/Follower_Page.vue'
import FollowerDashboard from '@/views/Follower/FollowerDashboard/FollowerDashboard.vue'
import Home from '@/views/Follower/Home/HomePage.vue'
import Trader from '@/views/Follower/Traders/TradersPage.vue'
import Market from '@/views/Follower/Market/MarketPage.vue'
import Community from '@/views/Follower/Community/CommunityPage.vue'
import LoginsPage from '@/views/Logins/LoginsPage.vue'
import Login from '@/views/Logins/Login/LoginPage.vue'
import Register from '@/views/Logins/Register/RegisterPage.vue'
import TraderDashboard from '@/views/Trader/TraderDashboard.vue'
import TraderBasicInfo from '@/views/Trader/secondary_page/trader_basic_information.vue'
import Positions from '@/views/Trader/secondary_page/Three_level_page/Positions/POSITIONS.vue'
import Account from '@/views/Trader/secondary_page/Three_level_page/Account/ACCOUNT.vue'
import Widgets from '@/views/Trader/secondary_page/Three_level_page/Widgets/WIDGETS.vue'
import History from '@/views/Trader/secondary_page/Three_level_page/History/HISTORY.vue'
import Invite from '@/views/Trader/secondary_page/Three_level_page/Invite/INVITE.vue'
import Revenue from '@/views/Trader/secondary_page/Three_level_page/Revenue/REVENUE.vue'
import Settings from '@/views/Trader/secondary_page/Three_level_page/Settings/SETTINGS.vue'
import SocialFeed from '@/views/Trader/secondary_page/Three_level_page/Socialfeed/SOCIAL_FEED.vue'
import Trade from '@/views/Trader/secondary_page/Three_level_page/Trade/TRADE.vue'
import Ztp from '@/views/Trader/secondary_page/Three_level_page/Ztp/ZTP.vue'
import AdminDashboard from '@/views/Admin/AdminDashboard.vue'

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: FollowerPage,
      children: [
        { path: '', component: Home },
        { path: '/followerDashboard', component: FollowerDashboard },
        { path: 'traders', component: Trader },
        { path: 'market', component: Market },
        { path: 'community', component: Community },
      ],
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
