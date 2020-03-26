package Kotlin3
class Q4_ExtensionFunction {

}
fun Q4_ExtensionFunction.printMessage():String
{
    return "extension function"
}

 fun main()
 {

     println(Q4_ExtensionFunction().printMessage())
 }