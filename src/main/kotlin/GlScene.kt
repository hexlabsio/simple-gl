
data class GlScene(val objects: List<GlObject>){
    fun render(program: GlProgram){
        program.reconfigureViewport()
        objects.forEach { it.render(program) }
    }
}