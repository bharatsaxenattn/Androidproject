package Kotlin1

fun main() {
    println("enter the string")
    var a = readLine()!!.toString()
    val charArray = a.toCharArray()
    for (i in 0 until a.length) {
        for (j in i + 1 until a.length) {

            if (charArray[i] == charArray[j]) {
                charArray[i] = '@'
                charArray[j] = '@'
            }
        }
    }
    for (i in 0 until charArray.size) {
        if (charArray[i] != '@') {
            print(charArray[i]+" ");
        }
    }

}