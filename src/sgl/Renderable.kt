package sgl
interface Renderable{
    @JsName("render")
    fun render(program: Program)
}