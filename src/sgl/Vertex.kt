package sgl

import org.khronos.webgl.WebGLRenderingContext


data class Vertex3d(
        var position: Vector3 = Vector3(x = 0f, y = 0f, z = 0f),
        var color: Color = Color(r = 0f, g = 0f, b = 0f, a = 1f)
){
    fun attributes() = mapOf( "vertex_position" to position.components(), "vertex_color" to color.components() )

}

fun vertex3dAttributeMappings() = listOf(
        Attribute("vertex_position", AttributeType.ARRAY_BUFFER) { context, position ->
            context.vertexAttribPointer(position, 3, WebGLRenderingContext.FLOAT, false, 0, 0)
        },
        Attribute("vertex_color", AttributeType.ARRAY_BUFFER) { context, position ->
            context.vertexAttribPointer(position, 4, WebGLRenderingContext.FLOAT, false, 0, 0)
        }
)

