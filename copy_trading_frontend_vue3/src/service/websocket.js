// src/services/websocket.js
import { emit } from '@/utils/eventBus'; // 引入事件总线模块

let socket;

export function connectWebSocket(userId) {
  if (!socket || socket.readyState === WebSocket.CLOSED) {
    socket = new WebSocket(`ws://localhost:9099/ws?userId=${userId}`);

    socket.onopen = function () {
      console.log('WebSocket connected');
    };

    socket.onmessage = function (event) {
      const message = JSON.parse(event.data);
      const { code, msg, data } = message;

      // 通过事件总线分发 WebSocket 消息
      if(code ==1){
        emit( msg, data);
      } else {
        console.error('error: ', msg); // 处理错误信息
      }
    };

    socket.onclose = function () {
      console.log('WebSocket closed. Reconnecting...');
      setTimeout(() => connectWebSocket(userId), 1000); // 动态传入 userId，保持传入的 userId 一致
    };

    socket.onerror = function (error) {
      console.error('WebSocket error:', error);
    };
  }
}

export function closeWebSocket() {
  if (socket) {
    socket.close();
  }
}
