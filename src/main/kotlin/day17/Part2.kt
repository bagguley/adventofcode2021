package day17

fun main() {
    println(Part2.calc(testData[0], testData[1], testData[2], testData[3]))
    println(Part2.calc(data[0], data[1], data[2], data[3]))
}

object Part2 {
    fun calc(minX: Int, maxX: Int, minY: Int, maxY: Int): Int {
        val world = World(minX, maxX, minY, maxY)
        val list = mutableListOf<Probe>()
        for (x in 0 .. maxX) {
            for (y in minY .. 10000) {
                val probe = world.hits(x,y)
                if (probe != null) list.add(probe)
            }
        }
        return list.count()
    }
}