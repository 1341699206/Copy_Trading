<template>
  <div class="trade-card">
    <!-- 顶部资产信息 -->
    <div class="trade-card-header">
      <div class="asset-info">
        {{ formattedSymbol }}
        <span class="icon">~</span>
      </div>
      <div class="header-icons">
        <span class="status-indicator"></span>
        <button class="close-btn">×</button>
      </div>
    </div>

    <!-- 中部价格信息 -->
    <div class="price-section">
      <!-- 卖出价格 -->
      <div class="price sell" :class="sellPriceClass">
        <div class="large-text">
          <span class="price-small">{{ sellPriceWholePart }}</span>
          <span class="price-dot">.</span>
          <span class="price-big">{{ sellPriceMain }}</span>
          <span class="price-small">{{ sellPriceEnd }}</span>
        </div>
        <span class="price-arrow" v-if="sellPriceDirection !== 0">
          <i :class="sellPriceArrowClass"></i>
        </span>
      </div>

      <!-- 买入价格 -->
      <div class="price buy" :class="buyPriceClass">
        <div class="large-text">
          <span class="price-small">{{ buyPriceWholePart }}</span>
          <span class="price-dot">.</span>
          <span class="price-big">{{ buyPriceMain }}</span>
          <span class="price-small">{{ buyPriceEnd }}</span>
        </div>
        <span class="price-arrow" v-if="buyPriceDirection !== 0">
          <i :class="buyPriceArrowClass"></i>
        </span>
      </div>
    </div>

    <!-- 中间的点差显示 -->
    <div class="spread-between">
      <span>{{ spread.toFixed(1) }}</span>
    </div>

    <!-- 底部按钮区域 -->
    <div class="trade-card-footer">
      <span class="sell-text">SELL</span>
      <span class="buy-text">BUY</span>
    </div>
  </div>
</template>

<script setup>
import { computed, ref, watch } from 'vue';

const props = defineProps({
  asset: Object
});

const spread = 0.1; // 固定点差

// 响应式价格变量
const sellPrice = ref(props.asset.currentPrice - spread);
const buyPrice = ref(props.asset.currentPrice);

// 存储前一个价格用于变化判断
const prevSellPrice = ref(sellPrice.value);
const prevBuyPrice = ref(buyPrice.value);

// 格式化价格为整数和小数部分
const formatPrice = (price) => {
  const [wholePart, decimalPart] = price.toFixed(5).split('.');
  return {
    wholePart,
    main: decimalPart.slice(0, 2),
    end: decimalPart.slice(2)
  };
};

// 响应式存储价格变化的显示内容
const sellPriceWholePart = ref('');
const sellPriceMain = ref('');
const sellPriceEnd = ref('');
const buyPriceWholePart = ref('');
const buyPriceMain = ref('');
const buyPriceEnd = ref('');

// 价格变化方向：1-上涨，-1-下跌，0-未变化
const sellPriceDirection = ref(0);
const buyPriceDirection = ref(0);

// 监控资产价格变化
watch(
  () => props.asset.currentPrice,
  (newPrice) => {
    // 更新卖出价格
    prevSellPrice.value = sellPrice.value;
    sellPrice.value = newPrice - spread;

    sellPriceDirection.value = sellPrice.value > prevSellPrice.value
      ? 1
      : sellPrice.value < prevSellPrice.value
      ? -1
      : 0;

    const { wholePart, main, end } = formatPrice(sellPrice.value);
    sellPriceWholePart.value = wholePart;
    sellPriceMain.value = main;
    sellPriceEnd.value = end;

    // 更新买入价格
    prevBuyPrice.value = buyPrice.value;
    buyPrice.value = newPrice;

    buyPriceDirection.value = buyPrice.value > prevBuyPrice.value
      ? 1
      : buyPrice.value < prevBuyPrice.value
      ? -1
      : 0;

    const { wholePart: buyWhole, main: buyMain, end: buyEnd } = formatPrice(buyPrice.value);
    buyPriceWholePart.value = buyWhole;
    buyPriceMain.value = buyMain;
    buyPriceEnd.value = buyEnd;
  },
  { immediate: true }
);

// 根据价格变化返回的 class
const sellPriceClass = computed(() => (sellPriceDirection.value === 1 ? 'up' : sellPriceDirection.value === -1 ? 'down' : ''));
const buyPriceClass = computed(() => (buyPriceDirection.value === 1 ? 'up' : buyPriceDirection.value === -1 ? 'down' : ''));

// 箭头方向 class
const sellPriceArrowClass = computed(() => (sellPriceDirection.value === 1 ? 'arrow-up' : 'arrow-down'));
const buyPriceArrowClass = computed(() => (buyPriceDirection.value === 1 ? 'arrow-up' : 'arrow-down'));

// 格式化资产符号，例如 "EURUSD" 转为 "EUR/USD"
const formattedSymbol = computed(() => {
  const symbol = props.asset.symbol || '';
  const parts = symbol.split('');
  return parts.length === 6 ? `${parts.slice(0, 3).join('')}/${parts.slice(3).join('')}` : symbol;
});
</script>

<style scoped>
.trade-card {
  border: 1px solid #ddd;
  border-radius: 10px;
  padding: 1rem;
  width: 200px;
  background-color: #f9f9f9;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.trade-card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #f0f0f0;
  padding: 0.5rem;
  border-radius: 8px 8px 0 0;
}

.asset-info {
  font-weight: bold;
  font-size: 0.9rem;
}

.icon {
  margin-left: 0.5rem;
}

.header-icons {
  display: flex;
  align-items: center;
}

.status-indicator {
  width: 10px;
  height: 10px;
  background-color: yellow;
  border-radius: 50%;
  margin-right: 0.5rem;
}

.close-btn {
  border: none;
  background: none;
  font-size: 1.2rem;
  cursor: pointer;
}

.price-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 1rem 0;
}

.price {
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
}

.large-text {
  display: flex;
  align-items: baseline;
  font-weight: bold;
}

.price-small {
  font-size: 0.9rem;
  color: #777;
}

.price-big {
  font-size: 1.8rem;
  font-weight: bold;
  color: #333;
}

.price-dot {
  font-size: 1.8rem;
  color: #333;
  margin: 0 0.1rem;
}

.up {
  background-color: #d0f0c0;
  color: #228b22;
}

.down {
  background-color: #f8d7da;
  color: #c00;
}

.price-arrow {
  position: absolute;
  right: -15px;
  top: 50%;
  transform: translateY(-50%);
}

.arrow-up {
  color: green;
  font-size: 1.5rem;
}

.arrow-down {
  color: red;
  font-size: 1.5rem;
}

/* 点差样式 */
.spread-between {
  font-size: 0.9rem;
  font-weight: bold;
  color: #ff5722;
  text-align: center;
  margin: 0.5rem 0;
}

.trade-card-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 0.8rem;
}

.sell-text, .buy-text {
  font-weight: bold;
}
</style>
