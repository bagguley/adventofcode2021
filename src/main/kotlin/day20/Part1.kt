package day20

fun main() {
    println(Part1.calc(testData))
    println(Part1.calc(data))
}

object Part1 {
    fun calc(data: List<String>): Int {
        val world = World()
        world.load(data)
        world.iterate(2)
        return world.worldMapLit.size
    }
}

class World() {
    var worldMapLit = mutableSetOf<Pair<Int,Int>>()
    var worldMapOff = mutableSetOf<Pair<Int,Int>>()
    var algorithm: String = ""
    var outsideWorld: Boolean = false

    fun load(data: List<String>) {
        algorithm = data[0]

        val world = data[1].split("\n")
        val width = world[0].length
        val height = world.size

        for (y in 0 until height) {
            for (x in 0 until width) {
                if (world[y][x] == '#') {
                    worldMapLit.add(x to y)
                } else {
                    worldMapOff.add(x to y)
                }
            }
        }
    }

    fun worldGet(x: Int, y: Int): Char {
        if (worldMapLit.contains(x to y)) return '1'
        else if (worldMapOff.contains(x to y)) return '0'
        return if (outsideWorld) '1' else '0'
    }

    fun iterate(times: Int) {
        for (i in 0 until times) {
            iterate()
        }
    }

    fun iterate() {
        val newWorldMapLit = mutableSetOf<Pair<Int,Int>>()
        val newWorldMapOff = mutableSetOf<Pair<Int,Int>>()
        val litToProcess = litPointsToProcess()

        for (p in litToProcess) {
            val pointToDecimal: Int = pointToDecimal(p)
            val algorithmResult = algorithm[pointToDecimal] == '#'
            if (algorithmResult) newWorldMapLit.add(p) else newWorldMapOff.add(p)
        }

        val offToProcess = offPointsToProcess()

        for (p in offToProcess) {
            val pointToDecimal: Int = pointToDecimal(p)
            val algorithmResult = algorithm[pointToDecimal] == '#'
            if (algorithmResult) newWorldMapLit.add(p) else newWorldMapOff.add(p)
        }

        worldMapLit = newWorldMapLit
        worldMapOff = newWorldMapOff

        outsideWorld = if (outsideWorld) algorithm[511] == '#' else algorithm[0] == '#'
    }

    fun litPointsToProcess(): Set<Pair<Int,Int>> {
        return worldMapLit.flatMap { expandedPoint(it) }.toSet()
    }

    fun offPointsToProcess(): Set<Pair<Int,Int>> {
        return worldMapOff.flatMap { expandedPoint(it) }.toSet()
    }

    fun expandedPoint(point: Pair<Int,Int>): Set<Pair<Int,Int>> {
        return setOf(
            point.first-1 to point.second-1,
            point.first to point.second-1,
            point.first+1 to point.second-1,

            point.first-1 to point.second,
            point.first to point.second,
            point.first+1 to point.second,

            point.first-1 to point.second+1,
            point.first to point.second+1,
            point.first+1 to point.second+1
        )
    }

    fun pointToDecimal(point: Pair<Int,Int>): Int {
        val a = worldGet(point.first-1, point.second-1)
        val b = worldGet(point.first, point.second-1)
        val c = worldGet(point.first+1, point.second-1)

        val d = worldGet(point.first-1, point.second)
        val e = worldGet(point.first, point.second)
        val f = worldGet(point.first+1, point.second)

        val g = worldGet(point.first-1, point.second+1)
        val h = worldGet(point.first, point.second+1)
        val i = worldGet(point.first+1, point.second+1)

        return "$a$b$c$d$e$f$g$h$i".toInt(2)
    }
}