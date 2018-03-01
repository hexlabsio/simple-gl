# simple-gl [![CircleCI](https://circleci.com/gh/bobjamin/simple-gl/tree/master.svg?style=svg)](https://circleci.com/gh/bobjamin/simple-gl/tree/master)
Simple WebGL Wrapper 


# Usage
```bash
npm install -S simple-gl
```

```typescript
import { GLScene,  GLCircle, webGlContextFrom, Vertex, Vector2D } from 'simple-gl'

let vertexShader = require('./src/vertex-shader.glsl');
let fragmentShader = require('./src/fragment-shader.glsl');

function render(scene: GLScene, time: number){
    scene.render()
    requestAnimationFrame(render(scene))
}

(() => {
    render(new GLScene(
        webGlContextFrom(document.getElementById('display') as HTMLCanvasElement),
        vertexShader,
        fragmentShader,
        [new GLCircle(10, 0.2,new Vertex(new Vector2D(0,0))]
    ))
})()
```
