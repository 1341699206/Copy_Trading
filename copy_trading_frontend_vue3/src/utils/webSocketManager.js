import { Client } from '@stomp/stompjs'; // 导入浏览器兼容的 Stomp 客户端
import SockJS from 'sockjs-client';      // 导入 SockJS 客户端，用于创建 WebSocket 连接

class WebSocketManager {
  constructor() {
    // 初始化数据结构存储 WebSocket 实例和相关信息
    this.webSocketMap = new Map();  // 存储 WebSocket 实例，key 是 channel，value 是 WebSocket 对象
    this.listeners = new Map();     // 存储事件监听器，key 是 channel，value 是监听该 channel 的回调函数数组
    this.stompClients = new Map();  // 存储 STOMP 客户端，key 是 channel，value 是 STOMP 客户端实例
  }

  /**
   * 初始化并连接 WebSocket。
   * 连接成功后会自动订阅指定的 STOMP 主题。
   * @param {String} url WebSocket 服务器的地址
   * @param {String} handler WebSocket 的处理器部分，代表具体业务
   */
  connect(url, handler) {
    const channel = `${handler}`; // 使用 handler 构建唯一的 channel 标识符

    // 如果该通道已连接，输出警告并返回
    if (this.webSocketMap.has(channel)) {
      console.warn(`WebSocket for channel "${channel}" is already connected.`);
      return;
    }

    // 创建 SockJS WebSocket 连接
    const socket = new SockJS(url);

    // 创建 STOMP 客户端，配置连接选项
    const stompClient = new Client({
      webSocketFactory: () => socket,  // 使用 SockJS 实现 WebSocket 连接
      onConnect: () => {
        console.log(`WebSocket connected to: ${url}${handler}`);
        this.stompClients.set(channel, stompClient); // 保存 STOMP 客户端实例
        this._subscribeToTopic(channel, handler);   // 连接成功后订阅指定的主题
      },
      onDisconnect: () => {
        console.log(`WebSocket closed: ${channel}`);
        this.webSocketMap.delete(channel);        // 删除 WebSocket 实例
        this.stompClients.delete(channel);        // 删除 STOMP 客户端实例
      },
      onError: (error) => console.error(`WebSocket error (${channel}):`, error), // 处理连接错误
    });

    // 激活 STOMP 客户端，启动连接
    stompClient.activate();

    // 保存 WebSocket 实例
    this.webSocketMap.set(channel, socket);
  }

  /**
   * 订阅某个 WebSocket 通道的 STOMP 主题
   * @param {String} channel WebSocket 通道的唯一标识符
   * @param {String} handler WebSocket 的处理器部分
   */
  _subscribeToTopic(channel, handler) {
    const stompClient = this.stompClients.get(channel);

    // 如果 STOMP 客户端尚未连接，报错并返回
    if (!stompClient) {
      console.error(`STOMP client not connected for channel: ${channel}`);
      return;
    }

    // 订阅指定的 STOMP 主题
    stompClient.subscribe(`${handler}`, (message) => {
      // 确保消息格式正确并成功解析
      try {
        const data = JSON.parse(message.body);  // 解析接收到的消息
        this._dispatchMessage(channel, data);   // 分发消息给已注册的回调
      } catch (error) {
        console.error(`Failed to parse message for channel ${channel}:`, error); // 处理解析错误
      }
    });
  }

  /**
   * 注册消息监听器，接收指定通道的消息
   * @param {String} channel WebSocket 通道的唯一标识符
   * @param {Function} callback 回调函数，用于处理接收到的数据
   */
  onMessage(channel, callback) {
    // 如果该通道没有监听器，则初始化为空数组
    if (!this.listeners.has(channel)) {
      this.listeners.set(channel, []);
    }
    // 将回调函数添加到该通道的监听器数组中
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
      // 从监听器数组中移除指定的回调函数
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
      // 遍历所有回调函数并执行
      callbacks.forEach((cb) => cb(message));
    }
  }

  /**
   * 动态添加 WebSocket 数据监听器，自动连接并订阅
   * @param {String} url WebSocket 地址
   * @param {String} handler WebSocket 处理器部分
   * @param {Function} callback 监听回调函数，用于处理接收到的数据
   */
  addDynamicListener(url, handler, callback) {
    const channel = `${handler}`;
    // 如果该通道尚未连接，建立连接
    if (!this.webSocketMap.has(channel)) {
      this.connect(url, handler);
    }

    // 等待连接成功后再订阅该主题
    const stompClient = this.stompClients.get(channel);
    if (stompClient) {
      this._subscribeToTopic(channel, handler); // 确保订阅主题
    }

    // 注册消息监听器
    this.onMessage(channel, callback);
  }

  /**
   * 动态移除 WebSocket 数据监听器
   * @param {String} handler WebSocket 处理器部分
   * @param {Function} callback 要移除的回调函数
   */
  removeDynamicListener(handler, callback) {
    const channel = `${handler}`;
    // 移除指定的监听器
    this.offMessage(channel, callback);
    // 如果该通道没有剩余的监听器，则关闭连接
    if (!this.listeners.has(channel) || this.listeners.get(channel).length === 0) {
      this.disconnect(handler);
    }
  }

  /**
   * 关闭指定通道的 WebSocket 连接
   * @param {String} handler WebSocket 处理器部分
   */
  disconnect(handler) {
    const channel = `${handler}`;
    const stompClient = this.stompClients.get(channel);

    // 如果 STOMP 客户端存在，停止客户端连接
    if (stompClient) {
      stompClient.deactivate(); // 停止 STOMP 客户端
      this.stompClients.delete(channel); // 删除 STOMP 客户端实例
    }

    // 如果 WebSocket 连接存在，关闭连接
    const ws = this.webSocketMap.get(channel);
    if (ws) {
      ws.close(); // 关闭 WebSocket 连接
      this.webSocketMap.delete(channel); // 删除 WebSocket 实例
    }
  }
}

// 创建 WebSocketManager 单例
const webSocketManager = new WebSocketManager();
export default webSocketManager;
