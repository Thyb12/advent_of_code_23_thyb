import java.io.File

fun main() {
    val filePath = "/Users/b513im/Documents/advent_of_code/d2/d2.txt"
    val gameRecords = mutableListOf<String>()

    File(filePath).useLines { lines ->
        gameRecords.addAll(lines)
    }
    println("nombre de lignes : ${gameRecords.size}")

    val targetCubes = mapOf("red" to 12, "green" to 13, "blue" to 14)
    val possibleGames = mutableListOf<Int>()

    for ((index, gameRecord) in gameRecords.withIndex()) {
        val gameSubset = gameRecord.split(":")
        var isValid = true

        
        val listGameSubsets = gameSubset[1].trim().split(";")
        for (roundSubsets in listGameSubsets) {
            val coupSubsets = roundSubsets.trim().split(",")
            for(subset in coupSubsets){
                val cubes = subset.trim().split(" ")
                val color = cubes[1].replace(" ","")
                val countStr = cubes[0]
                if(index == 0) {
                    println("color: $color  countStr: $countStr")
                }
                if(countStr != "Game") {
                    if (!countStr.isDigitsOnly()) {
                        isValid = false
                    }

                    val count = countStr.toInt()

                    if (targetCubes[color] == null || count > targetCubes[color]!!) {
                        isValid = false
                    }
                }
            }
        }

        if (isValid) {
            possibleGames.add(index + 1)
        }
    }

    val sumOfIDs = possibleGames.sum()
    println("Sum of IDs of possible games: $sumOfIDs")
}

fun String.isDigitsOnly(): Boolean {
    return all { it.isDigit() }
}
