package sgl

import kotlin.math.PI

open class Camera: InverseTransform(){
    val world: Matrix4
        get() = inverseMatrix

    val forward: Vector3
        get() = (rotation.transformation() * Vector3.Z).direction()

    open fun screenPositionFor(position: Vector3): Vector2 = Vector2.zero
    open fun screenPositionAndDepthFor(position: Vector3): Vector3 = Vector3.zero
    open fun transformToScreenSpace(vector: Vector2) = Vector2.zero
    open fun transformToViewSpace(vector: Vector2) = Vector2.zero
    open fun transformToWorldSpace(vector: Vector2) = Vector3.zero
    open fun transformToWorldSpaceLine(vector: Vector2): Pair<Vector3, Vector3> = Vector3.zero to Vector3.Z

    open fun update(program: Program){
        program.updateWorld(world)
    }
}

open class PerspectiveCamera: Camera(){
    private var dirtyCam: Boolean = true
    var fieldOfView: Float = (PI/2.0).toFloat()
        set(value){ dirtyCam = true; field = value}
    var width: Float = 1500f
        set(value){ dirtyCam = true; field = value}
    var height: Float = 800f
        set(value){ dirtyCam = true; field = value}

    var projection: Matrix4 = aspectProjection(fieldOfView, width, height)
        get(){
            if(dirtyCam) {
                projection = aspectProjection(fieldOfView, width, height)
                dirtyCam = false
            }
            return field
        }

    override fun screenPositionFor(position: Vector3): Vector2 = with(projection * with(transform(position)){ Vector4(x,y,z, 1f) }) {
        Vector2(r,g)
    }
    override fun screenPositionAndDepthFor(position: Vector3): Vector3 = with(projection * with(transform(position)){ Vector4(x,y,z, 1f) }) {
        Vector3(r,g,a)
    }
    override fun transformToScreenSpace(vector: Vector2) = vector * Vector2(width/2f, -height/2f) + Vector2(width/2f, height/2f)
    override fun transformToViewSpace(vector: Vector2) = (vector - Vector2(width/2f, height/2f)) * Vector2(2f/width, -2f/height)
    override fun transformToWorldSpace(vector: Vector2): Vector3 {
        val viewSpace = with(transformToViewSpace(vector)){Vector4(x, y, 0.01f, 1f)}
        val projected = projection.inverse() * viewSpace
        val transformed = matrix * projected
        return Vector3(transformed.r, transformed.g, transformed.b)
    }
    override fun transformToWorldSpaceLine(vector: Vector2): Pair<Vector3, Vector3> {
        val viewSpace = with(transformToViewSpace(vector)){Vector4(x, y, 0.02f, 1f)}
        val projected = projection.inverse() * viewSpace
        val b =with(matrix * projected){Vector3(r, g, b)}
        val a = transformToWorldSpace(vector)
        return a to (b-a).direction()
    }

    override fun update(program: Program){
        program.updateProjection(projection)
        program.updateWorld(world)
    }
}