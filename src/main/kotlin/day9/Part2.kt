package day9

fun main() {
    println(Part2.calc(testData))
    println(Part2.calc(data))
}

object Part2 {
    fun calc(data: List<String>): Int {
        val sizes = basins(data).map { size(it, data) }
        return sizes.sorted().takeLast(3).reduce{a,b->a*b}
    }

    private fun basins(data: List<String>): List<Pair<Int,Int>> {
        val width = data[0].length
        val height = data.size

        val basins = mutableListOf<Pair<Int,Int>>()
        for (y in 0 until height) {
            for (x in 0 until width) {
                val res = (if (x-1 >= 0) data[y][x] < data[y][x-1] else true) &&
                        (if (x+1 < width) data[y][x] < data[y][x+1] else true) &&
                        (if (y-1 >= 0) data[y][x] < data[y-1][x] else true) &&
                        (if (y+1 < height) data[y][x] < data[y+1][x] else true)
                if (res) basins.add(x to y)
            }
        }
        return basins
    }

    private fun size(basin: Pair<Int,Int>, data: List<String>): Int {
        val members = mutableSetOf<Pair<Int,Int>>()
        var toAdd = setOf(basin)
        while (toAdd.isNotEmpty()) {
            members.addAll(toAdd)
            val neighbours = toAdd.flatMap { neighbours(it.first, it.second, data) }.toSet()
            toAdd = neighbours - members
        }
        return members.size
    }

    private fun neighbours(x: Int, y: Int, data: List<String>):Set<Pair<Int,Int>> {
        val width = data[0].length
        val height = data.size
        val result = mutableSetOf<Pair<Int,Int>>()
        if (x-1 >= 0 && data[y][x-1] < '9') result.add(x-1 to y)
        if (x+1 < width && data[y][x+1] < '9') result.add(x+1 to y)
        if (y-1 >= 0 && data[y-1][x] < '9') result.add(x to y-1)
        if (y+1 < height && data[y+1][x] < '9') result.add(x to y+1)
        return result
    }
}