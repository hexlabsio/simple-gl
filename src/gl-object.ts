import Vertex from './vertex'

export enum GLOptimizedType{
    TRIANGLES = WebGLRenderingContext.TRIANGLES,
    TRIANGLE_FAN = WebGLRenderingContext.TRIANGLE_FAN,
    LINES =  WebGLRenderingContext.LINES
}

export default abstract class GLObject{
    abstract verticies(): Array<Vertex>
    optimizedType(): GLOptimizedType { return GLOptimizedType.TRIANGLES }
    setColor(color: number[]){
        this.verticies().forEach((vertex: Vertex) => vertex.color = color)
    }
}