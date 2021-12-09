package day9

fun main() {
    println(Part1.calc(testData))
    println(Part1.calc(data))
}

object Part1 {
    fun calc(data: List<String>): Int {
        val width = data[0].length
        val height = data.size

        var count = 0
        for (y in 0 until height) {
            for (x in 0 until width) {
               val res = (if (x-1 >= 0) data[y][x] < data[y][x-1] else true) &&
                       (if (x+1 < width) data[y][x] < data[y][x+1] else true) &&
                       (if (y-1 >= 0) data[y][x] < data[y-1][x] else true) &&
                       (if (y+1 < height) data[y][x] < data[y+1][x] else true)
                if (res) count += (1 + data[y][x].toString().toInt())
            }
        }
        return count
    }
}