package Kotlin4

class Q2_Employee(name:String,age:Int)
{
   var name:String=name
    var age:Int=age
}
fun main()
{
    var a=Q2_Employee("abc",20)

    var b=Q2_Employee("abcd",40)

    var c=Q2_Employee("aaaa",50)

    var list= mutableListOf<Q2_Employee>();
    list.add(a)
    list.add(b)
    list.add(c)

    for(i in 0 until list.size)
    {
        var x=list.get(i)
        if (x.age>30)
            println(x.name)
    }
}