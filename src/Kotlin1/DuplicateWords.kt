package Kotlin1

fun main(arg:Array<String>)
{
    print("enter the string")
    var a= readLine()!!
    var wordsArray=a.split(" ").toTypedArray();
    for (i in 0 until  wordsArray.size)
    {
        var count=1;
        for (j in i+1 until wordsArray.size)
        {

            if(wordsArray[i].equals(wordsArray[j]))
            {
                count++
                wordsArray[j]="@@"
            }
        }
        if (count > 1 && (wordsArray[i] != "@@")) {
            println(wordsArray[i] + ":" + count);
        }
    }
}