package jsland

import simpleGl.Vector3
import simpleGl.times

external fun require(module:String):dynamic

fun main(args: Array<String>) {
    val bob = Vector3(1f,2f,3f)
    val fred = bob * bob
    println("Hello $fred!")

}