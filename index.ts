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
export { default as GLPolygon } from './src/gl-polygon'
export { default as GLLine } from './src/gl-line'

export function webGlContextFrom(canvas: HTMLCanvasElement): WebGLRenderingContext{
    let gl = canvas.getContext('webgl')
    if(!gl){
        console.error('No WebGL Capabilities!');
        throw new Error('No WebGL Capabilities!');
    }
    return gl;
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