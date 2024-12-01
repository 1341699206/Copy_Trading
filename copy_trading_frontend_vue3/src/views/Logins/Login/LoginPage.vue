//a
<script setup>
import { ref } from 'vue';
import { ElMessage } from 'element-plus';
import { useUserStore } from '@/stores/user'
import 'element-plus/theme-chalk/el-message.css';
import { useRouter } from 'vue-router'; 

const userStore = useUserStore();

// 用户注册信息
const userInfo = ref({
  username: '',
  password: '',
});

// 规则数据对象
const rules = {
  username: [
    { required: true, message: 'Name cannot be empty' },
    { min: 4, max: 16, message: 'Name length required 4-16 characters' }
  ],
  password: [
    { required: true, message: 'Password cannot be empty.' },
    { min: 6, max: 14, message: 'Password length requires 6-14 characters' }
  ]
};

const formRef = ref(null);
const router = useRouter();

//登录函数
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
  <div class="register">
    <h1>Log in</h1>

    <el-form ref="formRef" :model="userInfo" :rules="rules" label-position="top" label-width="60px" status-icon>
      <!-- 输入 Name -->
      <el-form-item label="username" prop="username"> 
        <el-input v-model="userInfo.username" id="username" />
      </el-form-item>

      <!-- 输入密码 -->
      <el-form-item label="password" prop="password"> 
        <el-input type="password" v-model="userInfo.password" id="password" />
      </el-form-item>

      <!-- 登录按钮 -->
      <el-button @click="doLogin">login</el-button>
      <el-button @click="$router.push('/register')">register</el-button>

    </el-form>

  </div>
</template>

<style scoped lang="scss">
.el-form {
  max-width: 400px;
  margin: 0 auto;
}
</style>