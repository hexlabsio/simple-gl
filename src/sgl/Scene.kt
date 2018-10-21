package sgl
data class Scene(val objects: List<Renderable>){
    @JsName("render")
    fun render(program: Program){
        objects.forEach { it.render(program) }
    }
}