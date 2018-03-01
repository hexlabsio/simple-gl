import GLObject, { GLOptimizedType } from './gl-object'
import Vertex from './vertex'
import { Vector2D } from './vector';

export default class GLCircle extends GLObject{
    vertices: Array<Vertex>

    constructor(vertices: number, radius: number, center: Vertex){
        super()
        let rotation = 2.0 * Math.PI / vertices
        var direction = new Vector2D(1,0)
        let start = new Vector2D(center.position.x + direction.x * radius, center.position.y + direction.y * radius)
        let verts = [center, new Vertex(start)]
        for(let i = 0;i<vertices-1;i++){
            direction = new Vector2D(direction.x * Math.cos(-rotation) - direction.y * Math.sin(-rotation),direction.y * Math.cos(-rotation) + direction.x * Math.sin(-rotation))
            let next = new Vector2D(center.position.x + direction.x * radius, center.position.y + direction.y * radius)
            verts.push(new Vertex(next))
        }
        verts.push(new Vertex(new Vector2D(start.x, start.y)))
        this.vertices = verts
    }

    optimizedType(){
        return GLOptimizedType.TRIANGLE_FAN
    }
    
    verticies(): Array<Vertex>{
        return this.vertices
    }
}