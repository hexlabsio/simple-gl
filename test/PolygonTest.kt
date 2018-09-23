import sgl.Polygon
import sgl.Vector3
import kotlin.math.sqrt
import kotlin.test.Test
import kotlin.test.expect

class PolygonTest{
    @Test fun unitSquareIsSquare(){
        expect(listOf(
                Vector3(0f, 0f, 0f),
                Vector3(-0.5f, -0.5f, 0f),
                Vector3(-0.5f, 0.5f, 0f),
                Vector3(0.5f, 0.5f, 0f),
                Vector3(0.5f, -0.5f, 0f),
                Vector3(-0.5f, -0.5f, 0f)
        )){ Polygon(4, sqrt(0.5f), Vector3(-1f, -1f, 0f).direction()).localVertices.map { it.position } }
    }
}