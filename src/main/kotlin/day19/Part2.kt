package day19

fun main() {
    println(Part2.calc(testData))
    println(Part2.calc(data))
}

object Part2 {
    fun calc(data: List<String>): Int {
        val ocean = Ocean(data)

        return ocean.calcManhattanDistance()
    }
}