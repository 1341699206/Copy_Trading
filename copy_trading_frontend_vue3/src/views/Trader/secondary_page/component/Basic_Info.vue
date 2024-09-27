<template>
  <div class="basic-info-container">
    <!-- Trader头像和账户信息 -->
    <div class="trader-info">
      <img src="path_to_avatar" alt="Trader Avatar" class="avatar"/>
      <div class="trader-details">
        <h2>JQ Ethan</h2>
        <p>ETHANROSEKK</p>
        <select class="trader-type">
          <option value="Trader">Trader</option>
          <!-- 其他选项 -->
        </select>
      </div>
    </div>

    <!-- Trader的资金和状态信息 -->
    <div class="trader-stats">
      <div class="stat-item">
        <p>Amount Following</p>
        <h3>-</h3>
      </div>
      <div class="stat-item">
        <p>Investors</p>
        <h3>0</h3>
      </div>
      <div class="stat-item">
        <p>Rank (All)</p>
        <h3>-</h3>
      </div>
      <div class="stat-item">
        <p>Rank (Europe)</p>
        <h3>-</h3>
      </div>
      <div class="stat-item">
        <p>Rank (USA)</p>
        <h3>-</h3>
      </div>
      <div class="stat-item">
        <p>Rank (Japan)</p>
        <h3>-</h3>
      </div>
      <div class="stat-item">
        <p>Equity</p>
        <h3>$5,000.00</h3>
      </div>
      <div class="stat-item">
        <p>Balance</p>
        <h3>$5,000.00</h3>
      </div>
      <div class="stat-item">
        <p>Realized PNL</p>
        <h3>$0.00</h3>
      </div>
      <div class="stat-item">
        <p>Margin</p>
        <h3>0.00% (0%)</h3>
      </div>
      <div class="stat-item">
        <p>Free Margin</p>
        <h3>$5,000.00</h3>
      </div>
      <button class="funds-button">Funds</button>
    </div>
  </div>
</template>

<script setup>
</script>

<style scoped>
.basic-info-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
}

.trader-info {
  display: flex;
  align-items: center;
}

.avatar {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  margin-right: 20px;
}

.trader-details h2 {
  margin: 0;
  font-size: 24px;
}

.trader-details p {
  margin: 5px 0;
  font-size: 18px;
}

.trader-type {
  margin-top: 5px;
}

.trader-stats {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  align-items: center;
}

.stat-item {
  text-align: center;
}

.stat-item h3 {
  font-size: 18px;
  margin: 0;
}

.funds-button {
  background-color: #ff9f00;
  color: white;
  border: none;
  padding: 10px 15px;
  cursor: pointer;
  border-radius: 5px;
}

.funds-button:hover {
  background-color: #ff8500;
}
</style>
