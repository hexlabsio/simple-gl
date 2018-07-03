package simpleGl

import org.khronos.webgl.WebGLProgram
import org.khronos.webgl.WebGLRenderingContext

data class GlTriangle( val a: Vertex3d, val b: Vertex3d, val c: Vertex3d ): GlObject(listOf(a, b, c))

fun go(){
    GlTriangle(Vertex3d(), Vertex3d(), Vertex3d()).render(GlProgram("" as WebGLRenderingContext,"" as WebGLProgram, emptyMap(), emptyMap()))
}