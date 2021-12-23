package day21

fun main() {
    World(4, 8).loop()
    World(5, 6).loop()
}

class Node(val aScore: Int, val aPosition: Int, val bScore: Int, val bPosition: Int, val count: Long) {
    override fun toString(): String {
        return "($aScore, $bScore, $count)"
    }
}

class World(var first: Int, var second: Int) {
    private val diceRolls = diceRolls()
    private var nodes = listOf<Node>()
    var aWinners = 0L
    var bWinners = 0L

    fun loop() {
        nodes = listOf(Node(0, first, 0, second, 1))

        while (nodes.isNotEmpty()) {
            nodes = nodes.flatMap { s -> nextANodes(s.aScore, s.aPosition, s.bScore, s.bPosition, s.count) }
            nodes = nodes.groupBy { s -> listOf(s.aScore, s.aPosition, s.bScore, s.bPosition) }
                .map { g -> Node(g.key[0], g.key[1], g.key[2], g.key[3], g.value.sumOf { i -> i.count }) }

            val aWins = nodes.filter { it.aScore >= 21}
            val aWinCount = aWins.sumOf { it.count }
            aWinners += aWinCount
            nodes = nodes.filter { !aWins.contains(it) }

            nodes = nodes.flatMap { s -> nextBNodes(s.aScore, s.aPosition, s.bScore, s.bPosition, s.count) }
            nodes = nodes.groupBy { s -> listOf(s.aScore, s.aPosition, s.bScore, s.bPosition) }
                .map { g -> Node(g.key[0], g.key[1], g.key[2], g.key[3], g.value.sumOf { i -> i.count }) }

            val bWins = nodes.filter { it.bScore >= 21}
            val bWinCount = bWins.sumOf { it.count }
            bWinners += bWinCount
            nodes = nodes.filter { !bWins.contains(it) }
        }

        println(aWinners)
        println(bWinners)
    }

    private fun nextANodes(aScore: Int, aPosition: Int, bScore: Int, bPosition: Int, count: Long): List<Node> {
        val result = mutableListOf<Node>()

        // Roll the dice
        for (roll in diceRolls) {
            val newPosition = nextPosition[aPosition]!![roll - 3]
            result.add(Node(aScore + newPosition, newPosition, bScore, bPosition, count))
        }

        return result
    }

    private fun nextBNodes(aScore: Int, aPosition: Int, bScore: Int, bPosition: Int, count: Long): List<Node> {
        val result = mutableListOf<Node>()

        // Roll the dice
        for (roll in diceRolls) {
            val newPosition = nextPosition[bPosition]!![roll - 3]
            result.add(Node(aScore, aPosition, bScore + newPosition, newPosition, count))
        }

        return result
    }

    private fun diceRolls(): List<Int> {
        val result = mutableListOf<Int>()
        for (a in 1 .. 3) {
            for (b in 1 .. 3) {
                for (c in 1 .. 3) {
                    result.add(a+b+c)
                }
            }
        }
        return result
    }

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
}