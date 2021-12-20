package day20

fun main() {
    println(Part2.calc(testData))
    println(Part2.calc(data))
}

object Part2 {
    fun calc(data: List<String>): Int {
        val world = World()
        world.load(data)
        world.iterate(50)
        return world.worldMapLit.size
    }
}