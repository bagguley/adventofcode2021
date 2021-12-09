package day8

fun main() {
    println(Part2.calc(testData))
    println(Part2.calc(data))
}

object Part2 {
    fun calc(data: List<String>): Int {
        val input = load(data)
        return input.sumOf {
            val map = calcDigits(it.first)
            it.second.map { c -> map.entries.find { e -> e.value == c }!!.key }.joinToString("").toInt()
        }
    }

    private fun calcDigits(digits: List<List<Char>>): MutableMap<Int, List<Char>> {
        val map = calcMap(digits)
        map[9] = digits.find { it.size == 6 && it.containsAll(map[4]!!) }!!  // Y
        map[2] = digits.find { it.size == 5 && !map[9]!!.containsAll(it) }!! // Y
        map[3] = digits.find { it.size == 5 && map[9]!!.containsAll(it) && it.containsAll(map[7]!!) }!! // Y
        map[5] = digits.find { it.size == 5 && it != map[2]!! && it != map[3]!! }!! // Y
        map[6] = digits.find { it.size == 6 && it.containsAll(map[5]!!) && it != map[9]}!! //
        map[0] = digits.find { !map.values.contains(it) }!!
        return map
    }

    private fun calcMap(digits: List<List<Char>>): MutableMap<Int,List<Char>> {
        val map = mutableMapOf<Int, List<Char>>()

        digits.forEach { chars ->
            when (chars.size) {
                2 -> 1
                3 -> 7
                4 -> 4
                7 -> 8
                else -> null
            }?.let{ map[it] = chars.toList() }
        }

        return map
    }

    private fun load(data: List<String>): List<Pair<List<List<Char>>, List<List<Char>>>> {
        return data.map {
            val p = it.split(" | ")
            p[0].split(" ").map{s->s.toCharArray().sorted().toList()} to p[1].split(" ").map{s->s.toCharArray().sorted().toList()}
        }
    }
}