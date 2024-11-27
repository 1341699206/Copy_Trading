class WebSocketManager {
  constructor() {
    this.webSocketMap = new Map(); // 存储 WebSocket 实例，key 是 channel，value 是 WebSocket 对象
    this.listeners = new Map();   // 存储事件监听器，key 是 channel，value 是监听该 channel 的回调函数数组
  }

  /**
   * 连接到 WebSocket 服务器
   * @param {String} url WebSocket 服务器地址
   * @param {String} handler WebSocket 的处理器路径部分
   */
  connect(url, handler) {
    const channel = `${handler}`; // 使用 handler 构建唯一的 channel 标识符

    if (this.webSocketMap.has(channel)) {
      console.warn(`WebSocket for channel "${channel}" is already connected.`);
      return;
    }

    // 创建 WebSocket 实例
    const ws = new WebSocket(`${url}${handler}`);

    ws.onopen = () => {
      console.log(`WebSocket connected to: ${url}${handler}`);
      this.webSocketMap.set(channel, ws);
    };

    ws.onmessage = (event) => {
      try {
        const data = JSON.parse(event.data); // 解析接收到的消息
        this._dispatchMessage(channel, data); // 分发消息给监听器
      } catch (error) {
        console.error(`Failed to parse WebSocket message for channel ${channel}:`, error);
      }
    };

    ws.onclose = () => {
      console.log(`WebSocket closed: ${channel}`);
      this.webSocketMap.delete(channel);
    };

    ws.onerror = (error) => {
      console.error(`WebSocket error (${channel}):`, error);
    };
  }

  /**
   * 注册消息监听器
   * @param {String} channel WebSocket 通道的唯一标识符
   * @param {Function} callback 回调函数，用于处理接收到的数据
   */
  onMessage(channel, callback) {
    if (!this.listeners.has(channel)) {
      this.listeners.set(channel, []);
    }
    this.listeners.get(channel).push(callback);
  }

  /**
   * 移除某个通道的消息监听器
   * @param {String} channel WebSocket 通道的唯一标识符
   * @param {Function} callback 要移除的回调函数
   */
  offMessage(channel, callback) {
    if (this.listeners.has(channel)) {
      const callbacks = this.listeners.get(channel);
      this.listeners.set(channel, callbacks.filter((cb) => cb !== callback));
    }
  }

  /**
   * 分发接收到的消息给所有监听器
   * @param {String} channel WebSocket 通道的唯一标识符
   * @param {Object} message 接收到的消息对象
   */
  _dispatchMessage(channel, message) {
    if (this.listeners.has(channel)) {
      const callbacks = this.listeners.get(channel);
      callbacks.forEach((cb) => cb(message));
    }
  }

  /**
   * 动态添加 WebSocket 数据监听器
   * @param {String} url WebSocket 地址
   * @param {String} handler WebSocket 处理器路径部分
   * @param {Function} callback 监听回调函数，用于处理接收到的数据
   */
  addDynamicListener(url, handler, callback) {
    const channel = `${handler}`;
    if (!this.webSocketMap.has(channel)) {
      this.connect(url, handler);
    }
    this.onMessage(channel, callback);
  }

  /**
   * 动态移除 WebSocket 数据监听器
   * @param {String} handler WebSocket 处理器路径部分
   * @param {Function} callback 要移除的回调函数
   */
  removeDynamicListener(handler, callback) {
    const channel = `${handler}`;
    this.offMessage(channel, callback);
    if (!this.listeners.has(channel) || this.listeners.get(channel).length === 0) {
      this.disconnect(handler);
    }
  }

  /**
   * 关闭指定通道的 WebSocket 连接
   * @param {String} handler WebSocket 处理器路径部分
   */
  disconnect(handler) {
    const channel = `${handler}`;
    const ws = this.webSocketMap.get(channel);

    if (ws) {
      ws.close();
      this.webSocketMap.delete(channel);
    }
  }
}

// 创建 WebSocketManager 单例
const webSocketManager = new WebSocketManager();
export default webSocketManager;
