// Create a History subpage Vue component
// This component is designed to implement a "History" subpage similar to ZuluTrade.
// The page will include basic features like date filtering, currency pair selection, and trade type filtering, along with tabs to switch between different history views.

<template>
  <div class="history-page">
    <!-- Title -->
    <h1>Trade History</h1>

    <!-- Tabs for Different History Views -->
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
    <div v-if="activeTab === 'Trade History Log'">
      <!-- Trade History Log Content -->
      <div class="filters">
        <div class="filter-item">
          <label for="from-date">From Date:</label>
          <input type="date" id="from-date" v-model="filters.fromDate" />
        </div>
        <div class="filter-item">
          <label for="to-date">To Date:</label>
          <input type="date" id="to-date" v-model="filters.toDate" />
        </div>
        <div class="filter-item">
          <label for="currency">Currency Pair:</label>
          <select id="currency" v-model="filters.currency">
            <option value="">Select Currency Pair</option>
            <option value="EUR/USD">EUR/USD</option>
            <option value="GBP/USD">GBP/USD</option>
            <option value="USD/JPY">USD/JPY</option>
          </select>
        </div>
        <div class="filter-item">
          <label for="trade-type">Trade Type:</label>
          <select id="trade-type" v-model="filters.tradeType">
            <option value="">All</option>
            <option value="buy">Buy</option>
            <option value="sell">Sell</option>
          </select>
        </div>
      </div>

      <!-- Trade History Table -->
      <div class="history-table">
        <table>
          <thead>
            <tr>
              <th>Date</th>
              <th>Currency Pair</th>
              <th>Type</th>
              <th>Amount</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            <tr v-if="filteredTrades.length === 0">
              <td colspan="5">No trades found using the specified filters.</td>
            </tr>
            <tr v-for="(trade, index) in filteredTrades" :key="index">
              <td>{{ trade.date }}</td>
              <td>{{ trade.currency }}</td>
              <td>{{ trade.type }}</td>
              <td>{{ trade.amount }}</td>
              <td>{{ trade.status }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div v-if="activeTab === 'Cancelled Orders Log'">
      <!-- Cancelled Orders Log Content -->
      <p>No cancelled orders available.</p>
    </div>

    <div v-if="activeTab === 'Other History'">
      <!-- Other History Content -->
      <p>No other history records available.</p>
    </div>
  </div>
</template>

<script>
export default {
  name: "HistoryPage",
  data() {
    return {
      // Tabs for switching between different views
      tabs: ["Trade History Log", "Cancelled Orders Log", "Other History"],
      activeTab: "Trade History Log",
      // Filter conditions
      filters: {
        fromDate: "",
        toDate: "",
        currency: "",
        tradeType: "",
      },
      // Sample trade data
      trades: [
        {
          date: "2024-10-01",
          currency: "EUR/USD",
          type: "buy",
          amount: 1000,
          status: "closed",
        },
        {
          date: "2024-10-15",
          currency: "GBP/USD",
          type: "sell",
          amount: 2000,
          status: "open",
        },
        {
          date: "2024-10-20",
          currency: "USD/JPY",
          type: "buy",
          amount: 1500,
          status: "closed",
        },
      ],
    };
  },
  computed: {
    // Filter trades based on the conditions
    filteredTrades() {
      return this.trades.filter((trade) => {
        const matchesDate =
          (!this.filters.fromDate || trade.date >= this.filters.fromDate) &&
          (!this.filters.toDate || trade.date <= this.filters.toDate);
        const matchesCurrency =
          !this.filters.currency || trade.currency === this.filters.currency;
        const matchesTradeType =
          !this.filters.tradeType || trade.type === this.filters.tradeType;

        return matchesDate && matchesCurrency && matchesTradeType;
      });
    },
  },
};
</script>

<style scoped>
.history-page {
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

.filters {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
}

.filter-item {
  display: flex;
  flex-direction: column;
}

.history-table {
  margin-top: 20px;
}

.history-table table {
  width: 100%;
  border-collapse: collapse;
}

.history-table th,
.history-table td {
  border: 1px solid #ccc;
  padding: 10px;
  text-align: left;
}

.history-table th {
  background-color: #f8f8f8;
}
</style>
