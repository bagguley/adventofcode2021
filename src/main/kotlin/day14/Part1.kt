package day14

import java.lang.StringBuilder

fun main() {
    println(Part1.calc(testData))
    println(Part1.calc(data))
}

object Part1 {
    fun calc(data: Pair<String, List<Pair<String, String>>>): Int {
        var str = data.first
        val map = data.second
        for (x in 0..9) {
            str = walk(str, map)
        }

        val counts = str.groupBy { it }.map { it.value.size }.sorted()
        return counts.last() - counts.first()
    }

    private fun walk(string: String, map: List<Pair<String, String>>): String {
        val str = StringBuilder()
        for (x in 0..string.length-2) {
            val s = string[x].toString() + string[x+1]
            str.append(string[x])
            map.find { s == it.first }?.let { str.append(it.second) }
        }
        str.append(string.last())
        return str.toString()
    }
}
