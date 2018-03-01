import Vertex from './vertex'
import { Transform2D } from './transform';

export enum GLOptimizedType{
    TRIANGLES = WebGLRenderingContext.TRIANGLES,
    TRIANGLE_FAN = WebGLRenderingContext.TRIANGLE_FAN,
    LINES =  WebGLRenderingContext.LINES
}

export default abstract class GLObject{
    transform: Transform2D = new Transform2D()
    abstract verticies(): Array<Vertex>
    optimizedType(): GLOptimizedType { return GLOptimizedType.TRIANGLES }
    setColor(color: number[]){
        this.verticies().forEach((vertex: Vertex) => vertex.color = color)
    }
    transformedVerticies(): Array<Vertex>{
        let transformationMatrix = this.transform.transform()
        return this.verticies().map(
            (vertex: Vertex) => new Vertex(transformationMatrix.transform(vertex.position))
        )
    }
}