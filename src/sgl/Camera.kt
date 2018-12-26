package sgl

import kotlin.math.PI

open class Camera(val transformation: InverseTransform = InverseTransform()): Transformable{
    override fun transform(vector: Vector3) = transformation.transform(vector)
    override fun translate(translation: Vector3) = transformation.translate(translation)
    override fun rotate(angle: Float, axis: Vector3) = transformation.rotate(angle, axis)
    override fun rotate(about: Vector3, angle: Float, axis: Vector3) = transformation.rotate(about, angle, axis)
    override fun scale(scale: Vector3) = transformation.scale(scale)


    fun world() = transformation.inverseMatrix
    open fun update(program: Program){
        program.updateWorld(world())
    }
}

open class PerspectiveCamera(var fieldOfView: Float = (PI/2.0).toFloat(), var width: Float = 1500f, var height: Float = 800f): Camera(){
    override fun update(program: Program){
        program.updateProjection(aspectProjection(fieldOfView, width, height))
        program.updateWorld(world())
    }
}