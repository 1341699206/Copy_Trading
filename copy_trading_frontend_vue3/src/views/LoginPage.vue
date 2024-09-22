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
  </div>
</template>

<script>
export default {
  name: "LoginPage",
  data() {
    return {
      email: "",  // 改为 email 字段
      password: "",
      selectedRole: "TRADER",  // 默认选中Trader
      successMessage: "",
      errorMessage: ""
    };
  },
  methods: {
    login() {
      // 创建一个包含登录信息的对象
      const credentials = {
        email: this.email,    // 使用 email 而不是 username
        password: this.password,
        role: this.selectedRole
      };

      // 发送 POST 请求到后端登录 API
      this.$axios
        .post("/api/login", credentials)
        .then(() => {
          // 登录成功后，显示成功消息
          this.successMessage = "Login successful!";
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
          this.errorMessage = error.response?.data?.message || "Login failed.";
        });
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
</style>
