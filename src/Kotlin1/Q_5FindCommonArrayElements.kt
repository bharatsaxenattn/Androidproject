package Kotlin1
fun main(arg: Array<String>) {

    println("enter the length of first array")

    var length1: Int = readLine()!!.toInt()

    println("please enter element of first array")

    var arr1 = Array<Int>(length1) {
        readLine()!!.toInt()
    }
    println("enter the length of second array")
    var length2 = readLine()!!.toInt()
    println("please enter element of second array")

    var arr2 = Array<Int>(length2)
    {
        readLine()!!.toInt()
    }

    println("common elements")
    val commonArray = arr1.intersect(arr2.toList()).toIntArray()
    for (item in commonArray)
        println("common element "+item )


}