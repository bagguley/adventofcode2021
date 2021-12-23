package day21

fun main() {
    Part2.calc()

    println("444356092776315")
    println("341960390180808")
}

object Part2 {
    // TODO Generate these programmatically
    private val rollToNumWorlds = mapOf(3 to 1, 4 to 3, 5 to 6, 6 to 7, 7 to 6, 8 to 3, 9 to 1)
    private val possibleDieScore = rollToNumWorlds.keys

    private val nextPosition = mapOf(
        1  to listOf(4, 5, 6, 7, 8, 9, 10),
        2  to listOf(5, 6, 7, 8, 9, 10, 1),
        3  to listOf(6, 7, 8, 9, 10, 1, 2),
        4  to listOf(7, 8, 9, 10, 1, 2, 3),
        5  to listOf(8, 9, 10, 1, 2, 3, 4),
        6  to listOf(9, 10, 1, 2, 3, 4, 5),
        7  to listOf(10, 1, 2, 3, 4, 5, 6),
        8  to listOf(1, 2, 3, 4, 5, 6, 7),
        9  to listOf(2, 3, 4, 5, 6, 7, 8),
        10 to listOf(3, 4, 5, 6, 7, 8, 9)
    )

    fun calc() {
        val first = recurse(Hop(4, 0, 1, 0), 21)

        val second = recurse(Hop(8, 0, 1, 0), 21)

        val x = first.groupBy { h -> h.steps }
            .map { entry ->
                val stepNum = entry.key
                val numWorlds = entry.value.sumOf { it.worlds }
                val secondWinners = second.filter { s -> s.steps < stepNum }.sumOf { it.worlds }
                numWorlds - secondWinners
        }.sum()

        println(x)
    }

    private fun recurse(hop: Hop, targetScore: Int): List<Hop> {
        val result = mutableListOf<Hop>()

        if (hop.score >= targetScore) return listOf(hop)

        val nextHops = possibleDieScore.map { roll ->
            val newPosition = nextPosition[hop.position]!![roll - 3]
            Hop(newPosition, newPosition + hop.score, hop.worlds * rollToNumWorlds[roll]!!, hop.steps + 1)
        }

        for (h in nextHops) {
            result.addAll(recurse(h, targetScore))
        }
        return result
    }
}

class Hop(val position: Int, val score: Long, val worlds: Long, val steps: Int) {
    override fun toString(): String {
        return "$score, $worlds, $steps\n"
    }
}