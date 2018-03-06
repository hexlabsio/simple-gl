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
        this.objects.forEach((object: GLObject) => object.render(program))
    }
}