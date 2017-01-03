var webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var fs = require('fs')

module.exports = {
  entry: {
    app: './app/scripts/cudu.ts'
  },
  output: {
    path: 'app',
    filename: 'bundle.[hash:7].js'
  },
  devtool: 'source-map',
  resolve: {
    extensions: ['', '.webpack.js', '.web.js', '.ts', '.js']
  },
  module: {
    loaders: [{ test: /\.tsx?$/, loader: 'ts-loader' }],
    preLoaders: [{ test: /\.js$/, loader: 'source-map-loader' }]
  },
  plugins: [    
    new webpack.BannerPlugin(fs.readFileSync('./header.txt', 'utf8'), { entryOnly: true, raw: true }),
    new HtmlWebpackPlugin({
      template: 'app/index.ejs',
      filename: 'index.html'
    })    
  ]
};