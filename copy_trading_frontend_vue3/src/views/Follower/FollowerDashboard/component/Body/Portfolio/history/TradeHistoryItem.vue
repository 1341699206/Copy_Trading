<script setup>
import { defineProps } from "vue";
// 定义组件的 props，接收父组件传递的 `item` 数据
defineProps({
  item: {
    type: Object,
    required: true,
  },
});
</script>

<template>
  <div class="item">
    <div class="name">
      <ul>
        <li>{{ item.currency }}</li>
        <li>
          <div class="time">{{ item.dateOpen }}</div>
          <div :class="['type', item.type]">{{ item.type }}</div>
        </li>
      </ul>
    </div>
    <div class="DataClosed">{{ item.dateClose }}</div>
    <div class="STDLots">{{ item.standardLots }}</div>
    <div class="OpenClose">
      <ul>
        <li class="open">{{ item.priceOpen }}</li>
        <li class="close">{{ item.priceClose }}</li>
      </ul>
    </div>
    <div class="High">{{ item.highestProfit }}</div>
    <div class="Low">{{ item.worstDrawdown }}</div>
    <div class="Roll">{{ item.interest }}</div>
    <div :class="['Profit', item.profit > 0 ? 'profit-positive' : 'profit-negative']">
      ${{ item.profit }}<br />
      <span :class="item.profit > 0 ? 'pips-positive' : 'pips-negative'">{{ item.pips }} pips</span>
    </div>
    <div class="Total">${{ item.total }}<br />{{ item.totalPips }} pips</div>
  </div>
</template>

<style lang="scss" scoped>
.item {
  display: grid;
  grid-template-columns: 2fr 2fr 1fr 2fr 1fr 1fr 1fr 2fr 2fr;
  padding: 10px;
  border-bottom: 1px solid #eee;
  align-items: center;
  font-size: 0.9rem;
  color: #333;
  gap: 1rem;

  .name {
    ul {
      list-style: none;
      padding: 0;
      margin: 0;
      li {
        &:first-child {
          font-weight: bold;
          color: #000;
        }
        &:nth-child(2) {
          display: flex;
          align-items: center;
          .time {
            font-size: 0.8rem;
            color: #888;
          }
          .type {
            font-size: 0.8rem;
            margin-left: 5px;
            padding: 2px 5px;
            border-radius: 3px;
            color: #fff;
            &.BUY {
              background-color: #4caf50;
            }
            &.SELL {
              background-color: #f44336;
            }
          }
        }
      }
    }
  }

  .DataClosed,
  .STDLots,
  .High,
  .Low,
  .Roll,
  .Profit,
  .Total {
    text-align: center;
  }

  .OpenClose {
    ul {
      list-style: none;
      padding: 0;
      margin: 0;
      text-align: center;
      li {
        &:first-child {
          font-weight: bold;
          color: #000;
        }
        &:last-child {
          font-size: 0.8rem;
          color: #666;
        }
      }
    }
  }

  .Profit {
    text-align: center;
    font-weight: bold;
    .pips-positive {
      color: #4caf50;
    }
    .pips-negative {
      color: #f44336;
    }
    &.profit-positive {
      color: #4caf50;
    }
    &.profit-negative {
      color: #f44336;
    }
  }

  .Total {
    text-align: center;
    font-weight: bold;
    color: #333;
  }
}
</style>