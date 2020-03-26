package Kotlin2

fun add(a:Int,b:Int):Int
{
    return a+b
}
fun add(a:Double,b:Double):Double
{
    return a+b
}
fun multiply(a:Int,b:Int):Int
{
    print(a*b)
    return a*b
}
fun concate(a:String,b:String):String
{
    return a+b
}
fun concate(a:String,b:String,c:String):String
{
    return a+b+c
}

fun main()
{
    println(add(1,2))
    println(add(1.2,2.2))
    println(multiply(1,2))
    println(concate("heloo"," world"))
    println(concate("heloo"," world"," world"))

}