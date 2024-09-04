# Copy_Trading

结合复制交易和 AI 推荐的交易平台。

## 目录

- [介绍](#介绍)
- [项目结构](#项目结构)
- [安装](#安装)
- [使用方法](#使用方法)
- [贡献](#贡献)
- [许可证](#许可证)

## 介绍

Copy_Trading 是一个将复制交易功能与 AI 驱动的推荐系统相结合的平台。该项目旨在为用户提供一个高效、直观的界面，用于管理交易并利用 AI 增强交易策略。

## 项目结构

该仓库分为两个主要部分：

- **后端**：使用 Spring Boot 构建，处理服务器端操作、API 和数据管理。
  - **目录**：`copy_trading_backend`
- **前端**：使用 Vue 3 开发，提供客户端用户界面。
  - **目录**：`copy_trading_frontend_vue3`

### 目录概览
- 以下是大致的目录结构图：

COPY-TRADE_PROJECT_ROOT/
├── .vscode/                         # VS Code 的配置文件夹
├── copy_trading_backend/            # 后端项目文件夹 (Spring Boot)
│   ├── src/                         # 源代码目录
│   │   ├── main/
│   │   │   ├── java/                # Java 源代码文件
│   │   │   │   └── com/
│   │   │   │       └── xtq_ymt/
│   │   │   │           └── copy_trading_backend/
│   │   │   │               ├── CopyTradingBackendApplication.java  # 主应用程序入口
│   │   │   │               └── controller/
│   │   │   │                   └── TestController.java             # 示例控制器
│   │   ├── resources/              # 资源文件夹
│   │   │   └── application.properties  # 应用配置文件
│   │   └── test/                   # 测试代码目录
│   │       └── java/
│   │           └── com/...
│   ├── pom.xml                     # Maven 配置文件
│   └── ... (其他后端文件)
├── copy_trading_frontend_vue3/      # 前端项目文件夹 (Vue 3)
│   ├── node_modules/                # Node.js 模块依赖目录
│   ├── public/                      # 公共资源文件夹 (如 HTML、图片等)
│   │   ├── favicon.ico
│   │   └── index.html
│   ├── src/                         # 源代码目录
│   │   ├── assets/                  # 静态资源文件夹 (如图片、CSS等)
│   │   ├── components/              # Vue 组件文件夹
│   │   ├── App.vue                  # Vue 主应用文件
│   │   └── main.js                  # Vue 入口文件
│   ├── .gitignore                   # 忽略的文件和目录配置
│   ├── babel.config.js              # Babel 配置文件
│   ├── package.json                 # npm 配置文件
│   └── README.md                    # 前端项目说明文件
├── database/                        # 数据库相关文件夹
│   └── script/                      # 数据库脚本文件夹
│       ├── schema.sql               # 数据库结构定义脚本
│       └── data.sql                 # 示例数据插入脚本
└── README.md                        # 项目根目录的说明文件



