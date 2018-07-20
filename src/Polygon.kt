import org.khronos.webgl.WebGLRenderingContext
import kotlin.math.*

data class Polygon(val sides: Int, val radius: Float, val startDirection: Vector3 = Vector3(1f,0f,0f))
    : Object(verticesFor(sides, radius, startDirection)){

    override fun optimizedType() = WebGLRenderingContext.TRIANGLE_FAN

    companion object {
        fun verticesFor(sides: Int, radius: Float, startDirection: Vector3 = Vector3(1f,0f,0f)): List<Vertex3d>{
            val angle = -(2.0 * PI / sides).toFloat()
            val start = startDirection * radius
            return (1 until sides).fold(listOf(Vertex3d(Vector3(0f,0f,0f)), Vertex3d(start))){
                acc, i ->  acc + Vertex3d(Vector3(
                        x = start.x * cos(angle*i) - start.y * sin(angle*i),
                        y = start.x * sin(angle*i) + start.y * cos(angle*i),
                        z = 0f
                    )
                )
            } + Vertex3d(start)
        }
    }
}