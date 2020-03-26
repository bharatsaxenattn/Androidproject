package Kotlin3

sealed class Q3_SealedClass {

    class Child1() : Q3_SealedClass() {

    }

    class Child2() : Q3_SealedClass() {

    }

    class Child3() : Q3_SealedClass() {
    }
}
    fun printName(name: Q3_SealedClass): String {
        return name.javaClass.name;
    }

    fun main(arg:Array<String>)
{


    println(Q3_SealedClass.Child1())
    println(Q3_SealedClass.Child2())
    println(Q3_SealedClass.Child3())
}