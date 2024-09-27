<script setup>
    import Trader_Header from './component/Trader_Header.vue';
    import Trader_Footer from './component/Trader_Footer.vue';
    
</script>


<template>
    <div>
        <Trader_Header></Trader_Header>
        <!-- 二级路由出口 -->
        <router-view></router-view>
        <Trader_Footer></Trader_Footer>
    </div>
</template>

