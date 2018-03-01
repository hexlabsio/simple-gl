export { default as GLProgram } from './src/gl-program'
export { default as AttributeInfo, AttributeType } from './src/attribute-info'
export { default as UniformInfo } from './src/uniform-info'
export { default as GLObject } from './src/gl-object'
export { default as GLScene } from './src/gl-scene'
export { default as Vertex } from './src/vertex'
export { Vector2D } from './src/vector'
export { Matrix3 } from './src/matrix'
export { Transform2D } from './src/transform'
export { default as GLTriangle } from './src/gl-triangle'
export { default as GLCircle } from './src/gl-circle'

export function webGlContextFrom(canvas: HTMLCanvasElement): WebGLRenderingContext{
    let gl = canvas.getContext('webgl')
    if(!gl){
        console.error('No WebGL Capabilities!');
        throw new Error('No WebGL Capabilities!');
    }
    return gl;
}

export function aspect(fieldOfViewInRadians: number, width: number, height: number): number[]{
    let f = Math.tan(Math.PI * 0.5 - 0.5 * fieldOfViewInRadians);
    let aspect = width/height
     return [
      f/aspect, 0, 0, 0,
      0, f, 0, 0,
      0, 0, 1, 0,
      0, 0,0 , 1
    ];
  }

interface Array<T> {
    flatMap<E>(callback: (t: T) => Array<E>): Array<E>
}

Object.defineProperty(Array.prototype, 'flatMap', {
    value: function(f: Function) {
        return this.reduce((ys: any, x: any) => {
            return ys.concat(f.call(this, x))
        }, [])
    },
    enumerable: false,
})