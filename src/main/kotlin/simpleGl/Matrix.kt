package simpleGl

data class Matrix3(
        val a: Float, val b: Float, val c: Float,
        val d: Float, val e: Float, val f: Float,
        val g: Float, val h: Float, val i: Float
): ArrayList<Float>(listOf(a, b, c, d, e, f, g, h ,i)){
    val determinant: Float by lazy { a*(e*i-h*f)-b*(d*i-g*f)+c*(d*h-g*e) }

    companion object {
        fun identity() = Matrix3(1f,0f,0f,0f,1f,0f,0f,0f,1f)
        fun translation(translation: Vector2) = Matrix3( 1f, 0f, translation.x, 0f, 1f, translation.y, 0f, 0f, 1f)
        fun scale(scale: Vector2) = Matrix3(scale.x,0f,0f,0f,scale.y,0f,0f,0f,1f)
    }
}
fun Matrix3.transpose() = Matrix3(a,d,g,b,e,h,c,f,i)
fun Matrix3.adjugate() = transpose().let {
        Matrix3(
            (e*i-h*f), -(d*i-g*f), (d*h-g*e),
            -(b*i-h*c), (a*i-g*c), -(a*h-g*b),
            (b*f-e*c), -(a*f-d*c), (a*e-d*b)
        )
    }

fun Matrix3.inverse() = determinant.let { if(it != 0f) adjugate() / it else Matrix3.identity() }
operator fun Matrix3.div(f: Float) = Matrix3(a/f,b/f,c/f, d/f, e/f, f/f, g/f, h/f, i/f)
operator fun Matrix3.times(v: Vector2) = Vector2( x = a * v.x + b * v.y + c, y = d * v.x + e * v.y + f )
operator fun Matrix3.times(m: Matrix3) = Matrix3(
        a*m.a+b*m.d+c*m.g, a*m.b+b*m.e+c*m.h, a*m.c+b*m.f+c*m.i,
        d*m.a+e*m.d+f*m.g, d*m.b+e*m.e+f*m.h, d*m.c+e*m.f+f*m.i,
        g*m.a+h*m.d+i*m.g, g*m.b+h*m.e+i*m.h, g*m.c+h*m.f+i*m.i
)
infix fun Matrix3.translate(translation: Vector2) = this * Matrix3.translation(translation)
infix fun Matrix3.scale(scale: Vector2) = this * Matrix3.scale(scale)
//fun Matrix3.asMatrix4
