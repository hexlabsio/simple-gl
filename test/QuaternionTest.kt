import sgl.Quaternion
import sgl.Vector3
import sgl.times
import kotlin.js.Math
import kotlin.test.Test

class QuaternionTest{
    @Test fun rotationAroundXAxis(){
        val xrotation = Quaternion.from((Math.PI/4.0).toFloat(), Vector3(0f, 0f, 1f))
        val yrotation = Quaternion.from((Math.PI/4.0).toFloat(), Vector3(1f, 0f, 0f))
        println(xrotation)
        println(yrotation)
        println(yrotation * yrotation)
        println((xrotation * yrotation).transformation())
        println(((xrotation * yrotation).transformation() * Vector3(0f, 1f, 0f)))
        println(((xrotation * yrotation).transformation() * Vector3(0f, 1f, 0f)).length())
    }
}