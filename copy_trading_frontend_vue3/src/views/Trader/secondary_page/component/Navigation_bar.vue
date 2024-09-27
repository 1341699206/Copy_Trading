<template>
  <nav class="navigation-bar">
    <ul class="nav-list">
      <li><router-link to="/trader_page/ztp">ZTP</router-link></li>
      <li><router-link to="/trader_page/social-feed">Social Feed</router-link></li>
      <li><router-link to="/trader_page">Account</router-link></li>
      <li><router-link to="/trader_page/settings">Settings</router-link></li>
      <li><router-link to="/trader_page/trade">Trade</router-link></li>
      <li><router-link to="/trader_page/positions">Positions</router-link></li>
      <li><router-link to="/trader_page/history">History</router-link></li>
      <li><router-link to="/trader_page/revenue">Revenue</router-link></li>
      <li><router-link to="/trader_page/invite">Invite</router-link></li>
      <li><router-link to="/trader_page/widgets">Widgets</router-link></li>
    </ul>
  </nav>
</template>

<script setup>
</script>

<style scoped>
.navigation-bar {
  padding: 10px;
  background-color: #f8f9fa;
  border-bottom: 1px solid #ddd;
}

.nav-list {
  display: flex;
  justify-content: space-around;
  list-style: none;
  padding: 0;
}

.nav-list li {
  margin: 0 15px;
}

.nav-list a {
  text-decoration: none;
  color: #333;
  font-size: 14px;
}

.nav-list a.router-link-exact-active {
  color: #ff6b35;
  border-bottom: 2px solid #ff6b35;
}
</style>
