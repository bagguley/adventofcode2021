package day14

fun main() {
    println(Part2.calc(testData.first, testData.second))
    println(Part2.calc(data.first, data.second))
}

object Part2 {
    fun calc(string: String, mapping: List<Pair<String, String>>): Long {
        val processor = Processor(mapping.toMap())
        return processor.calc(string)
    }
}

class Processor(private val expand: Map<String,String>) {
    private val cache: MutableMap<Pair<String,Int>, Map<Char, Long>> = mutableMapOf()

    fun calc(string: String): Long {
        var map = mapOf<Char, Long>()
        val length = string.length

        for (x in 0 until length-1) {
            val str = string[x].toString() + string[x+1]
            val walkResult = walk(str, 0)
            map = (map.keys + walkResult.keys).associateWith{ (map[it] ?: 0) + (walkResult[it] ?: 0) }
        }

        val fullMap = map.toMutableMap()
        string.forEach { c ->
            val count = fullMap[c] ?: 0
            fullMap[c] = count + 1
        }

        val counts = fullMap.map { it.value }.sorted()

        return counts.last() - counts.first()
    }

    private fun expansion(string: String): String? {
        return expand[string]
    }

    private fun walk(string: String, depth: Int): Map<Char, Long> {
        if (depth == 40 || expansion(string) == null) return mapOf()

        val expansion = expansion(string)!!
        val leftStr = string[0] + expansion
        val rightStr = expansion + string[1]

        val cost = mapOf(expansion[0] to 1)
        val leftCost = cache[leftStr to depth + 1] ?: walk(leftStr, depth + 1)
        val rightCost = cache[rightStr to depth + 1] ?: walk(rightStr, depth + 1)

        cache[leftStr to depth + 1] = leftCost
        cache[rightStr to depth + 1] = rightCost

        return (leftCost.keys + rightCost.keys + cost.keys).associateWith { (leftCost[it] ?: 0) + (rightCost[it] ?: 0) + (cost[it] ?: 0)}
    }
}