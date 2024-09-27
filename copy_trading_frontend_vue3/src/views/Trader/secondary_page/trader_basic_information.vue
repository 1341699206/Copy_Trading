<script setup>
    import Basic_Info from './component/Basic_Info.vue';
    import Navigation_bar from './component/Navigation_bar.vue';
    
</script>


<template>
    <div>
        <Basic_Info></Basic_Info>
        <Navigation_bar></Navigation_bar>
        <!-- 二级路由出口 -->
        <router-view></router-view>
        
    </div>
</template>

