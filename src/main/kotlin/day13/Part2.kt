package day13

import java.lang.StringBuilder

fun main() {
    println(Part2.calc(data))
}

object Part2 {
    fun calc(data:List<List<String>>): String {
        var paper = load(data)

        for (instruction in data[1]) {
            paper = paper.fold(instruction)
        }
        return paper.print()
    }

    fun load(data:List<List<String>>): Paper {
        val coords = data[0]
        val dots = coords.map { it.split(",").let { s -> Pair(s[0].toInt(), s[1].toInt()) } }.toSet()
        return Paper(dots)
    }
}

fun Paper.print():String {
    val width = dots.maxOf { it.first }
    val height = dots.maxOf { it.second }
    val str = StringBuilder()
    for (y in 0 .. height) {
        for (x in 0 .. width) {
            str.append(if (dots.contains(x to y)) "#" else ".")
        }
        str.append("\n")
    }
    return str.toString()
}