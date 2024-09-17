const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 8080, // 将端口号设置为你希望的值，例如 8080 或其他端口
  },
})
