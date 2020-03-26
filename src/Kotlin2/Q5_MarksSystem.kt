package Kotlin2

fun main(){
    println("enter your marks")
    var number= readLine()!!.toInt()

    var result=when(number)
    {
        in 50..60->"Good"
        in 60..70->"Very Good"
        in 70..80->"excellent"
        in 80..100->"Rockstar"
        else->"Not known"
    }
    print(result)

}