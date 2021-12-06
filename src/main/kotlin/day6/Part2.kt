package day6

fun main() {
    println(Part2.calc(testData, 256))
    println(Part2.calc(data, 256))
}

object Part2 {
    val map = mutableMapOf<Pair<Int,Int>,Long>()

    fun calc(fish:List<Int>, daysLeft:Int):Long {
        return fish.sumOf { numOfFish(it, daysLeft) }
    }

    private fun numOfFish(counter:Int, daysLeft:Int): Long {
        if (map.containsKey(Pair(counter,daysLeft))) {
            return map[Pair(counter,daysLeft)]!!
        }
        if (daysLeft == 0) return 1

        return if (counter == 0) {
            val count = numOfFish(6, daysLeft-1)
            val newFish = numOfFish(8, daysLeft-1)
            map[Pair(counter, daysLeft)] = count + newFish
            map[Pair(counter, daysLeft)]!!
        } else {
            val count = numOfFish(counter-1, daysLeft-1)
            map[Pair(counter, daysLeft)] = count
            map[Pair(counter, daysLeft)]!!
        }
    }
}
