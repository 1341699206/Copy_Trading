<script setup>
import { ref } from "vue";
import CopierSelectFrame from './component/CopierSelectFrame'
import CopierDisplayFrame from './component/CopierDisplayFrame/CopierDisplayFrame'

// 定义一个响应式变量来存储 item
const selectedItem = ref(null);

function handleItem(item) {
  selectedItem.value = item; // 更新 selectedItem
}
</script>

<template>
    <div class="container">
        <copier-select-frame class="select" @sendItemToParent="handleItem"></copier-select-frame>
        <copier-display-frame class="display" :item="selectedItem"></copier-display-frame>
    </div>
</template>

<style scoped>
.container {
    display: flex;
    width: 100%;
    height: 100%;
}

.select {
    flex: 1; /* 1/3 */
}

.display {
    flex: 2; /* 2/3 */
}
</style>