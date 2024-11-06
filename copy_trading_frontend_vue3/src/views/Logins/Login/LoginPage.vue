<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import 'element-plus/theme-chalk/el-message.css'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { connectWebSocket } from '@/service/websocket'

const userStore = useUserStore()
const userInfo = ref({
  email: '',
  password: '',
  role: 'Follower',
})
const formRef = ref(null)
const router = useRouter()

const rules = {
  email: [
    { required: true, message: 'Email cannot be empty' }
  ],
  password: [
    { required: true, message: 'Password cannot be empty.' },
    { min: 6, max: 14, message: 'Password length requires 6-14 characters' }
  ],
  role: [
    { required: true }
  ],
}

const doLogin = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        userInfo.value.role = userInfo.value.role.toUpperCase()
        await userStore.getUserInfo(userInfo.value) // 登录并存储用户信息
        ElMessage({ type: 'success', message: 'Login successful!' })

        // 根据角色跳转页面 并连接 WebSocket
        if (userInfo.value.role === 'TRADER') {
          router.replace('/trader_page')
          connectWebSocket(userInfo.value.role + ':' + userStore.userInfo.user.traderId)
        } else if (userInfo.value.role === 'FOLLOWER') {
          router.replace('/followerDashboard')
          connectWebSocket(userInfo.value.role + ':' + userStore.userInfo.user.followerId)
        } else if (userInfo.value.role === 'ADMIN') {
          router.replace('/admin')
          connectWebSocket(userInfo.value.role + ':' + userStore.userInfo.user.adminId)
        }
      } catch (error) {
        ElMessage({ type: 'error', message: 'Login failed. Please check your credentials.' })
        console.error("Login error:", error)
      }
    } else {
      ElMessage({ type: 'error', message: 'Please fill out the form correctly!' })
    }
  })
}
</script>

<template>
  <section class="login-section">
    <div class="wrapper">
      <nav>
        <h1>Log in</h1>
      </nav>
      <div class="account-box">
        <div class="form">
          <el-form ref="formRef" :model="userInfo" :rules="rules" label-position="right" label-width="60px" status-icon>
            <el-form-item prop="email" label="email">
              <el-input v-model="userInfo.email" />
            </el-form-item>
            <el-form-item prop="password" label="password">
              <el-input type="password" v-model="userInfo.password" />
            </el-form-item>
            <el-form-item prop="role" label="role">
              <el-select v-model="userInfo.role" placeholder="role">
                <el-option value="Trader">Trader</el-option>
                <el-option value="Follower">Follower</el-option>
                <el-option value="Admin">Admin</el-option>
              </el-select>
            </el-form-item>
            <el-button class="loginB" @click="doLogin">login</el-button>
            <el-button class="registerB" @click="$router.push('/register')">register</el-button>
          </el-form>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped lang="scss">
.login-section {
  height: 488px;
  position: relative;
  .wrapper {
    width: 380px;
    background: #fff;
    position: absolute;
    left: 50%;
    top: 24%;
    transform: translate3d(100px, 0, 0);
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);
    nav {
      font-size: 14px;
      height: 55px;
      margin-bottom: 20px;
      border-bottom: 1px solid #f5f5f5;
      display: flex;
      padding: 0 40px;
      text-align: right;
      align-items: center;
      h1 {
        flex: 1;
        line-height: 1;
        display: inline-block;
        font-size: 18px;
        position: relative;
        text-align: center;
      }
    }
  }
}
.account-box {
  .form {
    padding: 0 20px 20px 20px;
    &-item {
      margin-bottom: 28px;
    }
  }
}
.loginB {
  background: #409eff;
  width: 40%;
  color: #fff;
}
.registerB {
  width: 40%;
}
</style>
