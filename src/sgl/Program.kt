package sgl
import org.khronos.webgl.Float32Array
import org.khronos.webgl.WebGLProgram
import org.khronos.webgl.WebGLRenderingContext
import org.khronos.webgl.WebGLShader
import org.w3c.dom.HTMLCanvasElement
import kotlin.browser.window
import kotlin.math.floor

data class Program(
        val renderingContext: WebGLRenderingContext,
        val program: WebGLProgram,
        val attributes: Map<String, AttributeInfo>,
        val uniforms: Map<String, UniformInfo<*>>
){

    @JsName("updateAttribute")
    fun updateAttribute(name: String, data: List<Float>){
        with(attributes[name]!!){
            renderingContext.bindBuffer(this.type.value, this.buffer)
            renderingContext.bufferData(this.type.value, Float32Array(data.toTypedArray()), WebGLRenderingContext.DYNAMIC_DRAW)
        }
    }

    @JsName("updateUniform")
    @Suppress("UNCHECKED_CAST")
    fun <T> updateUniform(name: String, data: T){
        with(uniforms[name]!! as UniformInfo<T>){
            stage()
            this.mapper(renderingContext, this.position, data)
        }
    }

    @JsName("updateProjection")
    fun updateProjection(projection: Matrix4){
        updateUniform("projectionMatrix", projection.toTypedArray())
    }
    @JsName("updateWorld")
    fun updateWorld(transform: Matrix4){
        updateUniform("worldMatrix", transform.toTypedArray())
    }

    @JsName("resize")
    fun resize(x: Int, y: Int, width: Int, height: Int) = renderingContext.viewport(x, y, width, height)

    fun reconfigureViewport(width: Int, height: Int){
        resize(renderingContext.canvas, width, height)
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
    @JsName("renderTriangles")
    fun renderTriangles(vertexCount: Int) = render(WebGLRenderingContext.TRIANGLES, vertexCount)

    companion object {

        private fun resize(canvas: HTMLCanvasElement, width: Int, height: Int){

            val realToCSSPixels = window.devicePixelRatio
            val displayWidth  = floor(width  * realToCSSPixels).toInt()
            val displayHeight = floor(height * realToCSSPixels).toInt()

            if (canvas.width  != displayWidth || canvas.height != displayHeight) {
                canvas.width  = displayWidth
                canvas.height = displayHeight
            }
        }
    }
}

@JsName("simpleProgramFrom")fun simpleProgramFrom(renderingContext: WebGLRenderingContext) = programFrom(
        renderingContext,
        simpleVertexShader,
        simpleFragmentShader,
        vertex3dAttributeMappings()
)

@JsName("projectedProgramFrom")fun projectedProgramFrom(renderingContext: WebGLRenderingContext) = programFrom(
        renderingContext,
        projectionVertexShader,
        simpleFragmentShader,
        vertex3dAttributeMappings(),
        listOf(
                Uniform<Array<Float>>(
                        name = "projectionMatrix",
                        mapper = { gl, position, data ->
                            gl.uniformMatrix4fv(position, false, data)
                        }
                ),
                Uniform(
                        name = "worldMatrix",
                        mapper = { gl, position, data ->
                            gl.uniformMatrix4fv(position, false, data)
                        }
                )
        )
)

@JsName("programFrom")fun programFrom(
        renderingContext: WebGLRenderingContext,
        vertexShader: String,
        fragmentShader: String,
        attributes: List<Attribute>,
        uniforms: List<Uniform<*>> = emptyList()
): Program {
    val program = renderingContext.program(listOf(
            renderingContext.shader(WebGLRenderingContext.FRAGMENT_SHADER, fragmentShader),
            renderingContext.shader(WebGLRenderingContext.VERTEX_SHADER, vertexShader)
    ))
    return Program(
            renderingContext,
            program,
            attributes = attributes.map { it.name to renderingContext.attribute(program, it) }.toMap(),
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

fun WebGLRenderingContext.attribute(program: WebGLProgram, attribute: Attribute): AttributeInfo {
    val position = this.getAttribLocation(program, attribute.name)
    val buffer = this.createBuffer()!!
    this.bindBuffer(attribute.type.value, buffer)
    this.enableVertexAttribArray(position)
    return AttributeInfo(attribute.name, position, attribute.type, buffer, attribute.mapper)
}

fun <T> WebGLRenderingContext.uniform(program: WebGLProgram, uniform: Uniform<T>): UniformInfo<T> {
    return UniformInfo(uniform.name, this.getUniformLocation(program, uniform.name)!!, uniform.mapper)
}

val simpleVertexShader =    "attribute vec4 vertex_position;\n" +
                            "attribute vec4 vertex_color;\n" +
                            "varying vec4 pixelColor;\n" +

                            "void main() {\n" +
                            "    vec4 pos = vertex_position;\n" +
                            "    pixelColor = vertex_color;\n" +
                            "    gl_Position = pos;\n" +
                            "}"
val simpleFragmentShader =  "precision mediump float;\n" +
                            "varying vec4 pixelColor;\n" +
                            "void main() {\n" +
                            "    gl_FragColor = pixelColor; \n" +
                            "}"

val projectionVertexShader= "attribute vec4 vertex_position;\n" +
                            "attribute vec4 vertex_color;\n" +
                            "varying vec4 pixelColor;\n" +
                            "uniform mat4 worldMatrix;\n" +
                            "uniform mat4 projectionMatrix;\n" +
                            "void main() {\n" +
                            "    vec4 worldPos = worldMatrix * vertex_position;\n" +
                            "    vec4 screenPos = projectionMatrix * vec4(worldPos.x, worldPos.y, worldPos.z, 1.0);\n" +
                            "    pixelColor = vertex_color;\n" +
                            "    gl_Position = screenPos;\n" +
                            "}"