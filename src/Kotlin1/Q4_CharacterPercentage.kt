package Kotlin1

fun main(arg: Array<String>) {
    print("enter the string ")
    var a = readLine()!!
    var lowerCaseLetter = 0
    var upperCaseLetter = 0
    var digits = 0
    var specialCharacter = 0
    for (i in 0 until a.length) {
        if (a[i].isLowerCase()){
            lowerCaseLetter++
        }

        else if (a[i].isUpperCase())
        {
            upperCaseLetter++
        }

        else if (a[i].isDigit())
        {
            digits++
        }

        else
            specialCharacter++
    }
    var len = a.length.toFloat();

    var lowerCasePercentage = (lowerCaseLetter / len) * 100;
    var UpperCasePercentage = (upperCaseLetter / len) * 100;
    var digitsPercentage = (digits / len) * 100;
    var specialCharacterPercentage = (specialCharacter / len) * 100;
    println("Lower case percentage is :" + lowerCasePercentage);
    println("Upper case percentage is :" + UpperCasePercentage);
    println("Digit percentage is : " + digitsPercentage);
    println("Special char percentage is :" + specialCharacterPercentage);
}