package Kotlin4

fun main(){
    var si={p:Double,r:Double,t:Double->(p*r*t)/100}
    var x=si(1000.0,4.0,2.0)
    print(x)
}