<template>
  <header class="app-header">
    <div class="container">
      <ul class="app-header-nav">
          <li class="logo">
              <router-link to="/">Co-trade</router-link>
          </li>
          <li class="home">
              <router-link to="/">Home</router-link>
          </li>
          <li>
              <router-link to="/traders">Traders</router-link>
          </li>
          <li>
              <router-link to="/market">Market</router-link>
          </li>
          <li>
              <router-link to="/community">Community</router-link>
          </li>
      </ul>
      <div class="right">
          <template v-if="false">
          </template>
          <template v-else>
            <a @click="$router.push('/login')"><i class="iconFont icon-login"></i>login</a>
            <a @click="$router.push('/register')"><i class="iconFont icon-register"></i>register</a>
          </template>
      </div>
    </div>
  </header>
</template>

<style lang="scss" scoped>
 .app-header {
  background: #fff;

  .container {
    height: 60px;
    border-style:solid;
    border-width: 1px;
    border-color: #000;
    display: flex;
    align-items: center;
  }

  .right {
    width: 220px;
    display: flex;
    text-align: center;
    padding-left: 40px;
    border-left: 2px solid $xtxColor;

    a {
      width: 38px;
      margin-right: 40px;
      font-size: 16px;
      line-height: 1;

      &:hover {
        color: $xtxColor;
      }
    }
  }

  .app-header-nav {
    width: 820px;
    display: flex;
    padding-left: 40px;
    position: relative;
    z-index: 998;

    .logo {
    width: 200px;

      a {
        display: block;
        height: 100%;
        width: 100%;
        text-indent: -9999px;
        //background: url('@/assets/images/logo.png') no-repeat center 18px / contain;
      }
    }
  
    li {
      margin-right: 40px;
      width: 50px;
      text-align: center;
  
      a {
        font-size: 16px;
        line-height: 32px;
        height: 32px;
        display: inline-block;
  
        &:hover {
          color: $xtxColor;
          border-bottom: 1px solid $xtxColor;
        }
      }
  
      .active {
        color: $xtxColor;
        border-bottom: 1px solid $xtxColor;
      }
    }
  }

  .cart {
    width: 50px;

    .curr {
      height: 32px;
      line-height: 32px;
      text-align: center;
      position: relative;
      display: block;

      .icon-cart {
        font-size: 22px;
      }

      em {
        font-style: normal;
        position: absolute;
        right: 0;
        top: 0;
        padding: 1px 6px;
        line-height: 1;
        background: $helpColor;
        color: #fff;
        font-size: 12px;
        border-radius: 10px;
        font-family: Arial;
      }
    }
  }
}
</style>
