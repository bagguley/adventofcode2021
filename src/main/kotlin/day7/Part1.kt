package day7

import kotlin.math.abs

fun main() {
    println(Part1.calc(testData))
    println(Part1.calc(data))

    println((0..data.maxOf{it}).minOf{x->data.sumOf{abs(it-x)}})
}

object Part1 {
    fun calc(data: List<Int>): Int {
        val max = data.maxOf { it }
        return sequence {
            for (x in 0..max) {
                yield(data.sumOf { abs(it - x) })
            }
        }.minOf { it }
    }
}