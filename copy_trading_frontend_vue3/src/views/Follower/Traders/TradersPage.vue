<script setup>
    import TradersHeader from './component/TradersHeader.vue'
    import TopPerforming from './component/TopPerforming.vue'
    import EachTopPerforming from './component/EachTopPerforming.vue'
    import MonthlyRising from './component/MonthlyRising.vue'
</script>

<template>
    <div>
        <traders-header></traders-header>
        <top-performing></top-performing>
        <each-top-performing></each-top-performing>
        <monthly-rising></monthly-rising>
    </div>
</template>