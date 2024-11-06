// Create a Positions subpage Vue component
// This component is designed to implement a "Positions" subpage similar to ZuluTrade.
// The page will include tabs for switching between "Open Positions" and "Orders" views, each displaying relevant trade data.

<template>
  <div class="positions-page">
    <!-- Title -->
    <h1>Positions</h1>

    <!-- Tabs for Different Views -->
    <div class="tabs">
      <button 
        v-for="(tab, index) in tabs" 
        :key="index" 
        :class="{ active: activeTab === tab }" 
        @click="activeTab = tab">
        {{ tab }}
      </button>
    </div>

    <!-- Content Based on Active Tab -->
    <div v-if="activeTab === 'Open Positions'">
      <!-- Open Positions Content -->
      <div class="overview">
        <p>
          <strong>${{ totalProfit }}</strong> PROFIT
        </p>
        <p>{{ openPositions.length }} OPEN POSITIONS</p>
        <p>{{ totalLots }} STD LOTS</p>
      </div>

      <!-- Open Positions Table -->
      <div class="positions-table">
        <table>
          <thead>
            <tr>
              <th>Trader</th>
              <th>Currency</th>
              <th>Type</th>
              <th>Std Lots</th>
              <th>Date Opened</th>
              <th>Entry</th>
              <th>Stop</th>
              <th>Limit</th>
              <th>Current</th>
              <th>Profit</th>
            </tr>
          </thead>
          <tbody>
            <tr v-if="openPositions.length === 0">
              <td colspan="10">You don't have any open positions.</td>
            </tr>
            <tr v-for="(position, index) in openPositions" :key="index">
              <td>{{ position.trader }}</td>
              <td>{{ position.currency }}</td>
              <td>{{ position.type }}</td>
              <td>{{ position.stdLots }}</td>
              <td>{{ position.dateOpened }}</td>
              <td>{{ position.entry }}</td>
              <td>{{ position.stop }}</td>
              <td>{{ position.limit }}</td>
              <td>{{ position.current }}</td>
              <td>{{ position.profit }}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <button @click="closeAllPositions" class="close-button">Close All Open Positions</button>
    </div>

    <div v-if="activeTab === 'Orders'">
      <!-- Orders Content -->
      <div class="overview">
        <p>{{ orders.length }} ORDERS</p>
      </div>

      <!-- Orders Table -->
      <div class="orders-table">
        <table>
          <thead>
            <tr>
              <th>Trader</th>
              <th>Currency</th>
              <th>Type</th>
              <th>Std Lots</th>
              <th>Date Opened</th>
              <th>Entry</th>
              <th>Stop</th>
              <th>Limit</th>
              <th>Current</th>
            </tr>
          </thead>
          <tbody>
            <tr v-if="orders.length === 0">
              <td colspan="9">You don't have any pending orders.</td>
            </tr>
            <tr v-for="(order, index) in orders" :key="index">
              <td>{{ order.trader }}</td>
              <td>{{ order.currency }}</td>
              <td>{{ order.type }}</td>
              <td>{{ order.stdLots }}</td>
              <td>{{ order.dateOpened }}</td>
              <td>{{ order.entry }}</td>
              <td>{{ order.stop }}</td>
              <td>{{ order.limit }}</td>
              <td>{{ order.current }}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <button @click="cancelAllOrders" class="cancel-button">Cancel All Pending Orders</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "PositionsPage",
  data() {
    return {
      // Tabs for switching between different views
      tabs: ["Open Positions", "Orders"],
      activeTab: "Open Positions",
      // Sample open positions data
      openPositions: [
        {
          trader: "Trader A",
          currency: "EUR/USD",
          type: "buy",
          stdLots: 1,
          dateOpened: "2024-10-15",
          entry: 1.1050,
          stop: 1.1000,
          limit: 1.1200,
          current: 1.1100,
          profit: 50,
        },
      ],
      // Sample orders data
      orders: [
        {
          trader: "Trader B",
          currency: "GBP/USD",
          type: "sell",
          stdLots: 2,
          dateOpened: "2024-10-18",
          entry: 1.3100,
          stop: 1.3200,
          limit: 1.2900,
          current: 1.3050,
        },
      ],
    };
  },
  computed: {
    // Calculate total profit for open positions
    totalProfit() {
      return this.openPositions.reduce((acc, position) => acc + position.profit, 0).toFixed(2);
    },
    // Calculate total lots for open positions
    totalLots() {
      return this.openPositions.reduce((acc, position) => acc + position.stdLots, 0);
    },
  },
  methods: {
    closeAllPositions() {
      alert("All open positions have been closed.");
    },
    cancelAllOrders() {
      alert("All pending orders have been cancelled.");
    },
  },
};
</script>

<style scoped>
.positions-page {
  padding: 20px;
}

.tabs {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.tabs button {
  padding: 10px 20px;
  border: none;
  cursor: pointer;
  background-color: #f0f0f0;
  transition: background-color 0.3s;
}

.tabs button.active {
  background-color: #007bff;
  color: #fff;
}

.tabs button:hover:not(.active) {
  background-color: #e0e0e0;
}

.overview {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
  font-weight: bold;
}

.positions-table,
.orders-table {
  margin-top: 20px;
}

.positions-table table,
.orders-table table {
  width: 100%;
  border-collapse: collapse;
}

.positions-table th,
.positions-table td,
.orders-table th,
.orders-table td {
  border: 1px solid #ccc;
  padding: 10px;
  text-align: left;
}

.positions-table th,
.orders-table th {
  background-color: #f8f8f8;
}

.close-button,
.cancel-button {
  margin-top: 20px;
  padding: 10px 20px;
  background-color: #d9534f;
  color: white;
  border: none;
  cursor: pointer;
}

.close-button:hover,
.cancel-button:hover {
  background-color: #c9302c;
}
</style>
