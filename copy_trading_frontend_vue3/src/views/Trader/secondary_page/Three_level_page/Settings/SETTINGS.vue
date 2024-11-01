// Create a Settings subpage Vue component
// This component is designed to implement a "Settings" subpage similar to ZuluTrade.
// The page will include four sections: FIFO Settings, Broker Account Link Settings, ZuluTrading API Settings, and Preferred Timezone.

<template>
  <div class="settings-page">
    <!-- FIFO Settings -->
    <div class="settings-section">
      <h2>FIFO Settings</h2>
      <div class="fifo-settings">
        <div class="fifo-policy">
          <input type="checkbox" v-model="fifoEnabled" />
          <label>
            FIFO: Comply with the FIFO and No-Hedging policy and Join the US Market!
          </label>
          <p>
            If you enable this option, your account will also be included in the US Traders Page where only FIFO compliant Traders will appear.
            <a href="#">Read more here.</a>
          </p>
        </div>
        <div class="currency-selection">
          <label>Currency</label>
          <select v-model="selectedCurrencies" multiple>
            <option v-for="currency in currencies" :key="currency" :value="currency">
              {{ currency }}
            </option>
          </select>
          <p>
            Please note that only the declared symbols will be broadcasted to your US Investors. FIFO and Hedging restrictions will apply only for these symbols, as well.
          </p>
          <button @click="updateFifoSettings">Update FIFO Settings</button>
        </div>
      </div>
    </div>

    <!-- Broker Account Link Settings -->
    <div class="settings-section">
      <h2>Broker Account Link Settings</h2>
      <div class="broker-account-link">
        <p>
          ZuluTrade can connect your trader account with different trading platforms. Click on "Link to a broker account" to select your platform and settings, edit your broker credentials, or switch to another broker.
          <a href="#">Certain restrictions apply when trading with a demo account.</a>
        </p>
        <div class="broker-status">
          <div>Account Status: Connected, ZuluTrade (Demo)</div>
          <button @click="linkBrokerAccount">Link to a broker account</button>
        </div>
      </div>
    </div>

    <!-- ZuluTrading API Settings -->
    <div class="settings-section">
      <h2>ZuluTrading API Settings</h2>
      <div class="api-settings">
        <p>
          What is ZuluTrading API? ZuluTrade is now offering the first version of our API that allows you to submit trading requests to your ZuluTrade account through our modern REST API.
        </p>
        <p>
          Do I need programming skills to use the ZuluTrading API? Although some basic programming skills are required, our comprehensive step-by-step guides can help you to easily set up your environment. So, all you need to do is enable your ZuluTrading API and then follow the step-by-step guide for the programming language of your choice!
          <a href="#">Click here to read how to use the ZuluTrading API.</a>
        </p>
        <div class="api-status">
          <div>API Status: The API is disabled for your account</div>
          <button @click="enableApi">Enable the API</button>
        </div>
      </div>
    </div>

    <!-- Preferred Timezone -->
    <div class="settings-section">
      <h2>Preferred Timezone</h2>
      <div class="preferred-timezone">
        <p>
          Changing the preferred timezone affects all aspects of the user interface, where dates and time are displayed. For example, Closed Trades in Trade History will have their Opened and Closed dates and times adjusted, from the default Coordinated Universal Time (UTC) to the preferred timezone.
        </p>
        <select v-model="selectedTimezone">
          <option v-for="timezone in timezones" :key="timezone" :value="timezone">
            {{ timezone }}
          </option>
        </select>
        <p>
          The preferred timezone does not match the local timezone. Displayed dates and time in your account will differ from the ones reported by your system's clock.
        </p>
        <button @click="updateTimezone">Update Preferred Timezone</button>
        <button @click="resetTimezoneChanges">Reset Changes</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "SettingsPage",
  data() {
    return {
      fifoEnabled: false,
      currencies: ["EUR/JPY", "EUR/USD", "GBP/USD", "USD/JPY"],
      selectedCurrencies: [],
      timezones: ["UTC", "Local Timezone: UTC+0800"],
      selectedTimezone: "UTC",
    };
  },
  methods: {
    updateFifoSettings() {
      alert("FIFO Settings Updated");
    },
    linkBrokerAccount() {
      alert("Link to Broker Account");
    },
    enableApi() {
      alert("API Enabled");
    },
    updateTimezone() {
      alert("Timezone Updated");
    },
    resetTimezoneChanges() {
      this.selectedTimezone = "UTC";
      alert("Timezone Changes Reset");
    },
  },
};
</script>

<style scoped>
.settings-page {
  padding: 40px;
  max-width: 1000px;
  margin: 0 auto;
  font-family: Arial, sans-serif;
  background-color: #f8f9fa;
}

.settings-section {
  padding: 20px;
  border: 1px solid #e2e2e2;
  border-radius: 10px;
  background-color: #ffffff;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
  margin-bottom: 30px;
}

h2 {
  margin-bottom: 20px;
  color: #007bff;
  font-weight: bold;
}

label {
  display: block;
  margin-bottom: 10px;
  font-weight: bold;
}

.currency-selection select {
  width: 100%;
  padding: 10px;
  margin-bottom: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  cursor: pointer;
  border-radius: 5px;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #0056b3;
}

p {
  margin-bottom: 20px;
  line-height: 1.6;
  color: #555;
}

a {
  color: #007bff;
  text-decoration: underline;
  cursor: pointer;
}

a:hover {
  color: #0056b3;
}

.broker-status,
.api-status {
  margin-top: 10px;
  font-weight: bold;
  color: #333;
}

select {
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}
</style>
