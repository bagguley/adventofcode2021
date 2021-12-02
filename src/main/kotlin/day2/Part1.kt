package day2

fun main() {
    println(Part1.calc(testData))
    println(Part1.calc(data))
}

object Part1 {
    fun calc(data: List<String>): Int {
        return data.map { toVector(it) }.reduce { a, b -> Pair(a.first + b.first, a.second + b.second) }
            .let { a -> a.first * a.second }
    }

    private fun toVector(str: String): Pair<Int, Int> {
        val arr = str.split(" ")
        return when (arr[0]) {
            "forward" -> Pair(arr[1].toInt(), 0)
            "down" -> Pair(0, arr[1].toInt())
            "up" -> Pair(0, -arr[1].toInt())
            else -> {
                throw IllegalStateException("Help")
            }
        }
    }
}