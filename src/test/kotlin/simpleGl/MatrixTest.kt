package simpleGl

import kotlin.test.Test
import kotlin.test.expect

import simpleGl.Vector2

class MatrixTest {
    @Test fun identity3(){
        expect(Matrix3(
                1f,0f,0f,
                0f, 1f, 0f,
                0f, 0f, 1f
        )){ Matrix3.identity() }
    }

    @Test fun translation3(){
        expect(Matrix3(
                1f,0f,16f,
                0f, 1f, -2f,
                0f, 0f, 1f
        )){ Matrix3.identity() translate Vector2(10f, -5f) translate Vector2(6f, 3f) }
    }
}