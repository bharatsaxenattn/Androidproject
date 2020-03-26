package Kotlin2

fun main(){
    val list = mutableListOf<Int>(10,20,30,40)
    list.add(30)
    list[3]=1
    println(list)
}