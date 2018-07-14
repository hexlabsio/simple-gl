
import kotlin.math.sqrt

typealias Color = Vector4

interface Vector {
    fun components(): List<Float>
    fun length(): Float
    @JsName("dot") fun dot(other: Vector): Float
    fun calculateLength() = sqrt(this.dot(this))
}

class InvalidVectorOperation(dimensions: Int): IllegalArgumentException("This operation is invalid, may only operate on Vector$dimensions")

data class Vector2(val x: Float, val y: Float): Vector{
    private var _length: Float? = null
    @JsName("plus") operator fun plus(other: Vector2) = Vector2(x = x + other.x, y = y + other.y)
    @JsName("minus") operator fun minus(other: Vector2) = Vector2(x = x - other.x, y = y - other.y)
    @JsName("scale") operator fun times(scale: Vector2) = Vector2(x = x * scale.x, y = y * scale.y)
    @JsName("scaleUniformly") operator fun times(scale: Float) = Vector2(x = x * scale, y = y * scale)
    override fun dot(other: Vector) = when(other){ is Vector2 -> x * other.x + y * other.y; else -> throw InvalidVectorOperation(2) }
    override fun length(): Float {
        if(_length == null) _length = calculateLength()
        return _length!!
    }
    override fun components() = listOf(x, y)
}

data class Vector3(val x: Float, val y: Float, val z: Float): Vector {
    private var _length: Float? = null
    @JsName("plus") operator fun plus(other: Vector3) = Vector3(x = x + other.x, y = y + other.y, z = z + other.z)
    @JsName("minus") operator fun minus(other: Vector3) = Vector3(x = x - other.x, y = y - other.y, z = z - other.z)
    @JsName("scale") operator fun times(scale: Vector3) = Vector3(x = x * scale.x, y = y * scale.y, z = z * scale.z)
    @JsName("scaleUniformly") operator fun times(scale: Float) = Vector3(x = x * scale, y = y * scale, z = z * scale)
    override fun dot(other: Vector) = when(other){ is Vector3 -> x * other.x + y * other.y + z * other.z; else -> throw InvalidVectorOperation(3) }
    override fun length(): Float {
        if (_length == null) _length = calculateLength()
        return _length!!
    }
    override fun components() = listOf(x, y, z)
}

data class Vector4(val r: Float, val g: Float, val b: Float, val a: Float): Vector {
    private var _length: Float? = null
    @JsName("plus") operator fun plus(other: Vector4) = Vector4(r = r + other.r, g = g + other.g, b = b + other.b, a = a + other.a)
    @JsName("minus") operator fun minus(other: Vector4) = Vector4(r = r - other.r, g = g - other.g, b = b - other.b, a = a - other.a)
    @JsName("scale") operator fun times(scale: Vector4) = Vector4(r = r * scale.r, g = g * scale.g, b = b * scale.b, a = a * scale.a)
    @JsName("scaleUniformly") operator fun times(scale: Float) = Vector4(r = r * scale, g = g * scale, b = b * scale, a = a * scale)
    override fun dot(other: Vector) = when(other){ is Vector4 -> r * other.r + g * other.g + b * other.b + a * other.a; else -> throw InvalidVectorOperation(4) }
    override fun length(): Float {
        if (_length == null) _length = calculateLength()
        return _length!!
    }
    override fun components() = listOf(r, g, b, a)
}
