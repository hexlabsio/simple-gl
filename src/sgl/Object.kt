package sgl

import org.khronos.webgl.WebGLRenderingContext

open class Object(val localVertices: List<Vertex3d>): Renderable{
    var localPosition: Vector3 = Vector3(0f,0f,0f)
    //var transformation: sgl.Matrix4 = sgl.Matrix4.identity()
    private var dirty = true
    private var cachedTransform: Matrix4 = Matrix4.identity()
    var rotation: Matrix4 = Matrix4.identity()
        set(value) {dirty = true; field = value}
    var translation: Matrix4 = Matrix4.identity()
        set(value) {dirty = true; field = value}
    var scale: Matrix4 = Matrix4.identity()
        set(value) {dirty = true; field = value}

    fun transformation(): Matrix4{
        if(dirty){
            dirty = false
            cachedTransform =  translation *  scale * rotation
        }
        return cachedTransform
    }

    fun position() = transformation() * localPosition
    fun vertices(): List<Vertex3d> = localVertices.map { it.copy(position = transformation() * it.position) }
    fun center() = transformation() * (localVertices.fold(Vector3(0f,0f,0f)) { acc, vertex -> acc + vertex.position } * (1f / localVertices.count()))

    open fun optimizedType() = WebGLRenderingContext.TRIANGLES

    open fun border() = Lines(vertices().map { it.copy(color = Color(0f,0f,0f,1f)) })

    fun color(color: Color){
        localVertices.forEach { it.color = color }
    }

    override fun render(program: Program){
        vertices().let { vertices ->
            vertices.flatMap { it.attributes().entries }
                    .groupBy { it.key }
                    .map { it.key to it.value.flatMap { it.value } }
                    .forEach { program.updateAttribute(it.first, it.second) }
            program.render(optimizedType(), vertices.size)
        }
    }

    @JsName("renderBorder")
    fun renderBorder(program: Program) = border().render(program)

}