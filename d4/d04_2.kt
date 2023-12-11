import java.io.File

fun main() {
    val filePath = "d4.txt"
    val maps = mutableListOf<String>()

    File(filePath).useLines { lines ->
        maps.addAll(lines)
    }
    val result = part2(input = maps)
    println(result)

}
 fun part2(input: List<String>): Int {
        var ret = 0
        var cards = ArrayList<Int>()
        for(i in 0 until input.size) {
            cards.add(1)
        }
        for(i in 0 until input.size) {
            var s = input[i]
            var l = s.split("\\s+".toRegex())
            var winners = HashSet<Int>()
            var iswinner = true
            var amt = 0
            for(sidx in 2 until l.size) {
                if(l[sidx] == "|") {
                    iswinner = false
                }
                else {
                    if(iswinner) {
                        winners.add(l[sidx].toInt())
                    }
                    else {
                        if(winners.contains(l[sidx].toInt())) {
                            amt++
                        }
                    }
                }
            }
            for(j in 0 until amt) {
                cards[i+j+1] += cards[i]
            }
        }
        for(x in cards) {
            ret += x
        }
        return ret
    }