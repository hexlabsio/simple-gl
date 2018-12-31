package sgl
open class Scene(val objects: MutableList<Renderable> = mutableListOf(), val cameras: MutableList<Camera> = mutableListOf(PerspectiveCamera())){
    var currentCamera = cameras[0]
    @JsName("render")
    fun render(program: Program){
        program.stage()
        currentCamera.update(program)
        objects.forEach { it.render(program) }
    }
}