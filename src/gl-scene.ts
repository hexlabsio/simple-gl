import GLObject, { GLOptimizedType } from "./gl-object"
import GLProgram from "./gl-program"
import Vertex from "./vertex"
import { AttributeType } from "./attribute-info"

export default class GLScene{
    objects: Array<GLObject>
    
    constructor(objects: Array<GLObject>){
        this.objects = objects
    }

    render(program: GLProgram){
        program.setViewportDefaults()
        this.objects.forEach(
            (object: GLObject) => {
                let verticies = object.transformedVerticies()
                let attributeValues = new Map<string, number[]>()
                verticies.forEach(
                    (vertex: Vertex) => {
                        vertex.attributeValues().forEach(
                            (value, key) => {
                                let floats = []
                                if(attributeValues.has(key)) floats = attributeValues.get(key)
                                floats = floats.concat(value)
                                attributeValues.set(key, floats)
                            }
                        )
                    }
                )
                attributeValues.forEach((value, key) =>  program.updateInput(key, value))
                program.render(object.optimizedType(), verticies.length)
            }
        )
    }
}