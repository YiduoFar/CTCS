const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  // 忽略命名不规范
  lintOnSave: false
})
