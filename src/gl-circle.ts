import GLObject from './gl-object'
import Vertex from './vertex'
import { Vector2D } from './vector';

export default class GLCircle extends GLObject{
    vertices: Array<Vertex>
    center: Vertex
    constructor(vertices: number, radius: number, center: Vertex){
        super()
        this.center = center
        let rotation = 2.0 * Math.PI / vertices
        var direction = new Vector2D(1,0)
        let start = new Vector2D(center.position.x + direction.x * radius, center.position.y + direction.y * radius)
        let verts = [new Vertex(start)]
        for(let i = 0;i<vertices-1;i++){
            direction = new Vector2D(direction.x * Math.cos(rotation) - direction.y * Math.sin(rotation),direction.y * Math.cos(rotation) + direction.x * Math.sin(rotation))
            let next = new Vector2D(center.position.x + direction.x * radius, center.position.y + direction.y * radius)
            verts.push(new Vertex(next))
        }
        this.vertices = verts
    }
    
    verticies(): Array<Vertex>{
        let vertices = []
        for(let i = 0;i<this.vertices.length;i++){
            let next = i == (this.vertices.length-1) ? this.vertices[0] : this.vertices[i+1]
            vertices.push(this.center, this.vertices[i], next)
        }
        return vertices
    }
}