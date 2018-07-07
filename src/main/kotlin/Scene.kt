
data class Scene(val objects: List<Object>){
    @JsName("render")
    fun render(program: Program){
        program.reconfigureViewport()
        objects.forEach { it.render(program) }
    }
}