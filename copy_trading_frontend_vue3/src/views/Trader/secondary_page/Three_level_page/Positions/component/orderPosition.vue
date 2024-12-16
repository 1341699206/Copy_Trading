<template>
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
</template>