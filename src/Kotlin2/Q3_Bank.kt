package Kotlin2

open class Q3_Bank
{
    open fun getDetails():String{
        return "minimum ROI is 10 for all bank"
    }
}
class ICICI: Q3_Bank()
{
    override fun getDetails(): String {
        return "minimum ROI is 14 for icici bank"
    }
}
class SBI: Q3_Bank()
{
    override fun getDetails(): String {
        return "minimum ROI is 12 for SBI bank"
    }
}
class PNB: Q3_Bank()
{
    override fun getDetails(): String {
        return "minimum ROI is 13 for PNB bank"
    }
}
fun main()
{
    println(Q3_Bank().getDetails())
    println(SBI().getDetails())
    println(ICICI().getDetails())
    println(PNB().getDetails())
}