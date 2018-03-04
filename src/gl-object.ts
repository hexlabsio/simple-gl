import Vertex from './vertex'
import { Transform2D } from './transform';
import { Vector2D } from '..';

export enum GLOptimizedType{
    TRIANGLES = WebGLRenderingContext.TRIANGLES,
    TRIANGLE_FAN = WebGLRenderingContext.TRIANGLE_FAN,
    LINES =  WebGLRenderingContext.LINES
}

export default abstract class GLObject{
    transform: Transform2D = new Transform2D()
    position: Vector2D = new Vector2D(0,0)
    abstract verticies(): Array<Vertex>
    optimizedType(): GLOptimizedType { return GLOptimizedType.TRIANGLES }
    setColor(color: number[]){
        this.verticies().forEach((vertex: Vertex) => vertex.color = color)
    }
    transformedPosition(): Vector2D{
        let transformationMatrix = this.transform.transform()
        return this.transform.transform().transform(this.position)
    }
    transformedVerticies(): Array<Vertex>{
        let transformationMatrix = this.transform.transform()
        return this.verticies().map(
            (vertex: Vertex) => new Vertex(transformationMatrix.transform(vertex.position),vertex.color)
        )
    }
}