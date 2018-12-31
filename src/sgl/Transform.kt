package sgl

interface Transformable{
    @JsName("transform") fun transform(vector: Vector3): Vector3
    @JsName("translate") fun translate(translation: Vector3)
    @JsName("rotate") fun rotate(angle: Float, axis: Vector3)
    @JsName("rotateAbout") fun rotate(about: Vector3, angle: Float, axis: Vector3)
    @JsName("scale") fun scale(scale: Vector3)
}

open class Transform: Transformable{
    var matrix: Matrix4 = Matrix4.identity
        get() {
            if (dirty) {
                matrix = Matrix4.translation(translationVector) * Matrix4.scale(scaleVector) * rotation.transformation() * transformations
                dirty = false
            }
            return field
        }
    var dirty = true
    var translationVector = Vector3.zero
        set(value) { dirty = true; field = value }
    var scaleVector = vectorOf(1f,1f,1f)
        set(value) { dirty = true; field = value }
    var rotation = Quaternion.from(0f, vectorOf(1f,0f,0f))
        set(value) { dirty = true; field = value }
    var transformations = Matrix4.identity
        set(value) { dirty = true; field = value }

    override fun translate(translation: Vector3){
        translationVector += translation
    }
    override fun rotate(angle: Float, axis: Vector3) {
        rotation *= Quaternion.from(angle, axis)
    }
    override fun rotate(about: Vector3, angle: Float, axis: Vector3) {
        val localPosition = translationVector + about
        transformations *= Matrix4.translation(localPosition*-1f) * Quaternion.from(angle, axis).transformation() * Matrix4.translation(localPosition)
    }
    override fun scale(scale: Vector3){
        scaleVector *= scale
    }

    override fun transform(vector: Vector3) = matrix * vector
    operator fun invoke(vector: Vector3) = transform(vector)
}

open class InverseTransform: Transform(){
    var inverseMatrix: Matrix4 = matrix.inverse()
        get() = matrix.inverse()

    override fun transform(vector: Vector3) = inverseMatrix * vector
}