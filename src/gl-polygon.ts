import GLObject, { GLOptimizedType } from './gl-object'
import Vertex from './vertex'
import { Vector3D } from './vector';

export default class GLPolygon extends GLObject{
    vertices: Array<Vertex>
    _lines: number[] = []

    constructor(vertices: number, radius: number, center: Vector3D){
        super()
        this.position = new Vector3D(0,0,0)
        let rotation = 2.0 * Math.PI / vertices
        var direction = new Vector3D(1,0,0)
        let start = new Vector3D(this.position.x + direction.x * radius, this.position.y + direction.y * radius, this.position.z + direction.z)
        let verts = [new Vertex(this.position), new Vertex(start)]
        for(let i = 0;i<vertices-1;i++){
            direction = new Vector3D(direction.x * Math.cos(-rotation) - direction.y * Math.sin(-rotation),direction.y * Math.cos(-rotation) + direction.x * Math.sin(-rotation), direction.z)
            let next = new Vector3D(this.position.x + direction.x * radius, this.position.y + direction.y * radius, this.position.z + direction.z * radius)
            verts.push(new Vertex(next))
        }
        verts.push(new Vertex(new Vector3D(start.x, start.y, start.z)))
        for(let i = 1;i<verts.length;i++){
            this._lines.push(i)
        }
        this.vertices = verts
        this.transform._translation = new Vector3D(center.x, center.y, center.z)
    }

    optimizedType(){
        return GLOptimizedType.TRIANGLE_FAN
    }
    lines(): number[]{
        return this._lines
    }
    verticies(): Array<Vertex>{
        return this.vertices
    }
}