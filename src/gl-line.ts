import GLObject from './gl-object'
import Vertex from './vertex'
import { GLOptimizedType } from './gl-object'

export default class GLLine extends GLObject{
    a: Vertex
    b: Vertex
    constructor(a: Vertex, b: Vertex){
        super()
        this.a = a
        this.b = b
    }
    optimizedType(): GLOptimizedType { return GLOptimizedType.LINES }
    verticies(): Array<Vertex>{
        return [this.a, this.b]
    }
}