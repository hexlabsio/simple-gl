package sgl

import org.khronos.webgl.WebGLRenderingContext

open class Line(val a: Vertex3d, val b: Vertex3d): Object(listOf(a, b)){
    override fun optimizedType() = WebGLRenderingContext.LINES
}

data class Lines(val verticies: List<Vertex3d>): Object(verticies){
    override fun optimizedType() = WebGLRenderingContext.LINE_STRIP
}