package Kotlin2

fun main(args: Array<String>){
    var hashSet = HashSet<Int>()
    hashSet.add(1)
    hashSet.add(2)
    hashSet.add(3)
    hashSet.add(4)
    hashSet.add(5)
    hashSet.add(6)
    hashSet.add(6)

    for (i in hashSet){
        println(i)
    }
}