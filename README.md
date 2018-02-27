# simple-gl
Simple WebGL Wrapper


# Usage
```bash
npm install -S simple-gl
```

```typescript
import { GLScene,  GLCircle, GLObject, GLProgram, AttributeType, webGlContextFrom, Vertex, Vector2D } from 'simple-gl'

let vertexShader = require('./src/vertex-shader.glsl');
let fragmentShader = require('./src/fragment-shader.glsl');

(() => {
    let scene = new GLScene(
        webGlContextFrom(document.getElementById('display') as HTMLCanvasElement),
        vertexShader,
        fragmentShader,
        [new GLCircle(100, 0.5,new Vertex(new Vector2D((Math.random()*2-1)*10,(Math.random()*2-1)*10)))]
    )
    setInterval( () => scene.render(), 1)
})()
```
