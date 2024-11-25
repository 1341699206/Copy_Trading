class WebSocketManager {
  constructor() {
    this.webSocketMap = new Map(); // 存储所有 WebSocket 实例
    this.listeners = new Map();   // 存储事件监听器 { channel: [callback1, callback2, ...] }
  }

  /**
   * 初始化并连接 WebSocket
   * @param {String} channel WebSocket唯一标识
   * @param {String} url WebSocket地址
   */
  connect(channel, url) {
    if (this.webSocketMap.has(channel)) {
      console.warn(`WebSocket for channel "${channel}" is already connected.`);
      return;
    }

    const ws = new WebSocket(url);
    ws.onopen = () => console.log(`WebSocket connected: ${channel}`);
    ws.onmessage = (event) => this._dispatchMessage(channel, event.data);
    ws.onerror = (error) => console.error(`WebSocket error (${channel}):`, error);
    ws.onclose = () => {
      console.log(`WebSocket closed: ${channel}`);
      this.webSocketMap.delete(channel);
    };

    this.webSocketMap.set(channel, ws);
  }

  /**
   * 关闭指定 WebSocket
   * @param {String} channel WebSocket唯一标识
   */
  disconnect(channel) {
    const ws = this.webSocketMap.get(channel);
    if (ws) {
      ws.close();
      this.webSocketMap.delete(channel);
    }
  }

  /**
   * 注册消息监听器
   * @param {String} channel WebSocket唯一标识
   * @param {Function} callback 回调函数 (data) => {...}
   */
  onMessage(channel, callback) {
    if (!this.listeners.has(channel)) {
      this.listeners.set(channel, []);
    }
    this.listeners.get(channel).push(callback);
  }

  /**
   * 移除消息监听器
   * @param {String} channel WebSocket唯一标识
   * @param {Function} callback 需要移除的回调函数
   */
  offMessage(channel, callback) {
    if (this.listeners.has(channel)) {
      const callbacks = this.listeners.get(channel);
      this.listeners.set(channel, callbacks.filter((cb) => cb !== callback));
    }
  }

  /**
   * 分发接收到的消息
   * @param {String} channel WebSocket唯一标识
   * @param {String} message 收到的消息
   */
  _dispatchMessage(channel, message) {
    if (this.listeners.has(channel)) {
      const callbacks = this.listeners.get(channel);
      callbacks.forEach((cb) => cb(JSON.parse(message)));
    }
  }
  
  /**
   * 动态添加某个 WebSocket 数据监听
   * @param {String} channel WebSocket唯一标识
   * @param {String} url WebSocket地址
   * @param {Function} callback 回调函数 (data) => {...}
   */
  addDynamicListener(channel, url, callback) {
    if (!this.webSocketMap.has(channel)) {
      this.connect(channel, url); // 如果未连接，则建立连接
    }
    this.onMessage(channel, callback); // 添加监听器
  }

  /**
   * 动态移除某个 WebSocket 数据监听
   * @param {String} channel WebSocket唯一标识
   * @param {Function} callback 需要移除的回调函数
   */
  removeDynamicListener(channel, callback) {
    this.offMessage(channel, callback); // 移除监听器
    if (!this.listeners.has(channel) || this.listeners.get(channel).length === 0) {
      this.disconnect(channel); // 如果没有剩余监听器，则断开连接
    }
  }
  
}

// 创建单例 WebSocketManager
const webSocketManager = new WebSocketManager();
export default webSocketManager;

