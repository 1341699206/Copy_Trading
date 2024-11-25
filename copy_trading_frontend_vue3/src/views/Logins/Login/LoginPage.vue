//a 
<script setup>
import { ref } from 'vue';
import { ElMessage } from 'element-plus';
import 'element-plus/theme-chalk/el-message.css';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/user';

const userStore = useUserStore();
const userInfo = ref({
  username: '',
  password: '',
});
const formRef = ref(null);
const router = useRouter();

const rules = {
  username: [{ required: true, message: 'Username cannot be empty' }],
  password: [
    { required: true, message: 'Password cannot be empty.' },
    { min: 6, max: 14, message: 'Password length requires 6-14 characters' },
  ],
};

const doLogin = async () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        // 调用 userStore 的登录方法
        await userStore.getUserInfo({ 
          username: userInfo.value.username, 
          password: userInfo.value.password 
        });
        ElMessage({ type: 'success', message: 'Login successful!' });

        // 根据角色跳转页面并连接 WebSocket
        const { role } = userStore.userInfo;
        if (role === 'TRADER') {
          router.replace('/trader_page');
        } else if (role === 'FOLLOWER') {
          router.replace('/followerDashboard');
        } else if (role === 'ADMIN') {
          router.replace('/admin');
        }
      } catch (error) {
        ElMessage({ type: 'error', message: 'Login failed. Please check your credentials.' });
        console.error('Login error:', error);
      }
    } else {
      ElMessage({ type: 'error', message: 'Please fill out the form correctly!' });
    }
  });
};
</script>

<template>
  <section class="login-section">
    <div class="wrapper">
      <nav>
        <h1>Log in</h1>
      </nav>
      <div class="account-box">
        <div class="form">
          <el-form
            ref="formRef"
            :model="userInfo"
            :rules="rules"
            label-position="right"
            label-width="60px"
            status-icon
          >
            <el-form-item prop="username" label="username">
              <el-input v-model="userInfo.username" />
            </el-form-item>
            <el-form-item prop="password" label="password">
              <el-input type="password" v-model="userInfo.password" />
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
