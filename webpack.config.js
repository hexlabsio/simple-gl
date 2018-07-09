const KotlinWebpackPlugin = require('@jetbrains/kotlin-webpack-plugin');
const path = require('path');

module.exports = {
  entry: 'kotlinApp', // kotlinApp is the default module name

  resolve: {
    modules: ['kotlin_build', 'node_modules'],
  },

  module: {
    rules: [
      {
        test: /\.js$/,
        include: path.resolve(__dirname, '../kotlin_build'),
        exclude: [
          /kotlin\.js$/,
        ],
        use: ['source-map-loader'],
        enforce: 'pre',
      },
    ],
  },

  output: {
    path: __dirname + '/build',
    filename: 'simple-gl-min.js',
  },

  plugins: [
    new KotlinWebpackPlugin({
      src: __dirname,
      verbose: true,
      optimize: true,
      librariesAutoLookup: true,
      packagesContents: [require('./package.json')],
    }),
  ],
};