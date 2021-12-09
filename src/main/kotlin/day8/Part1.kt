package day8

fun main() {
    println(Part1.calc(testData))
    println(Part1.calc(data))
}

object Part1 {
    private val uniqueLengths = listOf(2, 4, 3, 7)
    fun calc(data: List<String>): Int {
        val input = load(data)
        return input.flatMap{it.second.filter{s->s.length in uniqueLengths }}.count()
    }

    private fun load(data: List<String>): List<Pair<List<String>, List<String>>> {
        return data.map {
            val p = it.split(" | ")
            p[0].split(" ") to p[1].split(" ")
        }
    }
}