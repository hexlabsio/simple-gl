package sgl

import org.khronos.webgl.WebGLRenderingContext
import org.khronos.webgl.WebGLUniformLocation

typealias UniformMapper<T> = (renderingContext: WebGLRenderingContext, position: WebGLUniformLocation, data: T) -> Unit

data class UniformInfo<in T>(
        val name: String,
        val position: WebGLUniformLocation,
        val mapper: UniformMapper<T>
)

data class Uniform<in T>(
        val name: String,
        val mapper: UniformMapper<T>
)
