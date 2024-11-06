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
  <div class="name">
    <ul>
      <li>{{ item.currency }}</li>
      <li>
        <div class="time">{{ item.dateOpen }}</div>
        <div class="type">{{ item.type }}</div>
      </li>
    </ul>
  </div>
  <div class="DataClosed">{{ item.dateClose }}</div>
  <div class="STDLots">{{ item.standardLots }}</div>
  <div class="Open/Close">
    <ul>
      <li class="open">{{ item.priceOpen }}</li>
      <li class="close">{{ item.priceClose }}</li>
    </ul>
  </div>
  <div class="High">{{ item.highestProfit }}</div>
  <div class="Low">{{ item.worstDrawdown }}</div>
  <div class="Roll">{{ item.interest }}</div>
  <div class="Profit">
    <ul>
        <li>{{item.profit}}</li>
    </ul>
  </div>
  <div class="Total"></div>
</template>

<style lang="scss" scoped>
.item {
  display: flex;
  padding: 10px;
  border-bottom: 1px solid #eee;
  align-items: center;
  font-size: 0.9rem;
  color: #333;

  // 币种与开仓信息
  .name {
    flex: 2;
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

  // 平仓时间
  .DataClosed {
    flex: 2;
    font-size: 0.8rem;
    color: #888;
    text-align: center;
  }

  // 标准手数
  .STDLots {
    flex: 1;
    text-align: center;
  }

  // 开盘/收盘价格
  .OpenClose {
    flex: 2;
    ul {
      list-style: none;
      padding: 0;
      margin: 0;
      text-align: right;
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

  // 最高盈利
  .High {
    flex: 1;
    color: #4caf50;
    font-weight: bold;
    text-align: right;
  }

  // 最差回撤
  .Low {
    flex: 1;
    color: #f44336;
    font-weight: bold;
    text-align: right;
  }

  // 利息
  .Roll {
    flex: 1;
    text-align: center;
    color: #333;
  }

  // 盈利
  .Profit {
    flex: 2;
    text-align: right;
    font-weight: bold;
    color: #4caf50;
    ul {
      list-style: none;
      padding: 0;
      margin: 0;
    }
  }

  // 总利润
  .Total {
    flex: 2;
    text-align: right;
    font-weight: bold;
    color: #333;
  }
}
</style>