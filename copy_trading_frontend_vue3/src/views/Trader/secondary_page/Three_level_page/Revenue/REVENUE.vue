// Create a Revenue subpage Vue component
// This component is designed to implement a "Revenue" subpage similar to ZuluTrade.
// The page will include four tabs: Revenue, Subscribed Users, Rejected Trades, and Payments.

<template>
  <div class="revenue-page">
    <!-- Tabs for navigation -->
    <div class="tabs">
      <button 
        v-for="tab in tabs" 
        :key="tab" 
        @click="activeTab = tab"
        :class="{ active: activeTab === tab }"
      >
        {{ tab }}
      </button>
    </div>

    <!-- Tab Content -->
    <div v-if="activeTab === 'Revenue'" class="tab-content">
      <h2>Compensation Methods</h2>
      <div class="compensation-methods">
        <div>Classic (Volume Based): 0.5 pips/lot</div>
        <div>Profit Sharing: 20%</div>
        <div>Payment - Reserve Ratio: 50% - 0%</div>
        <div>U.S. Commissions: 70%</div>
        <div>Other Commissions: Stock CFDs: 0.01% per side</div>
      </div>

      <h2>Revenue Summary</h2>
      <div class="revenue-summary">
        <div>Total Pending: $0.00</div>
        <div>Total Outstanding: $0.00</div>
        <a href="#" class="read-more">Read more about the payment calculation</a>
      </div>

      <h2>Available Payment</h2>
      <div class="available-payment">
        <div>Payable Amount: $0.00 (until 2024-10-01)</div>
        <p>Attention: Your Account details have not been verified. Until your Account Verification has been completed, you will not be able to request payment.</p>
        <button>Enable AutoPay</button>
      </div>

      <h2>Revenues</h2>
      <div class="revenues">
        <div class="revenue-filters">
          <label>From date
            <input type="date" />
          </label>
          <label>To date
            <input type="date" />
          </label>
          <div class="status-filters">
            <label><input type="radio" name="status" /> Outstanding</label>
            <label><input type="radio" name="status" /> Pending</label>
            <label><input type="radio" name="status" /> Paid</label>
            <label><input type="radio" name="status" /> Declined</label>
          </div>
          <button>Show Revenues</button>
          <button>Export To Excel</button>
        </div>
        <table class="revenue-table">
          <thead>
            <tr>
              <th>Account Number</th>
              <th>Commission Type</th>
              <th>Buy/Sell</th>
              <th>Currency</th>
              <th>Lots (Standard)</th>
              <th>Date Open</th>
              <th>Date Closed</th>
              <th>Profit</th>
              <th>Commission</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td colspan="9" class="no-data">No data available in table</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div v-if="activeTab === 'Subscribed Users'" class="tab-content">
      <h2>Subscribed Users</h2>
      <div class="subscribed-users">
        <div class="filters">
          <label>Broker
            <select>
              <option>Select</option>
            </select>
          </label>
          <label>Account Number
            <input type="text" />
          </label>
          <label>Country of Residence
            <select>
              <option>Select</option>
            </select>
          </label>
          <label>
            <input type="checkbox" /> Receiving Signals
          </label>
          <label>
            <input type="checkbox" /> Not Receiving Trades
          </label>
          <button>Search</button>
          <button>Export To Excel</button>
          <button>Reset</button>
        </div>
        <div class="no-users">There are no subscribed users</div>
      </div>
    </div>

    <div v-if="activeTab === 'Rejected Trades'" class="tab-content">
      <h2>Rejected Signals</h2>
      <p>This table shows the signals sent to your subscribed users during last week that for some reason were rejected.</p>
      <p>Please, be especially aware of rejected stop/limit update signals as such rejections may cause unwanted results at your client's trades.</p>
      <div class="no-rejected">There are no rejected signals</div>
    </div>

    <div v-if="activeTab === 'Payments'" class="tab-content">
      <h2>Payment Summary For All Accounts</h2>
      <div class="payment-summary">
        <div>Total Pending: $0.00</div>
        <div>Paid (Profit Sharing): $0.00</div>
        <div>Total Paid: $0.00</div>
      </div>

      <h2>Payment History</h2>
      <div class="no-payments">No Payments Found</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "RevenuePage",
  data() {
    return {
      tabs: ['Revenue', 'Subscribed Users', 'Rejected Trades', 'Payments'],
      activeTab: 'Revenue',
    };
  },
};
</script>

<style scoped>
.revenue-page {
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
  background-color: #f0f0f0;
  cursor: pointer;
  border-radius: 5px;
}

.tabs button.active {
  background-color: #007bff;
  color: white;
}

.tab-content {
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.compensation-methods div,
.revenue-summary div,
.available-payment div,
.payment-summary div {
  margin-bottom: 10px;
}

.filters {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-bottom: 20px;
}

.filters label {
  display: flex;
  flex-direction: column;
}

.status-filters {
  display: flex;
  gap: 10px;
  align-items: center;
}

.revenue-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.revenue-table th, .revenue-table td {
  border: 1px solid #ccc;
  padding: 10px;
  text-align: left;
}

.no-users,
.no-rejected,
.no-payments,
.no-data {
  margin-top: 20px;
  color: #666;
  text-align: center;
}

.read-more {
  color: #007bff;
  text-decoration: none;
  cursor: pointer;
}

.read-more:hover {
  text-decoration: underline;
}
</style>
