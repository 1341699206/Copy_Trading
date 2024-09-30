<script setup>
  import {useScroll} from '@vueuse/core'
  const { y } = useScroll(window)
  
  import {useUserStore} from '@/stores/user'
  const userStore=useUserStore()
</script>

<template>
  <div class="app-header-sticky" :class="{show: y > 78 }">
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
            <li><a href="javascript:;"><i class="iconfont icon-user"></i>{{userStore.userInfo.user.name}}</a></li>
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


<style scoped lang='scss'>
.app-header-sticky {
  width: 100%;
  height: 80px;
  position: fixed;
  left: 0;
  top: 0;
  z-index: 999;
  background-color: #fff;
  border-bottom: 1px solid #e4e4e4;
  // 此处为关键样式!!!
  // 状态一：往上平移自身高度 + 完全透明
  transform: translateY(-100%);
  opacity: 0;

  // 状态二：移除平移 + 完全不透明
  &.show {
    transition: all 0.3s linear;
    transform: none;
    opacity: 1;
  }

  .container {
    display: flex;
    justify-content: space-between; /* 左右两侧对齐 */
    align-items: center; /* 垂直居中 */
    padding: 0 20px; /* 左右内边距 */
  }

  .left {
    ul {
      display: flex; /* 横排显示 */
      align-items: center; 
      margin-left: 20px; /*使所有左侧标签向右侧移动。*/
      padding: 0; /* 去掉内边距 */
      list-style: none; /* 去掉点 */

      li {
        margin-right: 80px; /* 间距 */
        padding: 20px 0; /* 上下距离统一 */
        
        .logo {
          font-size: 24px; /* Logo字体大小 */
          font-weight: bold; /* 加粗 */
          color: #333; /* Logo颜色 */
          width: 120px; /* 设置 logo 宽度 */
          //background: url("@/assets/images/logo.png") no-repeat right 2px;
          background-size: 160px auto;
        }

        a {
          text-decoration: none; /* 去掉下划线 */
          color: black;
          font-size: 18px;
          line-height: 1;

           &:hover {
            color: $xtxColor; /* 悬停时改变颜色 */
          }
        }
      }
    }
  }

  .right {
    width: 220px;
    display: flex;
    text-align: center;
    padding-left: 40px;
    border-left: 2px solid $xtxColor;

    a {
      width: 38px;
      margin-right: 40px;
      font-size: 16px;
      line-height: 1;

      &:hover {
        color: $xtxColor;
      }
    }
  }
}

  .right {
    ul {
      display: flex; /* 横排显示 */
      align-items: center;
      padding: 0; /* 去掉内边距 */
      list-style: none; /* 去掉点 */

      li {
        margin-left: 30px; /* 间距 */
        padding: 20px 0; /* 上下距离统一 */

        a {
          text-decoration: none; /* 去掉下划线 */
          font-size: 16px;
          color: black;
          line-height: 1;

          &:hover {
            color: $xtxColor; /* 悬停时改变颜色 */
          }
        }
      }
    }

  }
</style>