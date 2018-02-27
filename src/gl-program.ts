import AttributeInfo, { AttributeType } from './attribute-info'

export default class GLProgram{

    gl: WebGLRenderingContext
    program: any
    attributeInfo: Map<string, AttributeInfo>

    constructor(
        webGlContext: WebGLRenderingContext,
        vertextShaderSource: string,
        fragmentShaderSource: string,
        attributes: Map<string, {type: AttributeType, mapper: (webGlContext: WebGLRenderingContext, position: number) => void}>
    ){
        this.gl = webGlContext
        this.program = this.compile(vertextShaderSource, fragmentShaderSource)
        this.configure(attributes)
    }

    private configure(attributes: Map<string, {type: AttributeType, mapper: (webGlContext: WebGLRenderingContext, position: number) => void}>){
        let gl = this.gl
        let attributeInfo = new Map<string, AttributeInfo>()
        let program = this.program
        attributes.forEach(
            function(info, name){
                let position = gl.getAttribLocation(program, name);
                let buffer = gl.createBuffer()
                gl.bindBuffer(info.type, buffer);
                attributeInfo.set(name, new AttributeInfo(name,position,info.type,buffer,info.mapper))
            }
        )
        this.attributeInfo = attributeInfo
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
    this.setViewport(0, 0, this.gl.canvas.width, this.gl.canvas.height);
  }

  updateInput(name: string, data: number[]){
    let attributeInfo = this.attributeInfo.get(name);
    this.gl.bindBuffer(attributeInfo.type, attributeInfo.buffer)
    this.gl.bufferData(attributeInfo.type, new Float32Array(data), this.gl.STATIC_DRAW);
}
  
  render(vertextCount: number){
    this.gl.clearColor(0, 0, 0, 0)
    this.gl.clear(this.gl.COLOR_BUFFER_BIT)
    this.gl.useProgram(this.program)
    let program = this
    this.attributeInfo.forEach(
        (info, name) => {
            program.gl.enableVertexAttribArray(info.position)
            program.gl.bindBuffer(info.type, info.buffer)
            info.mapper(program.gl, info.position)
        }
    )
    this.gl.drawArrays(this.gl.TRIANGLES, 0, vertextCount)
  }


  static vertexPointers = (size: number) =>  (gl,position) => gl.vertexAttribPointer(position, size, gl.FLOAT, false, 0, 0)
}