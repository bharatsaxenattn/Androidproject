package Kotlin2

class Q1_CompanionAndInitBlock {


    init {
        var Name="Bharat"
        var lastName="Saxena"
        var age=22
        println("Name is $Name $lastName and age is $age")

    }
    companion object{
       const val Name="Bharat"
        const val lastName="Saxena"
        const val age=22
        fun nameIs()= println("name is : "+Name)
        fun ageIs()= println("age is : "+age)
    }

}
fun main(arg:Array<String>)
{
   var q1Companionandinitblock= Q1_CompanionAndInitBlock()
    Q1_CompanionAndInitBlock.nameIs()
    Q1_CompanionAndInitBlock.ageIs()


}