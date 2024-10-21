// src/utils/eventBus.js

//websocket 响应总线程

import { reactive } from 'vue';

// 定义事件总线对象
const eventBus = reactive({});

// 注册事件监听器
export function on(eventType, callback) {
  if (!eventBus[eventType]) {
    eventBus[eventType] = [];
  }
  eventBus[eventType].push(callback);
}

// 分发事件给注册的监听器
export function emit(eventType, data) {
  if (eventBus[eventType]) {
    eventBus[eventType].forEach(callback => callback(data));
  }
}

// 移除事件监听器，防止内存泄漏
export function off(eventType, callback) {
  if (eventBus[eventType]) {
    eventBus[eventType] = eventBus[eventType].filter(cb => cb !== callback);
  }
}
