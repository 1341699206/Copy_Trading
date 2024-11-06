<script setup>
import { defineProps } from "vue";
// 定义组件的 props，接收父组件传递的 `item` 数据
const props=defineProps({
  item: {
    type: Object,
    required: true,
  },
});

// 定义 emits，用于向父组件发送事件
const emit = defineEmits(["sendItem"]);

// 点击时触发事件，将 `item` 传递给父组件
function handleClick() {
  emit("sendItem", props.item);
}
</script>

<template>
    <div @click="handleClick">
        <div class="avatar"></div>
        <div class="name">{{item.name}}</div>
        <div class="profit">{{item.profit}}</div>
    </div>
</template>
