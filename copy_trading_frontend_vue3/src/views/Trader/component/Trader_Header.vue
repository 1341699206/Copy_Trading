<template>
  <header class="app-header">
    <div class="container">
      <!-- Logo部分 -->
      <div class="logo">
        <router-link to="/">ZuluTrade</router-link>
      </div>

      <!-- 中间的导航部分 -->
      <nav class="nav-menu">
        <ul>
          <li><router-link to="/traders">Traders</router-link></li>
          <li><router-link to="/combos">Combos</router-link></li>
          <li><router-link to="/markets">Markets</router-link></li>
          <li><router-link to="/learn">Learn</router-link></li>
          <li><router-link to="/about">About</router-link></li>
          <li><router-link to="/social-feed">Social Feed <span class="new-label">New</span></router-link></li>
        </ul>
      </nav>

      <!-- 右边的账户部分 -->
      <div class="account-section">
        <div class="icon-container">
          <!-- 图标和国家标志 -->
          <i class="iconFont icon-phone"></i>
          <i class="iconFont icon-calendar"></i>
          <i class="iconFont icon-flag"></i>
        </div>
        <div class="user-account">
          <span>My Account</span>
          <span class="username">ethanrosekk</span>
          <i class="iconFont icon-user"></i>
        </div>
      </div>
    </div>
  </header>
</template>

<script setup>
// 不涉及复杂逻辑，暂时不需要script部分
</script>

<style scoped>
/* 整个Header的样式 */
.app-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: white;
  padding: 10px 20px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

/* container 用于限制页面宽度 */
.container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

/* Logo样式 */
.logo {
  font-size: 24px;
  font-weight: bold;
  color: #ff7300; /* ZuluTrade标志的橙色 */
}

.nav-menu ul {
  display: flex;
  list-style-type: none;
  margin: 0;
  padding: 0;
}

.nav-menu ul li {
  margin-right: 20px;
}

.nav-menu ul li a {
  color: #333;
  font-size: 16px;
  text-decoration: none;
  transition: color 0.3s ease;
}

.nav-menu ul li a:hover {
  color: #ff7300; /* hover时的橙色 */
}

/* “New” 标签样式 */
.new-label {
  background-color: #ff7300;
  color: white;
  font-size: 10px;
  padding: 2px 5px;
  border-radius: 3px;
  margin-left: 5px;
}

/* 右边账户部分 */
.account-section {
  display: flex;
  align-items: center;
}

.icon-container {
  display: flex;
  align-items: center;
}

.icon-container i {
  margin-right: 15px;
  font-size: 20px;
}

.user-account {
  display: flex;
  align-items: center;
  font-size: 14px;
}

.username {
  margin-left: 10px;
  margin-right: 5px;
  font-weight: bold;
}

.icon-user {
  font-size: 18px;
  margin-left: 5px;
}
</style>
