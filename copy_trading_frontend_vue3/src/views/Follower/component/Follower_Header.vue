<script setup>
  import {useUserStore} from '@/stores/user'
  const userStore=useUserStore()
</script>

<template>
  <div class="app-header">
    <div class="container">
      <template v-if="userStore.userInfo.token">
        <div class="left">
          <ul>
            <li class="logo">
              <router-link to="/">Co-trade</router-link>
            </li>
            <li class="home">
              <router-link to="/followerDashboard">Dashboard</router-link>
            </li>
            <li>
              <router-link to="/traders">Traders</router-link>
            </li>
            <li>
              <router-link to="/market">Market</router-link>
            </li>
            <li>
              <router-link to="/community">Community</router-link>
            </li>
          </ul>
        </div>
        <div class="right">
          <ul>
            <li>
              <a href="javascript:;"><i class="iconfont icon-user"></i>{{userStore.userInfo.user.name}}</a>
            </li>
            <li>
              <el-popconfirm title="Sure you want to quit?" confirm-button-text="sure" cancel-button-text="cancel">
                <template #reference>
                  <a href="javascript:;">log out</a>
                </template>
              </el-popconfirm>
            </li>
          </ul>
        </div>
      </template>
      <template v-else>
        <div class="left">
          <ul class="app-header-nav">
            <li class="logo">
              <router-link to="/">Co-trade</router-link>
            </li>
            <li class="home">
              <router-link to="/">Home</router-link>
            </li>
            <li>
              <router-link to="/traders">Traders</router-link>
            </li>
            <li>
              <router-link to="/market">Market</router-link>
            </li>
            <li>
              <router-link to="/community">Community</router-link>
            </li>
          </ul>
        </div>
        <div class="right">
          <ul>
            <li><a @click="$router.push('/login')"><i class="iconFont icon-login"></i>login</a></li>
            <li><a @click="$router.push('/register')"><i class="iconFont icon-register"></i>register</a></li>
          </ul>
        </div>
      </template>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.app-header {
  background: #ffffff; /* 白色背景 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 添加阴影 */


  .container {
    height: 60px; /* 高度 */
    display: flex;
    justify-content: space-between; /* 左右对齐 */
    align-items: center; /* 垂直居中 */
    padding: 0 20px; /* 内边距 */
  }

  .left {
    ul {
      display: flex; /* 横排显示 */
      list-style: none; /* 去掉点 */
      padding: 0; /* 去掉内边距 */

      li {
        margin-right: 80px; /* 标签间距 */
        
        a {
          text-decoration: none; /* 去掉下划线 */
          font-size: 18px; /* 增大字体 */
          color: #333; /* 字体颜色 */
          transition: color 0.3s; /* 添加过渡效果 */

          &:hover {
            color: $xtxColor; /* 悬停时改变颜色 */
          }
        }
      }
    }
  }

  .right {
    ul {
      display: flex; /* 横排显示 */
      list-style: none; /* 去掉点 */
      padding: 0; /* 去掉内边距 */
      
      li {
        margin-left: 30px; /* 标签间距 */

        a {
          text-decoration: none; /* 去掉下划线 */
          font-size: 16px; /* 字体大小 */
          color: black; /* 黑色文本 */
          transition: color 0.3s; /* 添加过渡效果 */

          &:hover {
            color: $xtxColor; /* 悬停时改变颜色 */
          }
        }
      }
  
    }
  }

  .logo {
    a {
      width: 120px; /* 设置 logo 宽度 */
      font-size: 24px; /* Logo字体大小 */
      font-weight: bold; /* 加粗 */
      color: #333; /* Logo颜色 */
      //background: url("@/assets/images/logo.png") no-repeat right 2px;
      background-size: 160px auto;
    }
  }
}
</style>
