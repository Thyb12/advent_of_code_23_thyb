import java.io.File


fun main() {
    val filePath = "/Users/b513im/Documents/advent_of_code/d3/d3.txt"
    val tabOfPoints = mutableListOf<Pair<Int, Int>>()
    var sumFinal = 0
    val map = mutableListOf<String>()

    File(filePath).useLines { lines ->
        map.addAll(lines)
    }
    for((xIndex, xLigne) in map.withIndex()) {
        for((yIndex, yLine) in xLigne.withIndex()) {
            // si j'ai un caractere spécial autre que . et chiffre de 0 à 9 alors je l'ajoute
            if(yLine != '.' && yLine !in '0'..'9') {
                tabOfPoints.add(Pair(xIndex, yIndex))
            }
        }
    }
    for(point in tabOfPoints) {
        sumFinal += findNumberClosestToPoint(point, map)
    }

    println("sumFinal: $sumFinal")
}


fun findNumberClosestToPoint(point: Pair<Int, Int>, map: MutableList<String>): Int {
    var sumAdjacentPoints = 0
    val diagonalHautPoints = listOf(
        Pair(point.first + 1, point.second - 1), // up right   
        Pair(point.first - 1, point.second - 1)  // up left
    )
    val diagonalBasPoints = listOf(
        Pair(point.first + 1, point.second + 1), // down right
        Pair(point.first - 1, point.second + 1)  // down left
    )
    val adjacentLateralePoints = listOf(
        Pair(point.first, point.second + 1), // right
        Pair(point.first, point.second - 1), // left
    )
    // Laterale
    for (adjacentPoint in adjacentLateralePoints) {
        val x = adjacentPoint.first
        val y = adjacentPoint.second

        if (x >= 0 && x < map.size && y >= 0 && y < map[x].length) {
            val adjacentChar = map[x][y]
            if (adjacentChar in '0'..'9') {
                sumAdjacentPoints += trouverLeNombre(adjacentPoint, map).toInt()
            }
        }
    }
    
    //Haut et diagonal Haut
    val hautX = point.first - 1
    val hautY = point.second
    if (hautX >= 0 && hautX < map.size && hautY >= 0 && hautY < map[hautX].length) {
        val adjacentHautChar = map[hautX][hautY]
        if (adjacentHautChar in '0'..'9') {
            sumAdjacentPoints += trouverLeNombre(Pair(hautX, hautY), map).toInt()
        } else {
            for(diagonalHautPoint in diagonalHautPoints) {
                val x = diagonalHautPoint.first
                val y = diagonalHautPoint.second

                if (x >= 0 && x < map.size && y >= 0 && y < map[x].length) {
                    val adjacentHautDiagChar = map[x][y]
                    if (adjacentHautDiagChar in '0'..'9') {
                        sumAdjacentPoints += trouverLeNombre(diagonalHautPoint, map).toInt()
                    }
                }
            }
        }
    }
    // Bas et diagonal Bas
    val basX = point.first + 1
    val basY = point.second
    if(basX >= 0 && basX < map.size && basY >= 0 && basY < map[basX].length) {
        val adjacentBasChar = map[basX][basY]
        if (adjacentBasChar in '0'..'9') {
            sumAdjacentPoints += trouverLeNombre(Pair(basX, basY), map).toInt()
        } else {
            for(diagonalBasPoint in diagonalBasPoints) {
                val x = diagonalBasPoint.first
                val y = diagonalBasPoint.second

                if (x >= 0 && x < map.size && y >= 0 && y < map[x].length) {
                    val adjacentBasDiagChar = map[x][y]
                    if (adjacentBasDiagChar in '0'..'9') {
                        sumAdjacentPoints += trouverLeNombre(diagonalBasPoint, map).toInt()
                    }
                }
            }
        }
    }

    return sumAdjacentPoints
}



fun trouverLeNombre(point: Pair<Int, Int>, map: MutableList<String>): String {
    val res = goGauche(point, map)+map[point.first][point.second]+goDroite(point, map)
    return res
}

fun goGauche(point: Pair<Int, Int>, map : MutableList<String>): String {
    val x = point.first
    val y = point.second-1
    if(y>=0 && map[x][y] in '0'..'9') {
        return goGauche(Pair(x, y), map) + map[x][y]
    } else {
        return ""
    }
}

fun goDroite(point: Pair<Int, Int>, map : MutableList<String>): String {
    val x = point.first
    val y = point.second+1
    if(y<map[x].length && map[x][y] in '0'..'9') {
        return map[x][y] + goDroite(Pair(x, y), map)
    } else {
        return ""
    }
}
