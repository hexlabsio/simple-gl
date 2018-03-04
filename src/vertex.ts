import { Vector2D } from "./vector"
import { AttributeType } from "./attribute-info"

export default class Vertex{
    position: Vector2D
    color: number[] = [0,0,0, 1]
    constructor(position: Vector2D, color: number[] = [0,0,0, 1]){
       this.position = position
       this.color = color
    }
    attributeValues(): Map<string, number[]>{
        let attributes = new Map<string, number[]>()
        attributes.set(Vertex.positionAttribute, [this.position.x, this.position.y])
        attributes.set(Vertex.colorAttribute, this.color)
        return attributes
    }
    static size = 2
    static positionAttribute = "vertex_position"
    static colorAttribute = "vertex_color"
    static attributeMappings(): Map<string, {type: number, mapper: (gl, position) => void}>{
        let attributes = new Map<string, {type: number, mapper: (gl, position) => void}>()
        attributes.set(Vertex.positionAttribute, { type: AttributeType.ARRAY_BUFFER, mapper:  (gl,position) => gl.vertexAttribPointer(position, Vertex.size, gl.FLOAT, false, 0, 0) });
        attributes.set(Vertex.colorAttribute, { type: AttributeType.ARRAY_BUFFER, mapper:  (gl,position) => gl.vertexAttribPointer(position, 4, gl.FLOAT, false, 0, 0) });
        return attributes
    }
    
}