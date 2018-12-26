package sgl

import org.khronos.webgl.WebGLRenderingContext

open class Object(val localVertices: List<Vertex3d>, val transformation: Transform = Transform()): Renderable, Transformable{
    var localPosition: Vector3 = Vector3.zero
    override fun transform(vector: Vector3) = transformation.transform(vector)
    override fun translate(translation: Vector3) = transformation.translate(translation)
    override fun rotate(angle: Float, axis: Vector3) = transformation.rotate(angle, axis)
    override fun rotate(about: Vector3, angle: Float, axis: Vector3) = transformation.rotate(about, angle, axis)
    override fun scale(scale: Vector3) = transformation.scale(scale)
    fun position() = transform(localPosition)
    fun vertices(): List<Vertex3d> = localVertices.map { it.copy(position = transform(it.position)) }
    fun center() = transform(localVertices.fold(Vector3(0f,0f,0f)) { acc, vertex -> acc + vertex.position } * (1f / localVertices.count()))

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