package Kotlin1

fun main()
{
    print("Enter the Kotlin1.main String")
    var mainString= readLine()!!
    print("Enter the string you want to replace")
    var replace = readLine()!!
    print("enter the replaced string")
    var newString= readLine()!!
    val output= mainString.replace(replace,newString)
    println(output)

}

