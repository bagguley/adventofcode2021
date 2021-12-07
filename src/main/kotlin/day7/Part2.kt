package day7

import kotlin.math.abs

fun main() {
    println(Part2.calc(testData))
    println(Part2.calc(data))

    println((0..data.maxOf{it}).minOf{x->data.sumOf{abs(it-x)*(1+abs(it-x))/2}})
}

object Part2 {
    fun calc(data: List<Int>): Int {
        return sequence { for (x in 0..data.maxOf { it }) { yield(data.sumOf { abs(it - x)*(1 + abs(it - x))/2 }) }}.minOf { it }
    }
}