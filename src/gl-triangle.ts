import GLObject from './gl-object'
import Vertex from './vertex'

export default class GLTriangle extends GLObject{
    a: Vertex
    b: Vertex
    c: Vertex
    constructor(a: Vertex, b: Vertex, c: Vertex){
        super()
        this.a = a
        this.b = b
        this.c = c
    }
    verticies(): Array<Vertex>{
        return [this.a, this.b, this.c]
    }
}