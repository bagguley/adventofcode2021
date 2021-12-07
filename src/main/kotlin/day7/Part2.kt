package day7

import kotlin.math.abs

fun main() {
    println(Part2.calc(testData))
    println(Part2.calc(data))
}

object Part2 {
    fun calc(data: List<Int>): Int {
        val max = data.maxOf { it }
        return sequence {
            for (x in 0..max) {
                yield(data.sumOf { abs(it - x)*(1 + abs(it - x))/2 })
            }
        }.minOf { it }
    }
}