import Vertex from './vertex'

export default abstract class GLObject{
    abstract verticies(): Array<Vertex>
    setColor(color: number[]){
        this.verticies().forEach((vertex: Vertex) => vertex.color = color)
    }
}