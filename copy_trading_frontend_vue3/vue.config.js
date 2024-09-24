const { defineConfig } = require('@vue/cli-service')

//按需导入elementPlus组件
const AutoImport = require('unplugin-auto-import/webpack').default;
const Components = require('unplugin-vue-components/webpack').default;
const { ElementPlusResolver } = require('unplugin-vue-components/resolvers');
const ElementPlus = require('unplugin-element-plus/webpack');

module.exports = defineConfig({
  transpileDependencies: true,

  //导入自定义的主题色系统
  css: {
    loaderOptions: {
      scss: {
        additionalData: `@use "@/styles/element/index.scss" as *;`,
      },
    },
  },
  
  //按需导入elementPlus组件
  configureWebpack: {
    plugins: [
      AutoImport({
        resolvers: [ElementPlusResolver()],
      }),
      Components({
        resolvers: [
          // 配置Element Plus采用saas样式配色系统
          ElementPlusResolver({importStyle:"sass"}),
        ],
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
