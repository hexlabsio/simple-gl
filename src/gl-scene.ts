import GLObject from "./gl-object"
import GLProgram from "./gl-program"
import Vertex from "./vertex"
import { AttributeType } from "./attribute-info"

export default class GLScene{
    objects: Array<GLObject>
    program: GLProgram
    
    constructor(webGlContext: WebGLRenderingContext, vertexShader: string, fragmentShader: string, objects: Array<GLObject>, uniforms: Map<string, any>){
        this.objects = objects
        this.program = new GLProgram(webGlContext, vertexShader, fragmentShader, Vertex.attributeMappings(),uniforms)
    }

    render(){
        let verticies = flatMap(this.objects, (object: GLObject) => object.verticies())
        let attributeValues = new Map<string, number[]>()
        verticies.forEach(
            (vertex: Vertex) => vertex.attributeValues().forEach( 
                (value, key) => {
                    let floats = []
                    if(attributeValues.has(key)) floats = attributeValues.get(key)
                    floats = floats.concat(value)
                    attributeValues.set(key, floats)
                }
            )
        )
        this.program.setViewportDefaults()
        attributeValues.forEach((value, key) =>  this.program.updateInput(key, value))
        this.program.render(verticies.length)
    }
}

function flatMap<T, R>(array: Array<T>, f: (item: T) => Array<R>): Array<R> {
    return array.reduce((ys: Array<R>, x: T) => {
        return ys.concat(f(x))
    }, [])
}