//a
<script setup>
import { ref } from 'vue';
import { register } from '@/apis/authentication';
import { ElMessage } from 'element-plus';
import 'element-plus/theme-chalk/el-message.css';
import { useRouter } from 'vue-router'; 

// 用户注册信息
const userInfo = ref({
  username: '',
  email: '',
  password: '',
  role: 'Follower',
  createdAt:'',
  updatedAt:''
});

// 规则数据对象
const rules = {
  name: [
    { required: true, message: 'Name cannot be empty' },
    { min: 4, max: 16, message: 'Name length required 4-16 characters' }
  ],
  email: [
    { required: true, message: 'Email cannot be empty' }
  ],
  password: [
    { required: true, message: 'Password cannot be empty.' },
    { min: 6, max: 14, message: 'Password length requires 6-14 characters' }
  ],
  role: [
    { required: true }
  ]
};

const formRef = ref(null);
const router = useRouter();

// 注册函数
const doRegister = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        //获取时间
        const now=(new Date()).toISOString();
        userInfo.value.createdAt=now;
        userInfo.value.updatedAt=now;
        // 调用注册 API 并传递 userInfo 的值
        await register(userInfo.value);
        ElMessage({ type: 'success', message: 'Register successful!' });
        // 成功后跳转到登录页面
        router.push('/login');
      } catch (error) {
        // 处理错误
      }
    } else {
      ElMessage({ type: 'error', message: 'Please fill out the form correctly!' });
    }
  });
}
</script>

<template>
  <div class="register">
    <h1>Register</h1>

    <el-form ref="formRef" :model="userInfo" :rules="rules" label-position="top" label-width="60px" status-icon>
      <!-- 输入 Name -->
      <el-form-item label="username" prop="username"> 
        <el-input v-model="userInfo.username" id="username" />
      </el-form-item>

      <!-- 输入 Email -->
      <el-form-item label="email" prop="email"> 
        <el-input v-model="userInfo.email" id="email" />
      </el-form-item>

      <!-- 输入密码 -->
      <el-form-item label="password" prop="password"> 
        <el-input type="password" v-model="userInfo.password" id="password" />
      </el-form-item>

      <!-- 选择注册角色：Trader 或 Follower -->
      <el-form-item prop="role" label="role">
        <el-select v-model="userInfo.role" placeholder="role">
          <el-option value="Trader">Trader</el-option>
          <el-option value="Follower">Follower</el-option>
          <el-option value="Admin">Admin</el-option>
        </el-select>
      </el-form-item>

      <!-- 注册按钮 -->
      <el-button @click="doRegister">Register</el-button>
      <el-button @click="$router.push('/login')">Already have an account? Login here</el-button>

    </el-form>

  </div>
</template>

<style scoped lang="scss">
.el-form {
  max-width: 400px;
  margin: 0 auto;
}
</style>
