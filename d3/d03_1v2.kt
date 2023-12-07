import java.io.File


fun main() {
    val filePath = "/Users/b513im/Documents/advent_of_code/d3/d3.txt"
    var map = mutableListOf<String>()
    
    File(filePath).useLines { lines ->
        map.addAll(lines)
    }

    for((xIndex, xLigne) in map.withIndex()) {
        for((yIndex, yLine) in xLigne.withIndex()) {
            // si j'ai un chiffre de 0 à 9 alors je regarde si il est entouré de caractères spéciaux
            if(yLine in '0'..'9') {
                val point = Pair(xIndex, yIndex)
                if(!validNumbers(point, map, true)) {
                    map = supprimerChiffre(point, map)
                }
            }
        }
    }

    // remplacer tout les charactères spéciaux par des points
    for((xIndex, xLigne) in map.withIndex()) {
        for((yIndex, yLine) in xLigne.withIndex()) {
            if(yLine != '.' && yLine !in '0'..'9') {
                map[xIndex] = map[xIndex].replace(yLine, '.')
            }
        }
    }
    // réduire les . a la suite pour en avoir que 1 entre chaque chiffre
    for((xIndex, xLigne) in map.withIndex()) {
    
        while(true) {
            if(map[xIndex].contains("..")) {
                map[xIndex] = map[xIndex].replace("..", ".")
            } else { 
                break
            }
        }
    }

    var finalRes = 0
    // faire la somme de chaque chiffre
    for((xIndex, xLigne) in map.withIndex()) {
     val list = xLigne.split(".")
        for(chiffre in list) {
            if(chiffre == "") {
                continue
            }
            finalRes += chiffre.toInt()
        }
    }
    println("Resultat final: $finalRes")
}


fun validNumbers(point: Pair<Int, Int>, map: MutableList<String>, start :Boolean):Boolean {
    val x = point.first
    val y = point.second
    if(y> 0 && start) {
        //regarde à gauche si il y a un chiffre
        if(map[x][y - 1] in '0'..'9') {
            return true            
        }
    }
    if(y < map[x].length && map[x][y] in '0'..'9') {
        if(y-1 >= 0) {
            //regarde à gauche si il y a un caractère spécial
            if(map[x][y - 1] != '.' && map[x][y - 1] !in '0'..'9') {
                return true            
            }
        }
        
        if(y+1 < map[x].length) {
            //regarde à droite si il y a un caractère spécial
            if(map[x][y + 1] != '.' && map[x][y + 1] !in '0'..'9') {
                return true            
            }
        }
        
        if(x-1 >= 0) {
            //regarde en haut si il y a un caractère spécial
            if(map[x - 1][y] != '.' && map[x - 1][y] !in '0'..'9') {
                return true            
            }

            if(y-1 >= 0) {
                //regarde en haut à gauche si il y a un caractère spécial
                if(map[x - 1][y - 1] != '.' && map[x - 1][y - 1] !in '0'..'9') {
                    return true            
                }
            }

            if(y+1 < map[x].length) {
                //regarde en haut à droite si il y a un caractère spécial
                if(map[x - 1][y + 1] != '.' && map[x - 1][y + 1] !in '0'..'9') {
                    return true            
                }
            }
        }

        if(x+1 < map.size) {
            //regarde en bas si il y a un caractère spécial
            if(map[x + 1][y] != '.' && map[x + 1][y] !in '0'..'9') {
                return true            
            }

            if(y-1 >= 0) {
                //regarde en bas à gauche si il y a un caractère spécial
                if(map[x + 1][y - 1] != '.' && map[x + 1][y - 1] !in '0'..'9') {
                    return true            
                }
            }

            if(y+1 < map[x].length) {
                //regarde en bas à droite si il y a un caractère spécial
                if(map[x + 1][y + 1] != '.' && map[x + 1][y + 1] !in '0'..'9') {
                    return true            
                }
            }
        }
        
        return validNumbers(Pair(x, y + 1), map, false)
    } else {
        return false
    }
}

fun supprimerChiffre(point: Pair<Int, Int>, map: MutableList<String>): MutableList<String> {
    var tempoMap = map
    val x = point.first
    val y = point.second
    if(y >= map[x].length || map[x][y].toString() == ".") {
        return tempoMap
    } else {
        tempoMap[x] = tempoMap[x].replaceRange(y,y+1, ".")
        return supprimerChiffre(Pair(x, y + 1), tempoMap)
    }
}