package sgl

data class Quaternion(val scalar: Float, val axis: Vector3){
    @JsName("plus") operator fun plus(other: Quaternion) = Quaternion(scalar = scalar + other.scalar, axis = axis + other.axis)
    @JsName("minus") operator fun minus(other: Quaternion) = Quaternion(scalar = scalar - other.scalar, axis = axis - other.axis)
    @JsName("times") operator fun times(other: Quaternion) = Quaternion(
            scalar = scalar * other.scalar - axis.dot(other.axis),
            axis = Vector3(
                    x = scalar * other.axis.x + axis.x * other.scalar + axis.y * other.axis.z - axis.z * other.axis.y,
                    y = scalar * other.axis.y - axis.x * other.axis.z + axis.y * other.scalar + axis.z * other.axis.x,
                    z = scalar * other.axis.z + axis.x * other.axis.y - axis.y * other.axis.x + axis.z * other.scalar
            )
    )
    @JsName("scaleUniformly") operator fun times(scale: Float) = Quaternion(scalar = scalar * scale, axis = axis * scale)
    @JsName("div") operator fun div(scale: Float) = Quaternion(scalar = scalar / scale, axis = axis / scale)
    fun conjugate() = Quaternion(scalar = scalar, axis = axis * -1f)
    fun length() = kotlin.math.sqrt( (scalar * scalar + axis.x * axis.x + axis.y * axis.y + axis.z * axis.z).toDouble()).toFloat()
    fun normal() = length().let { Quaternion(scalar = scalar / it, axis = axis / it) }
    fun transformation(): Matrix4 {
        val x2 = axis.x * axis.x
        val y2 = axis.y * axis.y
        val z2 = axis.z * axis.z
        return Matrix4(
                a = 1 - 2 * y2 - 2 * z2, b = 2 * axis.x * axis.y - 2 * scalar * axis.z, c = 2 * axis.x * axis.z + 2 * scalar * axis.y, d = 0f,
                e = 2 * axis.x * axis.y + 2 * scalar * axis.z, f = 1 - 2 * x2 - 2 * z2, g = 2 * axis.y * axis.z - 2 * scalar * axis.x, h = 0f,
                i = 2 * axis.x * axis.z - 2 * scalar * axis.y, j = 2 * axis.y * axis.z + 2 * scalar * axis.x, k = 1 - 2 * x2 - 2 * y2, l = 0f,
                m = 0f, n = 0f, o = 0f, p = 1f
        )
    }
    companion object {
        @JsName("from") fun from(angle: Float, axis: Vector3) = Quaternion(
                scalar = kotlin.math.cos(angle / 2.0).toFloat(),
                axis = axis * kotlin.math.sin(angle / 2.0).toFloat()
        )
    }
}