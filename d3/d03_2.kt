import java.io.File

// 25 multiplications 5617984
fun main() {
    val filePath = "/Users/b513im/Documents/advent_of_code/d3/d3_2.txt"
    var map = mutableListOf<String>()
    
    File(filePath).useLines { lines ->
        map.addAll(lines)
    }
    var tmp = 0
    var tmpGlobal = 0
    //remplacer les * par de . si il n'y a pas exactement 2 chiffres autour
    for((xIndex, xLigne) in map.withIndex()) {
        for((yIndex, yLine) in xLigne.withIndex()) {
            if(yLine == '*') {
                tmpGlobal++
                val point = Pair(xIndex, yIndex)
                if(!validMultiple(point, map)) {
                    map[xIndex] = map[xIndex].replaceRange(yIndex,yIndex+1, ".")
                } else {
                    tmp++
                }
            }
            //si j'ai un autre caractere special que * et . alors je le remplace par un .
            if(yLine != '.' && yLine !in '0'..'9' && yLine != '*') {
                map[xIndex] = map[xIndex].replace(yLine, '.')
            }
        }
    }
    println("nombre de * enlever: $tmp sur $tmpGlobal")
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

    // parcourir la map et faire la somme de chaque multiplication
    var finalRes = 0
    var tmpres = 0
    for((xIndex, xLigne) in map.withIndex()) {
        for((yIndex, yLine) in xLigne.withIndex()) {
            if(yLine == '*') {
                tmpres++
                finalRes += multiplierLesChiffres(Pair(xIndex, yIndex), map)
            }
        }
    }
    println("Resultat final: $finalRes ($tmpres multiplications)") 
}

fun validMultiple(point: Pair<Int, Int>, map: MutableList<String>):Boolean {
    val x = point.first
    val y = point.second
    var count = 0
    if(y> 0) {
        //regarde à gauche si il y a un chiffre
        if(map[x][y - 1] in '0'..'9') {
            count++            
        }
    }
    if(y < map[x].length) {
        //regarde à droite si il y a un chiffre
        if(map[x][y + 1] in '0'..'9') {
            count++            
        }
    }
    if(x-1 >= 0) {
        //regarde en haut si il y a un chiffre
        if(map[x - 1][y] in '0'..'9') {
            count++            
        }else {
            //regarde en haut à gauche
            if(y-1 >= 0) {
                if(map[x - 1][y - 1] in '0'..'9') {
                    count++            
                }
            }
            //regarde en haut à droite
            if(y+1 < map[x].length) {
                if(map[x - 1][y + 1] in '0'..'9') {
                    count++            
                }
            }
        }
    } 
    if(x+1 < map.size) {
        //regarde en bas si il y a un chiffre
        if(map[x + 1][y] in '0'..'9') {
            count++            
        }else {
            //regarde en bas à gauche
            if(y-1 >= 0) {
                if(map[x + 1][y - 1] in '0'..'9') {
                    count++            
                }
            }
            //regarde en bas à droite
            if(y+1 < map[x].length) {
                if(map[x + 1][y + 1] in '0'..'9') {
                    count++            
                }
            }
        }
    } 
    if(count == 2) {
        return true
    } else {
        println("point: $point count: $count")
        return false
    }
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
            if(map[x][y - 1] == '*') {
                return true            
            }
        }
        
        if(y+1 < map[x].length) {
            //regarde à droite si il y a un caractère spécial
            if(map[x][y + 1] == '*' ) {
                return true            
            }
        }
        
        if(x-1 >= 0) {
            //regarde en haut si il y a un caractère spécial
            if(map[x - 1][y] == '*') {
                return true            
            }

            if(y-1 >= 0) {
                //regarde en haut à gauche si il y a un caractère spécial
                if(map[x - 1][y - 1] == '*') {
                    return true            
                }
            }

            if(y+1 < map[x].length) {
                //regarde en haut à droite si il y a un caractère spécial
                if(map[x - 1][y + 1] == '*') {
                    return true            
                }
            }
        }

        if(x+1 < map.size) {
            //regarde en bas si il y a un caractère spécial
            if(map[x + 1][y] == '*' ) {
                return true            
            }

            if(y-1 >= 0) {
                //regarde en bas à gauche si il y a un caractère spécial
                if(map[x + 1][y - 1] == '*') {
                    return true            
                }
            }

            if(y+1 < map[x].length) {
                //regarde en bas à droite si il y a un caractère spécial
                if(map[x + 1][y + 1] == '*') {
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




// partie calcul


//multiplie les 2 chiffres autour du point
fun multiplierLesChiffres(point: Pair<Int, Int>, map: MutableList<String>): Int {
    val x = point.first
    val y = point.second
    var premier = 0
    var deuxieme = 0

    if(y> 0) {
        //regarde à gauche si il y a un chiffre
        if(map[x][y - 1] in '0'..'9') {
            if(premier == 0) {
                premier = trouverLeNombre(Pair(x, y - 1), map).toInt()            
            } else {
                deuxieme = trouverLeNombre(Pair(x, y - 1), map).toInt()
            }
        }
    }
    if(y < map[x].length) {
        //regarde à droite si il y a un chiffre
        if(map[x][y + 1] in '0'..'9') {
            if(premier == 0) {
                premier = trouverLeNombre(Pair(x, y + 1), map).toInt()            
            } else {
                deuxieme = trouverLeNombre(Pair(x, y + 1), map).toInt()
            }
        }
    }
    if(x-1 >= 0) {
        //regarde en haut si il y a un chiffre
        if(map[x - 1][y] in '0'..'9') {
            if(premier == 0) {
                premier = trouverLeNombre(Pair(x - 1, y), map).toInt()            
            } else {
                deuxieme = trouverLeNombre(Pair(x - 1, y), map).toInt()
            }
        } else {
            //regarde en haut à gauche
            if(map[x - 1][y - 1] in '0'..'9') {
                if(premier == 0) {
                    premier = trouverLeNombre(Pair(x - 1, y - 1), map).toInt()            
                } else {
                    deuxieme = trouverLeNombre(Pair(x - 1, y - 1), map).toInt()
                }
            }
            //regarde en haut à droite
            if(map[x - 1][y + 1] in '0'..'9') {
                if(premier == 0) {
                    premier = trouverLeNombre(Pair(x - 1, y + 1), map).toInt()            
                } else {
                    deuxieme = trouverLeNombre(Pair(x - 1, y + 1), map).toInt()
                }
            }
        }
    }
    if(x+1 < map.size) { 
        //regarde en bas si il y a un chiffre
        if(map[x + 1][y] in '0'..'9') {
            if(premier == 0) {
                premier = trouverLeNombre(Pair(x + 1, y), map).toInt()            
            } else {
                deuxieme = trouverLeNombre(Pair(x + 1, y), map).toInt()
            }
        } else {
            //regarde en bas à gauche
            if(map[x + 1][y - 1] in '0'..'9') {
                if(premier == 0) {
                    premier = trouverLeNombre(Pair(x + 1, y - 1), map).toInt()            
                } else {
                    deuxieme = trouverLeNombre(Pair(x + 1, y - 1), map).toInt()
                }
            }
            //regarde en bas à droite
            if(map[x + 1][y + 1] in '0'..'9') {
                if(premier == 0) {
                    premier = trouverLeNombre(Pair(x + 1, y + 1), map).toInt()            
                } else {
                    deuxieme = trouverLeNombre(Pair(x + 1, y + 1), map).toInt()
                }
            }
        }
    }
    return premier * deuxieme
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