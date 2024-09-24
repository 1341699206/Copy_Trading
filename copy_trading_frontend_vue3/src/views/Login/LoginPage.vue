<template>
  <div class="login">
    <h1>Login</h1>
    <form @submit.prevent="login"> <!-- 表单提交触发 login 方法 -->
      <div>
        <label for="email">Email</label>
        <input type="email" v-model="email" id="email" required />
      </div>

      <div>
        <label for="password">Password</label>
        <input type="password" v-model="password" id="password" required />
      </div>

      <div>
        <label for="role">Select Role</label>
        <select v-model="selectedRole" id="role" required>
          <option value="TRADER">Trader</option>
          <option value="FOLLOWER">Follower</option>
          <option value="ADMIN">Admin</option>
        </select>
      </div>
      <button type="submit">Login</button>

      <!-- 显示登录成功或失败的消息 -->
      <p v-if="successMessage" class="success">{{ successMessage }}</p>
      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    </form>

    <!-- 跳转到注册页面的按钮 -->
    <button @click="goToRegister">Don't have an account? Register here</button>
  </div>
</template>

<script>
export default {
  name: "LoginPage",
  data() {
    return {
      email: "",  // email 字段
      password: "",
      selectedRole: "TRADER",  // 默认选中 Trader
      successMessage: "",
      errorMessage: ""  // 错误消息
    };
  },
  methods: {
    login() {
      // 创建一个包含登录信息的对象
      const credentials = {
        email: this.email,
        password: this.password,
        role: this.selectedRole
      };

      // 发送 POST 请求到后端登录 API
      this.$axios
        .post("/api/login", credentials)
        .then((response) => {
          // 登录成功后，显示成功消息
          this.successMessage = response.data || "Login successful!";
          this.errorMessage = "";

          // 根据用户选择的角色跳转到相应的页面
          if (this.selectedRole === "TRADER") {
            this.$router.push('/trader-dashboard');
          } else if (this.selectedRole === "FOLLOWER") {
            this.$router.push('/follower-dashboard');
          } else if (this.selectedRole === "ADMIN") {
            this.$router.push('/admin-dashboard');
          }
        })
        .catch((error) => {
          // 请求失败后，显示错误消息
          this.successMessage = "";
          if (error.response) {
            // 捕获后端返回的错误消息
            this.errorMessage = error.response.data || "Login failed: Invalid credentials.";
          } else {
            this.errorMessage = "Login failed: Unable to reach the server.";
          }
        });
    },

    // 新增 goToRegister 方法，用于跳转到注册页面
    goToRegister() {
      this.$router.push('/register');  // 跳转到注册页面
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
