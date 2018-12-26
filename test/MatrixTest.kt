import sgl.*
import kotlin.test.Test
import kotlin.test.expect

class MatrixTest {

    @Test fun identity3() = expect(Matrix3(
            1f, 0f, 0f,
            0f, 1f, 0f,
            0f, 0f, 1f
    )){ Matrix3.identity }

    @Test fun translation3() =  expect(Matrix3(
            1f, 0f, 16f,
            0f, 1f, -2f,
            0f, 0f, 1f
    )){ Matrix3.identity translate Vector2(10f, -5f) translate Vector2(6f, 3f) }

    @Test fun scale3() = expect(Matrix3(
            20f, 0f, 0f,
            0f, -0.5f, 0f,
            0f, 0f, 1f
    )){ Matrix3.identity scale Vector2(10f, -5f) scale Vector2(2f, 0.1f) }

    @Test fun transform3() = expect(Vector2(1.5f, 14f)) {
        (Matrix3.identity scale Vector2(0.5f, 2f) translate Vector2(-2f, 4f)) * Vector2(5f, 3f)
    }

    @Test fun transpose3() =  expect(Matrix3(
            1f, 4f, 7f,
            2f, 5f, 8f,
            3f, 6f, 9f
    )){
        Matrix3(
                1f, 2f, 3f,
                4f, 5f, 6f,
                7f, 8f, 9f
        ).transpose()
    }

    @Test fun determinant3() = expect(1f){
        Matrix3(
                1f, 2f, 3f,
                0f, 1f, 4f,
                5f, 6f, 0f
        ).determinant
    }

    @Test fun adjugate3() = expect(
            Matrix3(
                    -24f, 18f, 5f,
                    20f, -15f, -4f,
                    -5f, 4f, 1f
            )
    ){
        Matrix3(
                1f, 2f, 3f,
                0f, 1f, 4f,
                5f, 6f, 0f
        ).adjugate()
    }

    @Test fun inverse3(){
        val inverse = Matrix3(
                -24f, 18f, 5f,
                20f, -15f, -4f,
                -5f, 4f, 1f
        )
        val matrix = Matrix3(
                1f, 2f, 3f,
                0f, 1f, 4f,
                5f, 6f, 0f
        )
        expect(inverse){ matrix.inverse() }
        expect(Matrix3.identity){ matrix * inverse }
    }

    @Test fun identity4() = expect(Matrix4(
            1f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f,
            identity = true
    )){ Matrix4.identity }

    @Test fun translation4() =  expect(Matrix4(
            1f, 0f, 0f, 16f,
            0f, 1f, 0f, -2f,
            0f, 0f, 1f, 15f,
            0f, 0f, 0f, 1f
    )){ Matrix4.identity translate Vector3(10f, -5f, 10f) translate Vector3(6f, 3f, 5f) }

    @Test fun scale4() = expect(Matrix4(
            20f, 0f, 0f, 0f,
            0f, -0.5f, 0f, 0f,
            0f, 0f, 10f, 0f,
            0f, 0f, 0f, 1f
    )){ Matrix4.identity scale Vector3(10f, -5f, -5f) scale Vector3(2f, 0.1f, -2f) }


    @Test fun transform4() = expect(Vector3(1.5f, 14f, 15f)) {
        (Matrix4.identity scale Vector3(0.5f, 2f, 3f) translate Vector3(-2f, 4f, -5f)) * Vector3(5f, 3f, 10f)
    }

    @Test fun transpose4() =  expect(Matrix4(
            1f, 5f, 9f, 13f,
            2f, 6f, 10f, 14f,
            3f, 7f, 11f, 15f,
            4f, 8f, 12f, 16f
    )){
        Matrix4(
                1f, 2f, 3f, 4f,
                5f, 6f, 7f, 8f,
                9f, 10f, 11f, 12f,
                13f, 14f, 15f, 16f
        ).transpose()
    }

    @Test fun determinant4() = expect(170f){
        Matrix4(
                6f, 0f, -3f, 5f,
                4f, 13f, 6f, -8f,
                -1f, 0f, 7f, 4f,
                8f, 6f, 0f, 2f
        ).determinant
    }

    @Test fun inverse4(){
        val inverse = Matrix4(
                -3f, -0.5f, 1.5f, 1f,
                1f, 0.25f, -0.25f, -0.5f,
                3f, 0.25f, -1.25f, -0.5f,
                -3f, -0f, 1f, 1f
        )
        val matrix = Matrix4(
                1f, 1f, 1f, 0f,
                0f, 3f, 1f, 2f,
                2f, 3f, 1f, 0f,
                1f, 0f, 2f, 1f
        )
        expect(inverse){ matrix.inverse() }
        expect(Matrix4.identity.copy(identity = false)){ matrix * inverse }
    }

}