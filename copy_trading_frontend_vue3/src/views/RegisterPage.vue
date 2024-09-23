<template>
  <div class="register">
    <h1>Register</h1>
    <form @submit.prevent="register">
      <!-- 输入 Name -->
      <div>
        <label for="name">Name</label>
        <input type="text" v-model="name" id="name" required />
      </div>

      <!-- 输入 Email -->
      <div>
        <label for="email">Email</label>
        <input type="email" v-model="email" id="email" required />
      </div>

      <!-- 输入密码 -->
      <div>
        <label for="password">Password</label>
        <input type="password" v-model="password" id="password" required />
      </div>

      <!-- 选择注册角色：Trader 或 Follower -->
      <div>
        <label for="role">Select Role</label>
        <select v-model="selectedRole" id="role" required>
          <option value="TRADER">Trader</option>
          <option value="FOLLOWER">Follower</option>
        </select>
      </div>

      <!-- 选择国家，存储国家 ISO 代码 -->
      <div>
        <label for="country">Select Country</label>
        <select v-model="selectedCountry" id="country" required>
          <option v-for="country in countries" :key="country.code" :value="country.code">
            {{ country.name }}
          </option>
        </select>
      </div>

      <button type="submit">Register</button>

      <!-- 显示注册成功或失败的消息 -->
      <p v-if="successMessage" class="success">{{ successMessage }}</p>
      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    </form>

    <!-- 返回到登录页面的按钮 -->
    <button @click="goToLogin">Already have an account? Login here</button>
  </div>
</template>

<script>
export default {
  name: "RegisterPage",
  data() {
    return {
      name: "",
      email: "",
      password: "",
      selectedRole: "TRADER",  // 默认选中Trader
      selectedCountry: "",  // 存储国家 ISO 代码
      countries: [  // 国家列表（可以根据需要扩展或从 API 获取）
        { name: 'China', code: 'CN' },
        { name: 'Germany', code: 'DE' },
        { name: 'United States', code: 'US' },      
        { name: 'United Kingdom', code: 'GB' },
        // 添加更多国家
      ],
      successMessage: "",
      errorMessage: ""
    };
  },
  methods: {
    register() {
      // 创建一个包含注册信息的对象
      const newUser = {
        name: this.name,
        email: this.email,
        password: this.password,
        role: this.selectedRole,
        country: this.selectedCountry
      };

      // 发送 POST 请求到后端注册 API
      this.$axios
        .post("/api/register", newUser)
        .then(() => {
          // 注册成功后，显示成功消息
          this.successMessage = "Registration successful!";
          this.errorMessage = "";

          // 自动跳转到登录页面
          this.$router.push('/login');
        })
        .catch((error) => {
          // 请求失败后，显示错误消息
          this.successMessage = "";
          this.errorMessage = error.response?.data?.message || "Registration failed.";
        });
    },
    
    // 跳转到登录页面
    goToLogin() {
      this.$router.push('/login');
    }
  }
};
</script>

<style scoped>
/* 简单的样式 */
.success {
  color: green;
}

.error {
  color: red;
}

button {
  margin-top: 10px;
  color: blue;
  background: none;
  border: none;
  cursor: pointer;
  text-decoration: underline;
}

button:hover {
  text-decoration: none;
}
</style>
