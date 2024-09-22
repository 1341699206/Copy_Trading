<template>
  <div class="login">
    <h1>Login</h1>
    <form @submit.prevent="login"> <!-- 表单提交触发 login 方法 -->
      <div>
        <label for="username">Username</label>
        <input type="text" v-model="username" id="username" required />
      </div>

      <div>
        <label for="password">Password</label>
        <input type="password" v-model="password" id="password" required />
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
      username: "",
      password: "",
      successMessage: "",
      errorMessage: ""
    };
  },
  methods: {
    login() {
      // 创建一个包含登录信息的对象
      const credentials = {
        username: this.username,
        password: this.password
      };

      // 发送 POST 请求到后端登录 API
      this.$axios
        .post("/api/login", credentials)
        .then(() => {
          // 请求成功后，显示成功消息
          this.successMessage = "Login successful!";
          this.errorMessage = ""; // 清空错误消息

          // 如果需要，可以在这里将用户重定向到其他页面
          this.$router.push('/dashboard');
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
