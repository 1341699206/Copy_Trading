<template>
  <div class="trade-card">
    <!-- 顶部资产信息 -->
    <div class="trade-card-header">
      <div class="asset-info">
        {{ formattedInstrument }}
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
      <div class="price sell">
        <div class="large-text">
          <span class="price-small">{{ sellPriceWholePart }}</span>
          <span class="price-big">{{ sellPriceMain }}</span>
          <span class="price-small">{{ sellPriceEnd }}</span>
        </div>
      </div>

      <!-- 买入价格 -->
      <div class="price buy">
        <div class="large-text">
          <span class="price-small">{{ buyPriceWholePart }}</span>
          <span class="price-big">{{ buyPriceMain }}</span>
          <span class="price-small">{{ buyPriceEnd }}</span>
        </div>
      </div>
    </div>

    <!-- 中间的点差显示，放在 SELL 和 BUY 之间 -->
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

<script>
import { computed } from 'vue';

export default {
  name: 'TradeCard',
  props: {
    asset: Object
  },
  setup(props) {
    const spread = 0.1; // 固定点差
    const sellPrice = computed(() => props.asset.currentPrice - spread); // 使用 computed 保持响应性
    const buyPrice = computed(() => props.asset.currentPrice);

    // 格式化资产名称，插入斜杠分隔符（如 EUR/USD）
    const formattedInstrument = computed(() => {
      const instrument = props.asset.instrument || '';
      const parts = instrument.split('');
      if (parts.length === 6) {
        return `${parts.slice(0, 3).join('')}/${parts.slice(3).join('')}`;
      }
      return instrument;
    });

    // 处理价格：显示到五位小数并且提取不同部分
    const formatPrice = (price) => {
      const [wholePart, decimalPart] = price.toFixed(5).split('.'); // 分成整数部分和小数部分
      return {
        wholePart,
        main: decimalPart.slice(0, 2), // 小数点后三四位（突出显示）
        end: decimalPart.slice(2) // 其余小数位
      };
    };

    const sellPriceFormatted = computed(() => formatPrice(sellPrice.value));
    const buyPriceFormatted = computed(() => formatPrice(buyPrice.value));

    return {
      spread,
      formattedInstrument,
      sellPriceWholePart: sellPriceFormatted.value.wholePart,
      sellPriceMain: sellPriceFormatted.value.main,
      sellPriceEnd: sellPriceFormatted.value.end,
      buyPriceWholePart: buyPriceFormatted.value.wholePart,
      buyPriceMain: buyPriceFormatted.value.main,
      buyPriceEnd: buyPriceFormatted.value.end
    };
  }
};
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

/* 点差样式 */
.spread-between {
  font-size: 0.9rem;
  font-weight: bold;
  color: #ff5722;
  text-align: center;
  margin: 0.5rem 0; /* 调整位置到 SELL 和 BUY 之间 */
}

.sell {
  color: #555;
}

.buy {
  color: #333;
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
