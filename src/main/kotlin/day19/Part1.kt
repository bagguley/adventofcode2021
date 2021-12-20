package day19

fun main() {
    println(Part1.calc(testData))
    println(Part1.calc(data))
}

object Part1 {
    fun calc(data: List<String>): Int {
        val ocean = Ocean(data)

        return ocean.calcNumberOfBeacons()
    }
}

class Ocean(data: List<String>) {
    private val scanners: List<Scanner>  = data.mapIndexed{i,s -> Scanner(i,s)}

    fun calcNumberOfBeacons(): Int {
        var current = scanners.first()
        var remaining = scanners.minus(current)

        while (remaining.isNotEmpty()) {
            val matching = current.findMatches(remaining)
            val matchingIds = matching.map { it.id }
            val summed = current.coordinates + matching.flatMap{it.coordinates}
            remaining = remaining.filter { !matchingIds.contains(it.id) }
            current = Scanner(current.id, summed)
        }

        return current.coordinates.size
    }

    fun countMatches(): Int {
        var count = 0
        for (i in 0 until scanners.size-1) {
            for (j in i+1 until scanners.size) {
                if (scanners[i].matches(scanners[j])) count++
            }
        }
        return count
    }
}

class Scanner constructor(val id: Int, val coordinates: Set<Coordinate>) {

    constructor(id: Int, data: String) : this(id, data.split("\n").drop(1)
                .map { s -> s.split(",").map { it.toInt() }}.map { Coordinate(it[0], it[1], it[2]) }.toSet())

    fun findMatches(others: List<Scanner>): List<Scanner> {
        return others.mapNotNull { findMatches(it) }
    }

    private fun findMatches(other: Scanner): Scanner? {
        // Rotate the other scanner to produce 24 options to search.
        val rotations = other.allRotations()

        // For each rotation, use each beacon in this scanner and translate each beacon in the other
        // scanner to be in the same coordinates. Then translate the other beacons and see if they match
        // the beacons in this scanner. If 12 or more match, then we have an overlap.

        for (myRef in coordinates) {
            for (rotated in rotations) {
                val x = maxMatches2(myRef, rotated.coordinates)
                if (x != null) {
                    if (x.size >= 12) return Scanner(other.id, x)
                }
            }
        }
        return null
    }

    fun matches(other: Scanner): Boolean {
        // Rotate the other scanner to produce 24 options to search.
        val rotations = other.allRotations()

        // For each rotation, use each beacon in this scanner and translate each beacon in the other
        // scanner to be in the same coordinates. Then translate the other beacons and see if they match
        // the beacons in this scanner. If 12 or more match, then we have an overlap.

        for (myRef in coordinates) {
            for (rotated in rotations) {
                val maxMatches = maxMatches(myRef, rotated.coordinates)
                if (maxMatches >= 12) return true
            }
        }
        return false
    }

    private fun maxMatches(reference: Coordinate, otherCoordinates: Set<Coordinate>): Int {
        return otherCoordinates.maxOf { other ->
            val translation = reference.minus(other)
            val otherTranslated = otherCoordinates.map { it.add(translation) }.toSet()
            val inter = coordinates.intersect(otherTranslated)
            inter.size
        }
    }

    private fun maxMatches2(reference: Coordinate, otherCoordinates: Set<Coordinate>): Set<Coordinate>? {
        otherCoordinates.forEach{ other ->
            val translation = reference.minus(other)
            val otherTranslated = otherCoordinates.map { it.add(translation) }.toSet()
            val inter = coordinates.intersect(otherTranslated)
            if (inter.size >= 12) return otherTranslated
        }
        return null
    }

    private fun allRotations(): Set<Scanner> {
        val result = mutableSetOf<Scanner>()

        result.add(Scanner(id, coordinates.map { it.rotateX(0).rotateZ(0) }.toSet()))
        result.add(Scanner(id, coordinates.map { it.rotateX(0).rotateZ(90) }.toSet()))
        result.add(Scanner(id, coordinates.map { it.rotateX(0).rotateZ(180) }.toSet()))
        result.add(Scanner(id, coordinates.map { it.rotateX(0).rotateZ(270) }.toSet()))
        result.add(Scanner(id, coordinates.map { it.rotateX(90).rotateZ(0) }.toSet()))
        result.add(Scanner(id, coordinates.map { it.rotateX(90).rotateZ(90) }.toSet()))
        result.add(Scanner(id, coordinates.map { it.rotateX(90).rotateZ(180) }.toSet()))
        result.add(Scanner(id, coordinates.map { it.rotateX(90).rotateZ(270) }.toSet()))
        result.add(Scanner(id, coordinates.map { it.rotateX(180).rotateZ(0) }.toSet()))
        result.add(Scanner(id, coordinates.map { it.rotateX(180).rotateZ(90) }.toSet()))
        result.add(Scanner(id, coordinates.map { it.rotateX(180).rotateZ(180) }.toSet()))
        result.add(Scanner(id, coordinates.map { it.rotateX(180).rotateZ(270) }.toSet()))
        result.add(Scanner(id, coordinates.map { it.rotateX(270).rotateZ(0) }.toSet()))
        result.add(Scanner(id, coordinates.map { it.rotateX(270).rotateZ(90) }.toSet()))
        result.add(Scanner(id, coordinates.map { it.rotateX(270).rotateZ(180) }.toSet()))
        result.add(Scanner(id, coordinates.map { it.rotateX(270).rotateZ(270) }.toSet()))
        result.add(Scanner(id, coordinates.map { it.rotateY(90).rotateZ(0) }.toSet()))
        result.add(Scanner(id, coordinates.map { it.rotateY(90).rotateZ(90) }.toSet()))
        result.add(Scanner(id, coordinates.map { it.rotateY(90).rotateZ(180) }.toSet()))
        result.add(Scanner(id, coordinates.map { it.rotateY(90).rotateZ(270) }.toSet()))
        result.add(Scanner(id, coordinates.map { it.rotateY(270).rotateZ(0) }.toSet()))
        result.add(Scanner(id, coordinates.map { it.rotateY(270).rotateZ(90) }.toSet()))
        result.add(Scanner(id, coordinates.map { it.rotateY(270).rotateZ(180) }.toSet()))
        result.add(Scanner(id, coordinates.map { it.rotateY(270).rotateZ(270) }.toSet()))

        return result
    }
}

class Coordinate(val x: Int, val y: Int, val z: Int) {
    fun rotateX(deg: Int): Coordinate {
        val newX = x
        val newY = y * degCos(deg) - z * degSin(deg)
        val newZ = y * degSin(deg) + z * degCos(deg)
        return Coordinate(newX, newY, newZ)
    }

    fun rotateY(deg: Int): Coordinate {
        val newX = x * degCos(deg) + z * degSin(deg)
        val newY = y
        val newZ = z * degCos(deg) - x * degSin(deg)
        return Coordinate(newX, newY, newZ)
    }

    fun rotateZ(deg: Int): Coordinate {
        val newX = x * degCos(deg) - y * degSin(deg)
        val newY = x * degSin(deg) + y * degCos(deg)
        val newZ = z
        return Coordinate(newX, newY, newZ)
    }

    fun allRotations(): Set<Coordinate> {
        val result = mutableSetOf<Coordinate>()

        result.add(this.rotateX(0).rotateZ(0))
        result.add(this.rotateX(0).rotateZ(90))
        result.add(this.rotateX(0).rotateZ(180))
        result.add(this.rotateX(0).rotateZ(270))
        result.add(this.rotateX(90).rotateZ(0))
        result.add(this.rotateX(90).rotateZ(90))
        result.add(this.rotateX(90).rotateZ(180))
        result.add(this.rotateX(90).rotateZ(270))
        result.add(this.rotateX(180).rotateZ(0))
        result.add(this.rotateX(180).rotateZ(90))
        result.add(this.rotateX(180).rotateZ(180))
        result.add(this.rotateX(180).rotateZ(270))
        result.add(this.rotateX(270).rotateZ(0))
        result.add(this.rotateX(270).rotateZ(90))
        result.add(this.rotateX(270).rotateZ(180))
        result.add(this.rotateX(270).rotateZ(270))
        result.add(this.rotateY(90).rotateZ(0))
        result.add(this.rotateY(90).rotateZ(90))
        result.add(this.rotateY(90).rotateZ(180))
        result.add(this.rotateY(90).rotateZ(270))
        result.add(this.rotateY(270).rotateZ(0))
        result.add(this.rotateY(270).rotateZ(90))
        result.add(this.rotateY(270).rotateZ(180))
        result.add(this.rotateY(270).rotateZ(270))

        return result
    }

    private fun degCos(deg: Int): Int {
        return when (deg) {
            0 -> 1
            90 -> 0
            180 -> -1
            270 -> 0
            else -> throw Exception("Help")
        }
    }

    private fun degSin(deg: Int): Int {
        return when (deg) {
            0 -> 0
            90 -> 1
            180 -> 0
            270 -> -1
            else -> throw Exception("Help")
        }
    }

    fun minus(ref: Coordinate): Coordinate {
        return Coordinate(x - ref.x, y - ref.y, z - ref.z)
    }

    fun add(ref: Coordinate): Coordinate {
        return Coordinate(x + ref.x, y + ref.y, z + ref.z)
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Coordinate) return false
        return x == other.x && y == other.y && z == other.z
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        result = 31 * result + z
        return result
    }
}