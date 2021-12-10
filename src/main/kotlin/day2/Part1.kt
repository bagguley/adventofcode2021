package day2

fun main() {
    println(Part1.calc(testData))
    println(Part1.calc(data))
    println(Part1.calc2(data))
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

    fun calc2(data:List<String>): Int {
        return data.map{it.split(" ")}.let{d->d.filter{it[0][0]=='f'}.sumOf{it[1].toInt()}*(d.filter{it[0][0]=='d'}.sumOf{it[1].toInt()}+d.filter{it[0][0]=='u'}.sumOf{-it[1].toInt()})}
    }
}