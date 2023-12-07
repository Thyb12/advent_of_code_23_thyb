import java.io.File

fun main() {
    val filePath = "/Users/b513im/Documents/advent_of_code/d1/d1.txt"
    val lines = File(filePath).readLines()

    var sum = 0
    for (line in lines) {
        var numberfinal = ""
        var speelednumber = ""
        for (char in line) {
            if (char.isDigit()) {
                numberfinal += char
                speelednumber = ""
            } else {
                speelednumber += char
                if (speelednumber.contains("one")) {
                    numberfinal += "1"
                    speelednumber = "e"
                }
                if (speelednumber.contains("two")) {
                    numberfinal += "2"
                    speelednumber = "o"
                }
                if (speelednumber.contains("three")) {
                    numberfinal += "3"
                    speelednumber = "e"
                }
                if (speelednumber.contains("four")) {
                    numberfinal += "4"
                    speelednumber = ""
                }
                if (speelednumber.contains("five")) {
                    numberfinal += "5"
                    speelednumber = "e"
                }
                if (speelednumber.contains("six")) {
                    numberfinal += "6"
                    speelednumber = ""
                }
                if (speelednumber.contains("seven")) {
                    numberfinal += "7"
                    speelednumber = "n"
                }
                if (speelednumber.contains("eight")) {
                    numberfinal += "8"
                    speelednumber = "t"
                }
                if (speelednumber.contains("nine")) {
                    numberfinal += "9"
                    speelednumber = "e"
                }
            }
        }
        val finalNumber = if (numberfinal.length == 1) numberfinal + numberfinal else "${numberfinal.first()}${numberfinal.last()}"
        print(" $line => $finalNumber |")
        sum += finalNumber.toIntOrNull() ?: 0
    }
    println("")
    println("Sum of all calibration values: $sum")
}
