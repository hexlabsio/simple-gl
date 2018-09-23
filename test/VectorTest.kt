import sgl.Vector2
import kotlin.test.Test
import kotlin.test.expect


class VectorTest{
    @Test fun plus2D(){
        expect(Vector2(10f, 25f)){ Vector2(0f, 0f) + Vector2(10f, 25f) }
        expect(Vector2(-90f, 7f)){ Vector2(-95f, -3f) + Vector2(5f, 10f) }
    }

    @Test fun subtract2D() = expect(Vector2(5f, -25f)){ Vector2(10f, 25f) - Vector2(5f, 50f) }
    @Test fun scale2D() = expect(Vector2(90f, 60f)){ Vector2(3f, 2f) * 30f }
    @Test fun scaleVector2D() = expect(Vector2(-108f, 32f)){ Vector2(-12f, 4f) * Vector2(9f, 8f) }
}