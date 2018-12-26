package sgl

import kotlin.math.PI
import kotlin.math.tan

data class Matrix3(
        val a: Float, val b: Float, val c: Float,
        val d: Float, val e: Float, val f: Float,
        val g: Float, val h: Float, val i: Float
): ArrayList<Float>(listOf(a,d,g,b,e,h,c,f,i)){
    constructor(scale: Vector2, translation: Vector2): this(scale.x,0f,translation.x,0f,scale.y,translation.y,0f,0f,1f)

    val determinant: Float by lazy { a*(e*i-h*f)-b*(d*i-g*f)+c*(d*h-g*e) }

    companion object {
        val identity = Matrix3(1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f)
        @JsName("translation") fun translation(translation: Vector2) = Matrix3(1f, 0f, translation.x, 0f, 1f, translation.y, 0f, 0f, 1f)
        @JsName("scale") fun scale(scale: Vector2) = Matrix3(scale.x, 0f, 0f, 0f, scale.y, 0f, 0f, 0f, 1f)
    }
}
@JsName("Identity2d")
fun Matrix3Identity() = Matrix3.identity
@JsName("Translation2d")
fun Matrix3Translation(translation: Vector2) = Matrix3.translation(translation)
@JsName("Scale2d")
fun Matrix3Scale(scale: Vector2) = Matrix3.scale(scale)

fun Matrix3.transpose() = Matrix3(a, d, g, b, e, h, c, f, i)
fun Matrix3.adjugate() = transpose().let {
    Matrix3(
            (it.e * it.i - it.h * it.f), -(it.d * it.i - it.g * it.f), (it.d * it.h - it.g * it.e),
            -(it.b * it.i - it.h * it.c), (it.a * it.i - it.g * it.c), -(it.a * it.h - it.g * it.b),
            (it.b * it.f - it.e * it.c), -(it.a * it.f - it.d * it.c), (it.a * it.e - it.d * it.b)
    )
    }

fun Matrix3.inverse() = determinant.let { if(it != 0f) adjugate() / it else Matrix3.identity }
operator fun Matrix3.div(v: Float) = Matrix3(a / v, b / v, c / v, d / v, e / v, f / v, g / v, h / v, i / v)
operator fun Matrix3.times(v: Vector2) = Vector2(x = a * v.x + b * v.y + c, y = d * v.x + e * v.y + f)
operator fun Matrix3.times(m: Matrix3) = Matrix3(
        a * m.a + b * m.d + c * m.g, a * m.b + b * m.e + c * m.h, a * m.c + b * m.f + c * m.i,
        d * m.a + e * m.d + f * m.g, d * m.b + e * m.e + f * m.h, d * m.c + e * m.f + f * m.i,
        g * m.a + h * m.d + i * m.g, g * m.b + h * m.e + i * m.h, g * m.c + h * m.f + i * m.i
)
infix fun Matrix3.translate(translation: Vector2) = this * Matrix3.translation(translation)
infix fun Matrix3.scale(scale: Vector2) = this * Matrix3.scale(scale)

data class Matrix4(
        val a: Float, val b: Float, val c: Float, val d: Float,
        val e: Float, val f: Float, val g: Float, val h: Float,
        val i: Float, val j: Float, val k: Float, val l: Float,
        val m: Float, val n: Float, val o: Float, val p: Float, val identity: Boolean = false
): ArrayList<Float>(listOf(a, e, i, m, b, f, j, n, c, g, k, o, d, h, l, p)){

    val determinant: Float by lazy {
        (a * f * k * p) + (a * g * l * n) + (a * h * j * o) +
                (b * e * l * o) + (b * g * i * p) + (b * h * k * m) +
                (c * e * j * p) + (c * f * l * m) + (c * h * i * n) +
                (d * e * k * n) + (d * f * i * o) + (d * g * j * m) -
                (a * f * l * o) - (a * g * j * p) - (a * h * k * n) -
                (b * e * k * p) - (b * g * l * m) - (b * h * i * o) -
                (c * e * l * n) - (c * f * i * p) - (c * h * j * m) -
                (d * e * j * o) - (d * f * k * m) - (d * g * i * n)
    }
    fun transpose() = Matrix4(a, e, i, m, b, f, j, n, c, g, k, o, d, h, l, p)
    fun inverse() = Matrix4(
            ((f * k * p) + (g * l * n) + (h * j * o) - (f * l * o) - (g * j * p) - (h * k * n)) / determinant,
            ((b * l * o) + (c * j * p) + (d * k * n) - (b * k * p) - (c * l * n) - (d * j * o)) / determinant,
            ((b * g * p) + (c * h * n) + (d * f * o) - (b * h * o) - (c * f * p) - (d * g * n)) / determinant,
            ((b * h * k) + (c * f * l) + (d * g * j) - (b * g * l) - (c * h * j) - (d * f * k)) / determinant,

            ((e * l * o) + (g * i * p) + (h * k * m) - (e * k * p) - (g * l * m) - (h * i * o)) / determinant,
            ((a * k * p) + (c * l * m) + (d * i * o) - (a * l * o) - (c * i * p) - (d * k * m)) / determinant,
            ((a * h * o) + (c * e * p) + (d * g * m) - (a * g * p) - (c * h * m) - (d * e * o)) / determinant,
            ((a * g * l) + (c * h * i) + (d * e * k) - (a * h * k) - (c * e * l) - (d * g * i)) / determinant,

            ((e * j * p) + (f * l * m) + (h * i * n) - (e * l * n) - (f * i * p) - (h * j * m)) / determinant,
            ((a * l * n) + (b * i * p) + (d * j * m) - (a * j * p) - (b * l * m) - (d * i * n)) / determinant,
            ((a * f * p) + (b * h * m) + (d * e * n) - (a * h * n) - (b * e * p) - (d * f * m)) / determinant,
            ((a * h * j) + (b * e * l) + (d * f * i) - (a * f * l) - (b * h * i) - (d * e * j)) / determinant,

            ((e * k * n) + (f * i * o) + (g * j * m) - (e * j * l) - (f * k * m) - (g * i * n)) / determinant,
            ((a * j * o) + (b * k * m) + (c * i * n) - (a * k * n) - (b * i * o) - (c * j * m)) / determinant,
            ((a * g * n) + (b * e * o) + (c * f * m) - (a * f * o) - (b * g * m) - (c * e * n)) / determinant,
            ((a * f * k) + (b * g * i) + (c * e * j) - (a * g * j) - (b * e * k) - (c * f * i)) / determinant
    )
    companion object {
        val identity = Matrix4(1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, true)
        fun translation(translation: Vector3) = Matrix4(1f, 0f, 0f, translation.x, 0f, 1f, 0f, translation.y, 0f, 0f, 1f, translation.z, 0f, 0f, 0f, 1f)
        fun scale(scale: Vector3) = Matrix4(scale.x, 0f, 0f, 0f, 0f, scale.y, 0f, 0f, 0f, 0f, scale.z, 0f, 0f, 0f, 0f, 1f)
    }
}
operator fun Matrix4.times(v: Vector3) = if(identity) v else Vector3(
        a * v.x + b * v.y + c * v.z + d,
        e * v.x + f * v.y + g * v.z + h,
        i * v.x + j * v.y + k * v.z + l
)
operator fun Matrix4.times(v: Vector4) = if(identity) v else  Vector4(
        a * v.r + b * v.g + c * v.b + d * v.a,
        e * v.r + f * v.g + g * v.b + h * v.a,
        i * v.r + j * v.g + k * v.b + l * v.a,
        m * v.r + n * v.g + o * v.b + p * v.a
)
operator fun Matrix4.times(x: Matrix4) = when {
    this.identity -> x
    x.identity -> this
    else -> Matrix4(
            a * x.a + b * x.e + c * x.i + d * x.m, a * x.b + b * x.f + c * x.j + d * x.n, a * x.c + b * x.g + c * x.k + d * x.o, a * x.d + b * x.h + c * x.l + d * x.p,
            e * x.a + f * x.e + g * x.i + h * x.m, e * x.b + f * x.f + g * x.j + h * x.n, e * x.c + f * x.g + g * x.k + h * x.o, e * x.d + f * x.h + g * x.l + h * x.p,
            i * x.a + j * x.e + k * x.i + l * x.m, i * x.b + j * x.f + k * x.j + l * x.n, i * x.c + j * x.g + k * x.k + l * x.o, i * x.d + j * x.h + k * x.l + l * x.p,
            m * x.a + n * x.e + o * x.i + p * x.m, m * x.b + n * x.f + o * x.j + p * x.n, m * x.c + n * x.g + o * x.k + p * x.o, m * x.d + n * x.h + o * x.l + p * x.p
    )
}

infix fun Matrix4.translate(translation: Vector3) = this * Matrix4.translation(translation)
infix fun Matrix4.scale(scale: Vector3) = this * Matrix4.scale(scale)

fun aspectProjection(fieldOfViewInRadians: Float, width: Float, height: Float): Matrix4 {
    val f = tan(PI*0.5f - 0.5f * fieldOfViewInRadians).toFloat()
    val aspect = width/height
    return Matrix4(
            -f / aspect, 0f, 0f, 0f,
            0f, f, 0f, 0f,
            0f, 0f, 0.01f, 0.01f,
            0f, 0f, 0.8f, 1f
    )
}