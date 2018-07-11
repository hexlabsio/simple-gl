const KotlinWebpackPlugin = require('@jetbrains/kotlin-webpack-plugin');

module.exports = {
  entry: 'simple-gl',

  resolve: {
    modules: ['bin/build', 'node_modules'],
  },

  output: {
    path: __dirname + '/bin/bundle',
    filename: 'simple-gl.js',
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
          verbose: true,
          moduleName: 'simple-gl',
          optimize: false,
          sourceMapEmbedSources: 'always',
          metaInfo: true,
          sourceMaps: true,
          librariesAutoLookup: true,
          packagesContents: [require('./package.json')],
        }),
  ],
};