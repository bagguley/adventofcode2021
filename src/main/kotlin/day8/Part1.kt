package day8

fun main() {
    println(Part1.calc(testData))
    println(Part1.calc(data))

    println(data.map{it.split(" | ").let{s->s[0].split(" ") to s[1].split(" ")}}.flatMap{it.second.filter{s->s.length in listOf(2,3,4,7)}}.count())
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