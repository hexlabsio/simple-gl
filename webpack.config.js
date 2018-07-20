const KotlinWebpackPlugin = require('@jetbrains/kotlin-webpack-plugin');
const path = require('path')

module.exports = {
  entry: 'simple-gl',

  resolve: {
    modules: ['bin/build', 'node_modules'],
  },

  output: {
    path: __dirname + '/bin/bundle',
    filename: 'simple-gl.js',
  },

  module: {
      rules: [
        {
          test: /\.js$/,
          include: path.resolve(__dirname, '/bin'),
          exclude: [ /kotlin\.js$/ ],
          use: ['source-map-loader'],
          enforce: 'pre'
        }
      ]
    },

  plugins: [
    new KotlinWebpackPlugin({
      src: __dirname,
      output: 'bin/test',
      moduleName: 'simple-gl',
      librariesAutoLookup: true,
      packagesContents: [require('./package.json')],
    }),
    new KotlinWebpackPlugin({
          src: __dirname + '/src',
          output: 'bin/build',
          moduleName: 'simple-gl',
          sourceMapEmbedSources: 'always',
          metaInfo: true,
          sourceMaps: true,
          librariesAutoLookup: true,
          packagesContents: [require('./package.json')],
        }),
  ],
};