package day11

fun main() {
    println(Part1.calc(testData))
    println(Part1.calc(data))
}

object Part1 {
    fun calc(data: List<List<Cell>>): Long {
        val board = Board(data)

        var count = 0L
        for (i in 1..100) {
            count += board.step()
        }
        return count
    }
}

class Cell(var value: Int, var flashed: Boolean = false) {
    fun increment(): Boolean {
        value++
        flashed = value == 10
        return flashed
    }

    fun reset() {
        if (value > 9) value = 0
        flashed = false
    }

    override fun toString(): String {
        return value.toString()
    }
}

class Board(val data: List<List<Cell>>) {
    private val neighbours = mutableListOf(-1 to -1, 0 to -1, 1 to -1, -1 to 0, 1 to 0, -1 to 1, 0 to 1, 1 to 1)

    var width = data[0].size
    var height = data.size

    fun step(): Long {
        var flashCount = 0L
        val incrementFlashed = increment()
        flashCount += incrementFlashed.size
        val flashed = flash(incrementFlashed)
        flashCount += flashed
        reset()
        return flashCount
    }

    private fun increment():List<Pair<Int,Int>> {
        val result = mutableListOf<Pair<Int,Int>>()
        val width = data[0].size
        val height = data.size

        for (y in 0 until height) {
            for (x in 0 until width) {
                if (data[y][x].increment()) result.add(x to y)
            }
        }

        return result
    }

    private fun flash(flashed: List<Pair<Int, Int>>): Long {
        var result = 0L
        var toFlash = flashed
        while (toFlash.isNotEmpty()) {
            toFlash = increment(toFlash)
            result += toFlash.size
        }
        return result
    }

    private fun increment(flashed: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int,Int>>()

        for (p in flashed) {
            val neighbours = neighbours(p.first, p.second)
            neighbours.forEach{ n ->
                if (data[n.second][n.first].increment()) result.add(n.first to n.second)
            }
        }

        return result
    }

    private fun reset() {
        val width = data[0].size
        val height = data.size

        for(y in 0 until height) {
            for (x in 0 until width) {
                data[y][x].reset()
            }
        }
    }

    private fun neighbours(x: Int, y: Int): List<Pair<Int, Int>> {
        return neighbours.map { p -> p.first+x to p.second+y }.filter { p -> p.first >=0 && p.second >= 0 && p.first < width && p.second < height}
    }
}