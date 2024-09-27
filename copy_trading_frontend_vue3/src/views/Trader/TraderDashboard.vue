<script setup>
    import TraderHeader from './component/Trader_Header.vue';
    import TraderFooter from './component/Trader_Footer.vue';
    
</script>


<template>
    <div>
        <TraderHeader></TraderHeader>
        <!-- 二级路由出口 -->
        <router-view></router-view>
        <TraderFooter></TraderFooter>
    </div>
</template>

