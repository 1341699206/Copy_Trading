<script setup>
    import LoginHeader from './component/LoginHeader.vue';
</script>

<template>
    <div>
       <LoginHeader></LoginHeader>
        <!-- 二级路由的出口 -->
        <router-view></router-view>
    </div>
</template>