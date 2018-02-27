# simple-gl
Simple WebGL Wrapper


# Usage
```bash
npm install -S simple-gl
```

```typescript
import { GLScene,  GLCircle, webGlContextFrom, Vertex, Vector2D } from 'simple-gl'

let vertexShader = require('./src/vertex-shader.glsl');
let fragmentShader = require('./src/fragment-shader.glsl');

(() => {
    let scene = new GLScene(
        webGlContextFrom(document.getElementById('display') as HTMLCanvasElement),
        vertexShader,
        fragmentShader,
        [new GLCircle(10, 0.2,new Vertex(new Vector2D(0,0))]
    )
    setInterval( () => scene.render(), 1)
})()
```
