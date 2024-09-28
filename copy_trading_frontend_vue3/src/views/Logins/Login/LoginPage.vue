<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import 'element-plus/theme-chalk/el-message.css'
import { useRouter } from 'vue-router'
import {useUserStore} from '@/stores/user'

const userStore =useUserStore()

// 表单对象
const userInfo = ref({
  email: '',
  password: '',
  role: 'Follower',
})

// 规则数据对象
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

const formRef = ref(null)
const router = useRouter()

// 登录函数
const doLogin = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        // 将 role 转换为大写字母，以匹配后端要求
        userInfo.value.role = userInfo.value.role.toUpperCase()

        // 调用 loginAPI，并确保传递的是 userInfo.value
        await userStore.getUserInfo(userInfo.value)
        ElMessage({ type: 'success', message: 'Login successful!' })

        // 根据角色跳转页面
        if (userInfo.value.role === 'TRADER') {
          router.replace('/trader_page')
        } else if (userInfo.value.role === 'FOLLOWER') {
          router.replace('/followerDashboard')
        } else if (userInfo.value.role === 'ADMIN') {
          router.replace('/admin')
        }
      } catch (error) {
        // 处理登录失败的情况
        ElMessage({ type: 'error', message: 'Login failed: ' + error.response.data })
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
            <!-- 输入邮箱 -->
            <el-form-item prop="email" label="email">
              <el-input v-model="userInfo.email" />
            </el-form-item>

            <!-- 输入密码 -->
            <el-form-item prop="password" label="password">
              <el-input type="password" v-model="userInfo.password" />
            </el-form-item>

            <!-- 选择角色 -->
            <el-form-item prop="role" label="role">
              <el-select v-model="userInfo.role" placeholder="role">
                <el-option value="Trader">Trader</el-option>
                <el-option value="Follower">Follower</el-option>
                <el-option value="Admin">Admin</el-option>
              </el-select>
            </el-form-item>

            <!-- 登录按钮 -->
            <el-button class="loginB" @click="doLogin">login</el-button>
            <!-- 注册按钮 -->
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
