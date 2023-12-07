import java.io.File

fun main() {
    val filePath = "/Users/b513im/Documents/advent_of_code/d1/d1.txt"
    val lines = File(filePath).readLines()

    var sum = 0
    for (line in lines) {
        val numbers = line.replace(Regex("[^0-9]"), "")
        val finalNumber = if (numbers.length == 1) numbers + numbers else "${numbers.first()}${numbers.last()}"
        print(" $line => $finalNumber |")
        sum += finalNumber.toIntOrNull() ?: 0
    }
    println()
    println("Sum of all calibration values: $sum")
}
