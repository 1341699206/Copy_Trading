const { defineConfig } = require('@vue/cli-service')

//按需导入elementPlus组件
const AutoImport = require('unplugin-auto-import/webpack').default;
const Components = require('unplugin-vue-components/webpack').default;
const { ElementPlusResolver } = require('unplugin-vue-components/resolvers');
const ElementPlus = require('unplugin-element-plus/webpack');

module.exports = defineConfig({
  transpileDependencies: true,

  css: {
    loaderOptions: {
      scss: {
        //@use "@/styles/element/index.scss" as *;导入自定义的elementPlus主题色系统
        //@use "@/styles/var.scss" as *;导入样式变量，使用变量时，可以不再需要import
        additionalData: ` 
          @use "@/styles/element/index.scss" as *;
          @use "@/styles/var.scss" as *;
        `,
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
          // 配置Element Plus采用sass样式配色系统
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
