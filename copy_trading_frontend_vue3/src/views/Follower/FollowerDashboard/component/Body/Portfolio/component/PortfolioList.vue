<script setup>

defineProps({
  portfolioContent: {
    type: String,
    required: true,
  },
});
</script>

<template>
  <div class="portfolio-content">
    <div v-if="portfolioContent === 'open'"></div>
    <div v-else-if="portfolioContent === 'pending'"></div>
    <div v-else-if="portfolioContent === 'history'"></div>
  </div>
</template>