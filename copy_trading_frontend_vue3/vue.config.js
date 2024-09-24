const { defineConfig } = require('@vue/cli-service')

//按需导入elementPlus组件
const AutoImport = require('unplugin-auto-import/webpack').default;
const Components = require('unplugin-vue-components/webpack').default;
const { ElementPlusResolver } = require('unplugin-vue-components/resolvers');
const ElementPlus = require('unplugin-element-plus/webpack');

module.exports = defineConfig({
  transpileDependencies: true,
  
  //按需导入elementPlus组件
  configureWebpack: {
    plugins: [
      AutoImport({
        resolvers: [ElementPlusResolver()],
      }),
      Components({
        resolvers: [ElementPlusResolver()],
      }),
      ElementPlus({
        useSource: true,
      }),
    ],
  },
  devServer: {
    port: 8081, // 将端口号设置为你希望的值.
  },
})
