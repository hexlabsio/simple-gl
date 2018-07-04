
import kotlin.math.sqrt

typealias Color = Vector4

data class Vector2(override val x: Float, override val y: Float): Vector(listOf(x, y))
data class Vector3(override val x: Float, override val y: Float, override val z: Float): Vector(listOf(x, y, z))
data class Vector4(override val r: Float, override val g: Float, override val b: Float, override val a: Float = 0f): Vector(listOf(r, g, b, a))

open class Vector(val components: List<Float>): ArrayList<Float>(components){
    val length: Float by lazy { sqrt(this dot this) }
    val direction: Vector by lazy {
        if(length == 0f) Vector(components.map{ 0f })
        else Vector(components.map { it / length })
    }
    open val x: Float by lazy { components[0] }
    open val y: Float by lazy { components[1] }
    open val z: Float by lazy { components[2] }
    open val u: Float by lazy { components[0] }
    open val v: Float by lazy { components[1] }
    open val w: Float by lazy { components[2] }
    open val r: Float by lazy { components[0] }
    open val g: Float by lazy { components[1] }
    open val b: Float by lazy { components[2] }
    open val a: Float by lazy { components[3] }
}

operator fun Vector.plus(v: Vector) = operateOn(v){ a, b -> a + b }
operator fun Vector.minus(v: Vector) = operateOn(v){ a, b -> a - b }
operator fun Vector.times(v: Vector) = operateOn(v){ a, b -> a * b }
operator fun Vector.times(scale: Float) = Vector(components.map { it * scale })
operator fun Float.times(v: Vector) = v * this
infix fun Vector.dot(v: Vector) = this.times(v).components.sum()

fun Vector.to2D() = Vector2(x, y)
fun Vector.to3D() = Vector3(x, y, z)
fun Vector.to4D() = Vector4(r, g, b, a)

operator fun Vector2.plus(v: Vector2) = (this as Vector).plus(v).to2D()
operator fun Vector2.minus(v: Vector2) = (this as Vector).minus(v).to2D()
operator fun Vector2.times(v: Vector2) = (this as Vector).times(v).to2D()
operator fun Vector2.times(scale: Float) = (this as Vector).times(scale).to2D()
fun Vector2.direction() = this.direction.to2D()

operator fun Vector3.plus(v: Vector3) = (this as Vector).plus(v).to3D()
operator fun Vector3.minus(v: Vector3) = (this as Vector).minus(v).to3D()
operator fun Vector3.times(v: Vector3) = (this as Vector).times(v).to3D()
operator fun Vector3.times(scale: Float) = (this as Vector).times(scale).to3D()
fun Vector3.direction() = this.direction.to3D()

operator fun Vector4.plus(v: Vector4) = (this as Vector).plus(v).to4D()
operator fun Vector4.minus(v: Vector4) = (this as Vector).minus(v).to4D()
operator fun Vector4.times(v: Vector4) = (this as Vector).times(v).to4D()
operator fun Vector4.times(scale: Float) = (this as Vector).times(scale).to4D()
fun Vector4.direction() = this.direction.to4D()


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