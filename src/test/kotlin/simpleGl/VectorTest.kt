package simpleGl

import kotlin.test.Test
import kotlin.test.expect

import simpleGl.Vector2

class VectorTest{
    @Test fun translate2D() = expect(Vector2(10f, 25f)){ Vector2(0f, 0f) + Vector2(10f, 25f)}
}