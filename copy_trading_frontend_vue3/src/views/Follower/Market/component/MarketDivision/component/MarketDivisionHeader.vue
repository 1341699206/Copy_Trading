<template>
  <div class="index">
    <ul class="app-header-nav">
      <li>
        <router-link to="/forex" exact-active-class="active">Forex</router-link>
      </li>
      <li>
        <router-link to="/stocks" exact-active-class="active">Stocks</router-link>
      </li>
      <li>
        <router-link to="/cyptoCFD" exact-active-class="active">CyptoCFD</router-link>
      </li>
      <li>
        <router-link to="/indices" exact-active-class="active">Indices</router-link>
      </li>
      <li>
        <router-link to="/commodities" exact-active-class="active">Commodities</router-link>
      </li>
    </ul>
  </div>
</template>

<style lang="scss" scoped>
.index {
  width: 100%; /* 横向占满父容器宽度 */
  padding: 10px; /* 上下内边距 */
  .app-header-nav {
    display: flex; /* 横排显示 */
    list-style: none; /* 去掉点 */
    padding: 0; /* 去掉内边距 */
    margin: 0; /* 去掉外边距 */
    justify-content: flex-start; /* 从左侧开始排列 */
    
    /* 添加左右内边距以保持与父容器的距离 */
    padding-left: 20px; /* 左侧内边距 */
    padding-right: 20px; /* 右侧内边距 */
    li {
      margin-right: 40px; /* 按钮之间的间距 */
      
      a {
        text-decoration: none; /* 去掉下划线 */
        font-size: 18px; /* 增大字体 */
        color: #333; /* 字体颜色 */
        transition: color 0.3s; /* 添加过渡效果 */
        &:hover {
          color: $xtxColor; /* 悬停时改变颜色 */
        }
      }
       /* 为活动链接添加边框和背景色 */
      & .active {
        color: #fff; /* 改变文字颜色为白色 */
        background-color: $xtxColor; /* 填充背景色 */
        border: 2px solid $xtxColor; /* 边框颜色与背景色一致 */
      }
    }
  }
}
</style>