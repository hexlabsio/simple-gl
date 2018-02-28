export default class UniformInfo<T>{
    name: string
    position: WebGLUniformLocation
    mapper: (webGlContext: WebGLRenderingContext, position: WebGLUniformLocation, data: T) => void
    constructor(
        name: string,
        position: WebGLUniformLocation,
        mapper: (webGlContext: WebGLRenderingContext, position: WebGLUniformLocation, data: T) => void
    ){
        this.name = name
        this.position = position
        this.mapper = mapper
    }
}