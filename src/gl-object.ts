import Vertex from './vertex'
import { Transform3D } from './transform'
import { Vector3D } from './vector'
import GLProgram from './gl-program'

export enum GLOptimizedType{
    TRIANGLES = WebGLRenderingContext.TRIANGLES,
    TRIANGLE_FAN = WebGLRenderingContext.TRIANGLE_FAN,
    LINES =  WebGLRenderingContext.LINES,
    LINE_STRIP = WebGLRenderingContext.LINE_STRIP
}

export default abstract class GLObject{
    transform: Transform3D = new Transform3D()
    position: Vector3D = new Vector3D(0,0,0)
    lineColor: number[] = [0,0,0,0.2]
    _cachedPosition: Vector3D
    _cachedVerticies: Array<Vertex>
    abstract verticies(): Array<Vertex>
    lines(): number[]{ return [] }
    optimizedType(): GLOptimizedType { return GLOptimizedType.TRIANGLES }
    setColor(color: number[]){
        this.verticies().forEach((vertex: Vertex) => vertex.color = color)
    }
    transformedPosition(): Vector3D{
        //if(this.transform.changed){
            let transformationMatrix = this.transform.transform()
            this._cachedPosition = this.transform.transform().transform(this.position)
        //}
        return this._cachedPosition
    }
    transformedVerticies(): Array<Vertex>{
        //if(this.transform.changed || !this._cachedVerticies){
            let transformationMatrix = this.transform.transform()
            this._cachedVerticies = this.verticies().map(
                (vertex: Vertex) => new Vertex(transformationMatrix.transform(vertex.position),vertex.color)
            )
        //}
        return this._cachedVerticies
    }

    render(program: GLProgram){
        let verticies = this.transformedVerticies()
        this.renderWith(verticies, this.optimizedType(), program)
        let lines = this.lines()
        if(lines.length > 1){
            this.renderWith(
                this.lines().map(
                    (index: number)=> new Vertex(verticies[index].position, this.lineColor)
                ),
            GLOptimizedType.LINE_STRIP, program)
        }
    }

    renderWith(verticies: Array<Vertex>, type: GLOptimizedType, program: GLProgram){
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
        program.render(type, verticies.length)
    }
}