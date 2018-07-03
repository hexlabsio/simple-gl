package simpleGl

import org.khronos.webgl.WebGLRenderingContext

open class GlObject(val localVertices: List<Vertex3d>){
    var localPosition: Vector3 = Vector3(0f,0f,0f)
    var transformation: Matrix4 = Matrix4.identity()

    fun position() = transformation * localPosition
    fun vertices() = localVertices.map { it.copy(position = transformation * it.position) }

    fun render(program: GlProgram){
        vertices().let { vertices ->
            vertices.flatMap { it.attributes().entries }
                    .groupBy { it.key }
                    .map { it.key to it.value.flatMap { it.value } }
                    .forEach { program.updateAttribute(it.first, it.second) }
            program.render(WebGLRenderingContext.TRIANGLES, vertices.size)
        }
    }

}