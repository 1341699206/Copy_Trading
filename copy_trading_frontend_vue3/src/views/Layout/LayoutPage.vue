<script setup>
    import LayoutHeader from './component/LayoutHeader.vue';
    import LayoutFooter from './component/LayoutFooter.vue';
</script>


<template>
    <div>
        这里是首页
        <layout-header></layout-header>
        <!-- 二级路由出口 -->
        <router-view></router-view>
        <layout-footer></layout-footer>
    </div>
</template>
