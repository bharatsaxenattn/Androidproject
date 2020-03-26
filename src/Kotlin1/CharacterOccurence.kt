package Kotlin1

fun main()
{
    print("enter the string")
    var a= readLine()!!
    print("enter the character you want to replace")
    var charac= readLine()!!
    var count=a.length-a.replace(charac,"").length
    print("count == :"+count)
}