<template>
    <header class="login-header">
        <div class="container m-top-20">
            <h1 class="logo">
              <RouterLink to="/">Co-trade</RouterLink>
            </h1>
            <RouterLink class="entry" to="/">
              返回首页
              <i class="iconfont icon-angle-right"></i>
              <i class="iconfont icon-angle-right"></i>
            </RouterLink>
        </div>
      </header>
</template>

<style scoped lang='scss'>
.login-header {
  background: #fff;
  border-bottom: 1px solid #e4e4e4;

  .container {
    display: flex;
    height: 60px;
    align-items: flex-end;
    justify-content: space-between;
  }

  .logo {
    width: 200px;

    a {
      display: block;
      height: 132px;
      width: 100%;
      text-indent: -9999px;
      //background: url("@/assets/images/logo.png") no-repeat center 18px / contain;
    }
  }

  .sub {
    flex: 1;
    font-size: 24px;
    font-weight: normal;
    margin-bottom: 38px;
    margin-left: 20px;
    color: #666;
  }

  .entry {
    width: 120px;
    margin-bottom: 22px;
    font-size: 16px;

    i {
      font-size: 14px;
      color: $xtxColor;
      letter-spacing: -5px;
    }
  }
}
</style>