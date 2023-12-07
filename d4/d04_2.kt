import java.io.File
import kotlin.math.pow

fun main() {
    val filePath = "/Users/b513im/Documents/advent_of_code/d4/d4.txt"
    val maps = mutableListOf<String>()

    File(filePath).useLines { lines ->
        maps.addAll(lines)
    }
    var jeuEvoluer = JeuEvoluer()
    jeuEvoluer.maps.addAll(maps)
    tourPartie(jeuEvoluer)
}

fun tourPartie(jeuEvoluer: JeuEvoluer) {
    if(jeuEvoluer.round == jeuEvoluer.cartesBonus.size) {
        jeuEvoluer.cartesBonus.add(0)
    }
    if(jeuEvoluer.maps.size > jeuEvoluer.round) {
        val updatedJeuEvoluer = createList(jeuEvoluer.maps[jeuEvoluer.round], jeuEvoluer)
        val resultat = updatedJeuEvoluer.cartesJouer.count{ it in updatedJeuEvoluer.cartesBanque}
        if( resultat > 0) {
            updatedJeuEvoluer.ancienPoints += 2.0.pow(resultat - 1).toInt()
            updatedJeuEvoluer.points += 
                2.0.pow(resultat - 1).toInt() * (updatedJeuEvoluer.cartesBonus[updatedJeuEvoluer.round]+1)
        
            for(i in updatedJeuEvoluer.round+1..updatedJeuEvoluer.round+1+resultat) {
                if(i >= updatedJeuEvoluer.cartesBonus.size) {
                    updatedJeuEvoluer.cartesBonus.add(1)
                } else {
                    updatedJeuEvoluer.cartesBonus[i] += updatedJeuEvoluer.cartesBonus[updatedJeuEvoluer.round]+1
                }
            }
        }
        updatedJeuEvoluer.totalCartesAGratte += updatedJeuEvoluer.cartesBonus[updatedJeuEvoluer.round]+1
        updatedJeuEvoluer.round += 1
        tourPartie(updatedJeuEvoluer)  
    } else {
        println("Fin de la partie resultat: ${jeuEvoluer.points} carte a gratter: ${jeuEvoluer.totalCartesAGratte} : et ancien points: ${jeuEvoluer.ancienPoints}")
    }
}

fun createList(round: String, jeuEvoluer: JeuEvoluer): JeuEvoluer{
    val mapSplit = round.split(":")
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
    return jeuEvoluer.copy(cartesJouer = joueurList, cartesBanque = banqueList)
}
data class JeuEvoluer(
    var points: Int = 0,
    var ancienPoints: Int = 0,
    var round: Int = 0, 
    var cartesBonus: MutableList<Int> = mutableListOf(0,0,0,0,0,0),
    val maps: MutableList<String> = mutableListOf(),

    var cartesJouer: MutableList<Int> = mutableListOf(),
    var cartesBanque: MutableList<Int> = mutableListOf(),

    var totalCartesAGratte: Int = 0,
)