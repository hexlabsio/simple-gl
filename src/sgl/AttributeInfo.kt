package sgl
import org.khronos.webgl.WebGLBuffer
import org.khronos.webgl.WebGLRenderingContext

typealias AttributeMapper = (renderingContext: WebGLRenderingContext, position: Int) -> Unit

enum class AttributeType(val value: Int){ ARRAY_BUFFER(34962)}

data class Attribute(val name: String, val type: AttributeType, val mapper: AttributeMapper)

data class AttributeInfo(
        val name: String,
        val position: Int,
        val type: AttributeType,
        val buffer: WebGLBuffer,
        val mapper: AttributeMapper
)