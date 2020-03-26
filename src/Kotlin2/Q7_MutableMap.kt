package Kotlin2

fun main(){
    var map: MutableMap<Int, String> = mutableMapOf<Int, String>()
    map.put(1,"a")
    map.put(2,"b")
    map.put(3,"c")
    for (key in map.keys)
        println(" key is $key and value is "+map.get(key))
}