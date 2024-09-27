<script setup>
    import FollowerHeader from './component/Follower_Header.vue';
    import FollowerFooter from './component/Follower_Footer.vue';
    import FollowerFixed from './component/Follower_Fixed.vue';
</script>


<template>
    <div>
        <FollowerFixed></FollowerFixed>
        <FollowerHeader></FollowerHeader>
        <!-- 二级路由出口 -->
        <router-view></router-view>
        <FollowerFooter></FollowerFooter>
    </div>
</template>

