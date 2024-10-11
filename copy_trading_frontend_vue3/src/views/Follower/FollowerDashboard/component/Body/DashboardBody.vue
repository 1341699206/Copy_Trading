<script setup>
import DashboardHeader from '../Body/component/DashboardHeader.vue'

import { ref } from "vue";
const selectedItem = ref("portfolio"); // 管理当前选中的模块
</script>

<template>
    <div>
        <dashboard-header @update-content="selectedItem = $event"></dashboard-header>
    </div>
</template>