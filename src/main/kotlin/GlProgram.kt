
import org.khronos.webgl.Float32Array
import org.khronos.webgl.WebGLProgram
import org.khronos.webgl.WebGLRenderingContext
import org.khronos.webgl.WebGLShader
import org.w3c.dom.HTMLCanvasElement

data class GlProgram(
        val renderingContext: WebGLRenderingContext,
        val program: WebGLProgram,
        val attributes: Map<String, AttributeInfo>,
        val uniforms: Map<String, UniformInfo<*>>
){

    fun updateAttribute(name: String, data: List<Float>){
        with(attributes[name]!!){
            renderingContext.bindBuffer(this.type.value, this.buffer)
            renderingContext.bufferData(this.type.value, Float32Array(data.toTypedArray()), WebGLRenderingContext.DYNAMIC_DRAW)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> updateUniform(name: String, data: T){
        with(uniforms[name]!! as UniformInfo<T>){
            stage()
            this.mapper(renderingContext, this.position, data)
        }
    }

    fun resize(x: Int, y: Int, width: Int, height: Int) = renderingContext.viewport(x, y, width, height)

    fun reconfigureViewport(){
        GlProgram.resize(renderingContext.canvas)
        resize(0,0, renderingContext.canvas.width, renderingContext.canvas.height)
    }

    fun stage() = renderingContext.useProgram(program)

    @JsName("render")
    fun render(type: Int, vertexCount: Int){
        attributes.values.forEach {
            renderingContext.bindBuffer(it.type.value, it.buffer)
            it.mapper(renderingContext, it.position)
        }
        renderingContext.enable(WebGLRenderingContext.DEPTH_TEST)
        renderingContext.drawArrays(type, 0, vertexCount)
    }

    fun renderTriangles(vertexCount: Int) = render(WebGLRenderingContext.TRIANGLES, vertexCount)

    companion object {

        private fun resize(canvas: HTMLCanvasElement){
            if(canvas.width != canvas.clientWidth || canvas.height != canvas.clientHeight){
                canvas.width = canvas.clientWidth
                canvas.height = canvas.clientHeight
            }
        }
    }
}

fun glProgramFrom(
        renderingContext: WebGLRenderingContext,
        vertexShader: String,
        fragmentShader: String,
        attributes: List<Attribute>,
        uniforms: List<Uniform<*>> = emptyList()
): GlProgram{
    val program = renderingContext.program(listOf(
            renderingContext.shader(WebGLRenderingContext.FRAGMENT_SHADER, fragmentShader),
            renderingContext.shader(WebGLRenderingContext.VERTEX_SHADER, vertexShader)
    ))
    return GlProgram(
            renderingContext,
            program,
            attributes =  attributes.map { it.name to renderingContext.attribute(program, it) }.toMap(),
            uniforms = uniforms.map { it.name to renderingContext.uniform(program, it) }.toMap()
    )
}


fun WebGLRenderingContext.shader(type: Int, source: String): WebGLShader{
    return this.createShader(type).also {
        this.shaderSource(it, source)
        this.compileShader(it)
        if(this.getShaderParameter(it, WebGLRenderingContext.COMPILE_STATUS) == false){
            this.deleteShader(it)
            throw Error("Could not create shader of type $type")
        }
    }!!
}

fun WebGLRenderingContext.program(shaders: List<WebGLShader>): WebGLProgram{
    return this.createProgram().also { program ->
        shaders.forEach { shader -> this.attachShader(program, shader) }
        this.linkProgram(program)
        if(this.getProgramParameter(program, WebGLRenderingContext.LINK_STATUS) == false){
            this.deleteProgram(program)
            throw Error("Could not create program")
        }
    }!!
}

fun WebGLRenderingContext.attribute(program: WebGLProgram, attribute: Attribute): AttributeInfo{
    val position = this.getAttribLocation(program, attribute.name)
    val buffer = this.createBuffer()!!
    this.bindBuffer(attribute.type.value, buffer)
    this.enableVertexAttribArray(position)
    return AttributeInfo(attribute.name, position, attribute.type, buffer, attribute.mapper)
}

fun <T> WebGLRenderingContext.uniform(program: WebGLProgram, uniform: Uniform<T>): UniformInfo<T>{
    return UniformInfo(uniform.name, this.getUniformLocation(program, uniform.name)!!, uniform.mapper)
}