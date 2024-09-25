<script setup>
    import LayoutHeader from './component/LayoutHeader.vue';
    import LayoutFooter from './component/LayoutFooter.vue';
    import LayoutFixed from './component/LayoutFixed.vue';
</script>


<template>
    <div>
        <layout-fixed></layout-fixed>
        <layout-header></layout-header>
        <!-- 二级路由出口 -->
        <router-view></router-view>
        <layout-footer></layout-footer>
    </div>
</template>

