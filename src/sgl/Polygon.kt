package sgl

import org.khronos.webgl.WebGLRenderingContext
import kotlin.math.*

open class Polygon(val sides: Int, val radius: Float, val startDirection: Vector3 = Vector3(1f, 0f, 0f))
    : Object(verticesFor(sides, radius, startDirection)){

    override fun optimizedType() = WebGLRenderingContext.TRIANGLE_FAN
    private fun inFront(v: Vector3) = v.copy(z = v.z - 0.001f)
    override fun border() = with(vertices()){ Lines(subList(1,size).map { it.copy(color = Color(0f,0f,0f,1f)) }) }
    fun transformedRadius() = transform(Vector3(0f,1f,0f)).y - transformation.translationVector.y
    companion object {
        fun verticesFor(sides: Int, radius: Float, startDirection: Vector3 = Vector3(1f, 0f, 0f)): List<Vertex3d>{
            val angle = -(2.0 * PI / sides).toFloat()
            val start = startDirection * radius
            return (1 until sides).fold(listOf(Vertex3d(Vector3(0f, 0f, 0f)), Vertex3d(start))){
                acc, i ->  acc + Vertex3d(Vector3(
                    x = start.x * cos(angle * i) - start.y * sin(angle * i),
                    y = start.x * sin(angle * i) + start.y * cos(angle * i),
                    z = 0f
            )
            )
            } + Vertex3d(start)
        }
    }
}