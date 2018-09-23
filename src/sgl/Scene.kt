package sgl
data class Scene(val objects: List<Renderable>){
    @JsName("render")
    fun render(program: Program){
        program.reconfigureViewport()
        objects.forEach { it.render(program) }
    }
}