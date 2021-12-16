package day15

import java.util.*
import kotlin.Comparator

fun main() {
    println(Part1.calc(testData))
    println(Part1.calc(data))
}

object Part1 {
    fun calc(data:List<String>): Int {
        val cavern = Cavern(data)
        return cavern.calc()
    }
}

class Cavern(private val data:List<String>) {
    private val width = data[0].length
    private val height = data.size
    private val pathList = TreeSet(PositionComparator())
    private val visited = mutableSetOf<Pair<Int,Int>>()

    fun calc(): Int {
        pathList.add(Position(0 to 0, 0, distanceFromEnd(0 to 0)))

        var cheapest = pathList.minByOrNull { pos -> pos.estimatedFinalCost }!!
        while (cheapest.coordinates != Pair(width-1,height-1)) {
            pathList.remove(cheapest)
            visited.add(cheapest.coordinates)
            addNext(cheapest)
            cheapest = pathList.minByOrNull { pos -> pos.estimatedFinalCost }!!
        }

        return pathList.first().cost
    }

    private fun addNext(position: Position) {
        val up = up(position.coordinates)
        val right = right(position.coordinates)
        val down = down(position.coordinates)
        val left = left(position.coordinates)

        val nextPositions = listOfNotNull(up, right, down, left).map {Position(it, costOf(it) + position.cost, costOf(it) + position.cost + distanceFromEnd(it))  }
        nextPositions.forEach {
            val found = pathList.find { p -> p.coordinates == it.coordinates }
            if (!visited.contains(it.coordinates)) {
                if (found != null) {
                    if (found.estimatedFinalCost > it.estimatedFinalCost) {
                        pathList.remove(found)
                        pathList.add(it)
                    }
                } else {
                    pathList.add(it)
                }
            }
        }
    }

    private fun costOf(coordinates: Pair<Int,Int>): Int {
        return data[coordinates.second][coordinates.first].digitToInt()
    }

    private fun distanceFromEnd(coordinates: Pair<Int,Int>): Int {
        return (width - 1 - coordinates.first) + (height - 1 - coordinates.second)
    }

    private fun up(coordinates: Pair<Int,Int>): Pair<Int,Int>? {
        val x = coordinates.first
        val y = coordinates.second - 1

        if (x >= 0 && y >= 0 && x < width && y < height) return x to y
        return null
    }

    private fun right(coordinates: Pair<Int,Int>): Pair<Int,Int>? {
        val x = coordinates.first + 1
        val y = coordinates.second

        if (x >= 0 && y >= 0 && x < width && y < height) return x to y
        return null
    }

    private fun down(coordinates: Pair<Int,Int>): Pair<Int,Int>? {
        val x = coordinates.first
        val y = coordinates.second + 1

        if (x >= 0 && y >= 0 && x < width && y < height) return x to y
        return null
    }

    private fun left(coordinates: Pair<Int,Int>): Pair<Int,Int>? {
        val x = coordinates.first -1
        val y = coordinates.second

        if (x >= 0 && y >= 0 && x < width && y < height) return x to y
        return null
    }
}

data class Position(val coordinates: Pair<Int,Int>, val cost: Int, val estimatedFinalCost: Int)

class PositionComparator : Comparator<Position> {
    override fun compare(p0: Position?, p1: Position?): Int {
        if (p0 == null && p1 == null) return 0
        if (p0 == null) return -1
        if (p1 == null) return 1
        if (p0.coordinates.first == p1.coordinates.first && p0.coordinates.second == p1.coordinates.second) return 0
        if (p0.coordinates.first == p1.coordinates.first) return p1.coordinates.second - p0.coordinates.second
        return p1.coordinates.first - p0.coordinates.first
    }
}