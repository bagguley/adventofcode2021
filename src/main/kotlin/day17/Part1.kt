package day17

fun main() {
    println(Part1.calc(testData[0], testData[1], testData[2], testData[3]))
    println(Part1.calc(data[0], data[1], data[2], data[3]))
}

object Part1 {
    fun calc(minX: Int, maxX: Int, minY: Int, maxY: Int): Int {
        val world = World(minX, maxX, minY, maxY)
        val list = mutableListOf<Probe>()
        for (x in 0 .. maxX) {
            for (y in 0 .. 10000) {
                val probe = world.hits(x,y)
                if (probe != null) list.add(probe)
            }
        }
        return list.maxOf { it.maxY }
    }
}

class World(var minX: Int, var maxX: Int, var minY: Int, var maxY: Int) {
    var probe = Probe(0, 0, 0, 0)

    fun hits(xVel:Int, yVel:Int): Probe? {
        probe = Probe(0, 0, xVel, yVel)

        while (beforeTarget()) {
            step()
            if (inTarget()) return probe
        }
        return null
    }

    private fun inTarget(): Boolean {
        return probe.x in minX..maxX && probe.y in minY..maxY
    }

    private fun beforeTarget(): Boolean {
        return probe.x <= maxX && probe.y >= minY
    }

    private fun step() {
        probe.x += probe.xVel
        probe.updateY()

        if (probe.xVel > 0) probe.xVel--
        if (probe.xVel < 0) probe.xVel++
        probe.yVel--
    }
}

class Probe(var x: Int, var y: Int, var xVel: Int, var yVel: Int) {
    var maxY = 0

    fun updateY() {
        y += yVel
        if (y > maxY) maxY = y
    }
}