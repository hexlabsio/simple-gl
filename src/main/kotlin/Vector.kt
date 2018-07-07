
import kotlin.math.sqrt

typealias Color = Vector4

data class Vector2(val x: Float, val y: Float): Vector(listOf(x, y)){
    override fun typeTransform(vector: Vector) = vector.to2D()
}
data class Vector3(val x: Float, val y: Float, val z: Float): Vector(listOf(x, y, z)){
    override fun typeTransform(vector: Vector) = vector.to3D()
}
data class Vector4(val r: Float, val g: Float, val b: Float, val a: Float = 0f): Vector(listOf(r, g, b, a)){
    override fun typeTransform(vector: Vector) = vector.to4D()
}

open class Vector(val components: List<Float>): ArrayList<Float>(components){
    private var _length: Float? = null
    private var _direction: Vector? = null

    @JsName("typeTransform") open fun typeTransform(vector: Vector) = this
    @JsName("translate") fun translate(v: Vector) = typeTransform(this + v)
    @JsName("difference") fun difference(v: Vector) = typeTransform(this - v)
    @JsName("scale") fun scale(v: Vector) = typeTransform(this * v)
    @JsName("scaleEqually") fun scaleEqually(scale: Float) = typeTransform(this * scale)
    @JsName("dot") infix fun dot(v: Vector) = this.times(v).components.sum()

    fun length(): Float{
        if(_length == null) _length = sqrt(this dot this)
        return _length!!
    }
    fun direction(): Vector {
        if (_direction == null) _direction = with(length()) {
            when {
                this == 0f -> Vector(components.map { 0f })
                else -> Vector(components.map { it / this })
            }
        }
        return _direction!!
    }
}

operator fun Vector.plus(v: Vector) = operateOn(v){ a, b -> a + b }
operator fun Vector.minus(v: Vector) = operateOn(v){ a, b -> a - b }
operator fun Vector.times(v: Vector) = operateOn(v){ a, b -> a * b }
operator fun Vector.times(scale: Float) = Vector(components.map { it * scale })
operator fun Float.times(v: Vector) = v * this


fun Vector.to2D() = Vector2(this[0], this[1])
fun Vector.to3D() = Vector3(this[0], this[1], this[2])
fun Vector.to4D() = Vector4(this[0], this[1], this[2], this[3])

operator fun Vector2.plus(v: Vector2) = (this as Vector).plus(v).to2D()
operator fun Vector2.minus(v: Vector2) = (this as Vector).minus(v).to2D()
operator fun Vector2.times(v: Vector2) = (this as Vector).times(v).to2D()
operator fun Vector2.times(scale: Float) = (this as Vector).times(scale).to2D()
fun Vector2.direction() = this.direction().to2D()

operator fun Vector3.plus(v: Vector3) = (this as Vector).plus(v).to3D()
operator fun Vector3.minus(v: Vector3) = (this as Vector).minus(v).to3D()
operator fun Vector3.times(v: Vector3) = (this as Vector).times(v).to3D()
operator fun Vector3.times(scale: Float) = (this as Vector).times(scale).to3D()
fun Vector3.direction() = this.direction().to3D()

operator fun Vector4.plus(v: Vector4) = (this as Vector).plus(v).to4D()
operator fun Vector4.minus(v: Vector4) = (this as Vector).minus(v).to4D()
operator fun Vector4.times(v: Vector4) = (this as Vector).times(v).to4D()
operator fun Vector4.times(scale: Float) = (this as Vector).times(scale).to4D()
fun Vector4.direction() = this.direction().to4D()


private fun Vector.operateOn(b: Vector, operation: (Float, Float) -> Float) = Vector((components to b.components).sequence().map{ it.safe(operation) })

private fun <A> Pair<A?,A?>.safe(operation: (A, A) -> A): A{
    return when{
        (first != null && second != null) -> operation(first!!, second!!)
        first != null -> first!!
        second != null -> second!!
        else -> throw IllegalArgumentException("At least one side must be not null for safe operation")
    }
}

private fun <A, B> Pair<Iterable<A>, Iterable<B>>.sequence(): List<Pair<A?, B?>> = this.iterator().asSequence().toList()
private fun <A, B> Pair<Iterable<A>, Iterable<B>>.iterator(): Iterator<Pair<A?, B?>> {
    val a = first.iterator()
    val b = second.iterator()
    return object : Iterator<Pair<A?, B?>> {
        override fun hasNext() = a.hasNext() || b.hasNext()
        override fun next(): Pair<A?, B?> = Pair(if(a.hasNext()) a.next() else null, if(b.hasNext()) b.next() else null)
    }
}