const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 8432,

    //public: '127.0.0.1:8432',

    //open: true
    // hot: true,

    // disableHostCheck: true,
  }
})
