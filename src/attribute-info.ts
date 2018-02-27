export default class AttributeInfo{
    name: string
    position: number
    type: AttributeType
    buffer: WebGLBuffer
    mapper: (webGlContext: WebGLRenderingContext, position: number) => void
    constructor(
        name: string,
        position: number,
        type: AttributeType,
        buffer: WebGLBuffer,
        mapper: (webGlContext: WebGLRenderingContext, position: number) => void
    ){
        this.name = name
        this.position = position
        this.type = type
        this.buffer = buffer
        this.mapper = mapper
    }
}
export enum AttributeType{
    ARRAY_BUFFER = 34962
}