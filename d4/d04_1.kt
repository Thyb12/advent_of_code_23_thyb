import java.io.File
import kotlin.math.pow

fun main() {
    val filePath = "/Users/b513im/Documents/advent_of_code/d4/d4.txt"
    val maps = mutableListOf<String>()

    File(filePath).useLines { lines ->
        maps.addAll(lines)
    }
    var points = 0
    for(map in maps) {
        val mapSplit = map.split(":")
        val mapGame = mapSplit[1].trim().split("|")
        val joueur = mapGame[0].trim().split(" ")
        val banque = mapGame[1].trim().split(" ")
        
        val joueurList = mutableListOf<Int>()
        val banqueList = mutableListOf<Int>()
        for(nombre in joueur) {
            if(nombre != "") {
                joueurList.add(nombre.toInt())
            }
        }
        for(nombre in banque) {
            if(nombre != "") {
                banqueList.add(nombre.toInt())
            }
        }
        val resultat = joueurList.count{ it in banqueList}
        points += if (resultat > 0) {
            2.0.pow(resultat - 1).toInt()
        } else {
            0
        }
    }
    println("points: $points")
}