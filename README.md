# simple-gl
Simple WebGL Wrapper


# Usage
```bash
npm install -S simple-gl
```

```typescript
import { GLProgram, AttributeType, webGlContextFrom } from 'simple-gl'

(function(){
    let attributes = new Map()
    attributes.set("a_position", { type: AttributeType.ARRAY_BUFFER, mapper: GLProgram.vertexPointers(2) });
    attributes.set("a_color", { type: AttributeType.ARRAY_BUFFER, mapper: GLProgram.vertexPointers(4) });

    let program = new GLProgram(
        webGlContextFrom(document.getElementById('display') as HTMLCanvasElement),
        (document.getElementById('vertex-shader') as HTMLScriptElement).text,
        (document.getElementById('fragment-shader') as HTMLScriptElement).text,
        attributes
    )
    program.setViewportDefaults();
    
    var r1 = Math.random();
    var b1 = Math.random();
    var g1 = Math.random();
    program.updateInput("a_color", [ r1, b1, g1, 1,
        r1, b1, g1, 1,
        r1, b1, g1, 1,
        r1, b1, g1, 1,
        r1, b1, g1, 1,
        r1, b1, g1, 1])
    var x = 0
    setInterval(
        function(){
            
            program.updateInput("a_position", [
                x, 0,
                x, 0.5,
                0.7 + x, 0,
                x, 0.6,
                x, 1.1,
                0.7 + x, 0.6,
              ])
              
            program.render(6)
            //x += 0.001
        }, 1);
})()
```
