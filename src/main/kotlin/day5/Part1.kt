package day5

fun main() {
    println(Part1.calc(testData))
    println(Part1.calc(data))
}

object Part1 {
    fun calc(data:List<String>): Int {
        val map = load(data)
        return map.count{p -> p.value >= 2}
    }

    fun load(data: List<String>):Map<Pair<Int, Int>, Int> {
        val map = mutableMapOf<Pair<Int, Int>, Int>()
        data.forEach {
            val coords = it.split(" -> ")
            val from = toPair(coords[0])
            val to = toPair(coords[1])
            if (isVerticalOrHorizontal(from, to)) {
                putIntoMap(map, from, to)
            }
        }
        return map
    }

    private fun toPair(str: String):Pair<Int,Int> {
        val elements = str.split(",")
        return Pair(elements[0].toInt(), elements[1].toInt())
    }

    private fun putIntoMap(map: MutableMap<Pair<Int,Int>,Int>, from:Pair<Int,Int> ,to: Pair<Int,Int>) {
        val line = createLine(from, to)
        for (pair in line) {
            val p = map[pair]
            if (p != null) {
                map[pair] = p.plus(1)
            } else {
                map[pair] = 1
            }
        }
    }

    private fun createLine(from:Pair<Int,Int>, to:Pair<Int,Int>):List<Pair<Int,Int>> {
        val result = mutableListOf<Pair<Int,Int>>()
        var x = from.first
        var y = from.second

        val xStep = calcStep(x, to.first)
        val yStep = calcStep(y, to.second)

        result.add(Pair(x, y))

        do {
            x += xStep
            y += yStep
            result.add(Pair(x, y))
        } while (x != to.first || y != to.second)

        return result
    }

    private fun calcStep(from: Int, to:Int): Int {
        if (to == from) return 0
        if (to > from) return 1
        return -1
    }

    private fun isVerticalOrHorizontal(from:Pair<Int,Int>, to:Pair<Int,Int>): Boolean {
        return calcStep(from.first, to.first) == 0 || calcStep(from.second, to.second) == 0
    }
}