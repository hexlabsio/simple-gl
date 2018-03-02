import AttributeInfo, { AttributeType } from './attribute-info'
import UniformInfo from './uniform-info';

export default class GLProgram{

    gl: WebGLRenderingContext
    program: WebGLProgram
    attributeInfo: Map<string, AttributeInfo>
    uniformInfo: Map<string, UniformInfo<any>>

    constructor(
        webGlContext: WebGLRenderingContext,
        vertextShaderSource: string,
        fragmentShaderSource: string,
        attributes: Map<string, {type: AttributeType, mapper: (webGlContext: WebGLRenderingContext, position: number) => void}>,
        uniforms: Map<string, {mapper: (webGlContext: WebGLRenderingContext, position: WebGLUniformLocation) => void}>
    ){
        this.gl = webGlContext
        this.program = this.compile(vertextShaderSource, fragmentShaderSource)
        this.attributeInfo = this.configureAttributes(attributes)
        this.uniformInfo = this.configureUniforms(uniforms)
    }

    private configureUniforms(uniforms: Map<string, {mapper: (webGlContext: WebGLRenderingContext, position: number, data: any) => void}>): Map<string, UniformInfo<any>>{
        let uniformInfo = new Map<string, UniformInfo<any>>()
        uniforms.forEach(
            (info, name) => {
                let position = this.gl.getUniformLocation(this.program, name);
                uniformInfo.set(name, new UniformInfo(name, position, info.mapper))
            }
        )
        return uniformInfo
    }

    private configureAttributes(
        attributes: Map<string, {type: AttributeType, mapper: (webGlContext: WebGLRenderingContext, position: WebGLUniformLocation) => void}>,
    ): Map<string, AttributeInfo>{
        let attributeInfo = new Map<string, AttributeInfo>()
        attributes.forEach(
            (info, name) => {
                let position = this.gl.getAttribLocation(this.program, name);
                let buffer = this.gl.createBuffer()
                this.gl.bindBuffer(info.type, buffer);
                this.gl.enableVertexAttribArray(position)
                attributeInfo.set(name, new AttributeInfo(name,position,info.type,buffer,info.mapper))
            }
        )
        return attributeInfo
    }


    createShader(type: any, source: string): WebGLShader {
        let shader = this.gl.createShader(type);
        this.gl.shaderSource(shader, source);
        this.gl.compileShader(shader);
        if (!this.gl.getShaderParameter(shader, this.gl.COMPILE_STATUS)) {
            console.log(this.gl.getShaderInfoLog(shader));
            this.gl.deleteShader(shader);
            throw new Error('Could not create shader of type ' + type)
        }
        return shader;
    }

  createProgram(vertexShader: WebGLShader, fragmentShader: WebGLShader): WebGLProgram {
    let program = this.gl.createProgram();
    this.gl.attachShader(program, vertexShader);
    this.gl.attachShader(program, fragmentShader);
    this.gl.linkProgram(program);
    let successful = this.gl.getProgramParameter(program, this.gl.LINK_STATUS);
    if (!this.gl.getProgramParameter(program, this.gl.LINK_STATUS)) {
        console.log(this.gl.getProgramInfoLog(program));
        this.gl.deleteProgram(program);
        throw new Error('Could not create program')
    }
    return program;
  }

  compile(vertextShaderSource: string, fragmentShaderSource: string): WebGLProgram{
    let vertexShader = this.createShader(this.gl.VERTEX_SHADER, vertextShaderSource);
    let fragmentShader = this.createShader(this.gl.FRAGMENT_SHADER, fragmentShaderSource);
    return this.createProgram(vertexShader, fragmentShader);
  }

  setViewport(x: number, y: number, width: number, height: number){
    this.gl.viewport(x,y,width,height);
  }
  
  setViewportDefaults(){
    GLProgram.resize(this.gl.canvas)
    this.setViewport(0, 0, this.gl.canvas.width, this.gl.canvas.height);
    
  }
  static resize(canvas: HTMLCanvasElement) {
    // Lookup the size the browser is displaying the canvas.
    var displayWidth  = canvas.clientWidth;
    var displayHeight = canvas.clientHeight;
   
    // Check if the canvas is not the same size.
    if (canvas.width  != displayWidth ||
        canvas.height != displayHeight) {
      // Make the canvas the same size
      canvas.width  = displayWidth;
      canvas.height = displayHeight;
    }
  }

  updateInput(name: string, data: number[]){
    let attributeInfo = this.attributeInfo.get(name);
    this.gl.bindBuffer(attributeInfo.type, attributeInfo.buffer)
    this.gl.bufferData(attributeInfo.type, new Float32Array(data), WebGLRenderingContext.DYNAMIC_DRAW);
  }

  updateUniform<T>(name: string, data: T){
    let uniformInfo = this.uniformInfo.get(name)
    this.gl.useProgram(this.program)
    uniformInfo.mapper(this.gl,uniformInfo.position, data)
  }

  stageProgram(){
    this.gl.useProgram(this.program)
  }

  clear(r: number, g: number, b: number, t: number){
    this.gl.clearColor(r, g, b, t)
    this.gl.clear(this.gl.COLOR_BUFFER_BIT)
  }

  render(type, vertexCount: number){
    this.attributeInfo.forEach(
        (info, name) => {
            this.gl.bindBuffer(info.type, info.buffer)
            info.mapper(this.gl, info.position)
        }
    )
    this.gl.drawArrays(type, 0, vertexCount)
  }
  
  renderTriangles(vertextCount: number){
    this.render(WebGLRenderingContext.TRIANGLES, vertextCount)
  }

  renderFan(vertextCount: number){
    this.render(WebGLRenderingContext.TRIANGLE_FAN, vertextCount)
  }

  static vertexPointers = (size: number) =>  (gl,position) => gl.vertexAttribPointer(position, size, WebGLRenderingContext.FLOAT, false, 0, 0)
}