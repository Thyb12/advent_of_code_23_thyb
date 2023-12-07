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
    val possibleGamesMAx = mutableListOf<Int>()

    for ((index, gameRecord) in gameRecords.withIndex()) {
        val gameSubset = gameRecord.split(":")
        var isValid = true
        var redMax = 0
        var greenMax = 0
        var blueMax = 0

        
        val listGameSubsets = gameSubset[1].trim().split(";")
        for (roundSubsets in listGameSubsets) {
            var redMaxRound = 0
            var greenMaxRound = 0
            var blueMaxRound = 0
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
                    if(color == "red") 
                        redMaxRound += count
                    if(color == "green")
                        greenMaxRound += count
                    if(color == "blue")
                        blueMaxRound += count

                    if (targetCubes[color] == null || count > targetCubes[color]!!) {
                        isValid = false
                    }
                }
            }
            if(redMaxRound > redMax) {
                redMax = redMaxRound
            }
            if(greenMaxRound > greenMax) {
                greenMax = greenMaxRound
            }
            if(blueMaxRound > blueMax) {
                blueMax = blueMaxRound
            }
        }

        if (isValid) {
            possibleGames.add(index + 1)    
        }
        possibleGamesMAx.add(redMax * greenMax * blueMax)
    }

    val sumOfIDs = possibleGames.sum()
    println("Sum of IDs of possible games: $sumOfIDs")
    println("Max of possible games: ${possibleGamesMAx.sum()}")
}

fun String.isDigitsOnly(): Boolean {
    return all { it.isDigit() }
}
