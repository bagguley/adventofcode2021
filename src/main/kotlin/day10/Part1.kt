package day10

import java.util.*

fun main() {
    println(Part1.calc(testData))
    println(Part1.calc(data))
}

object Part1 {
    private val closeMap = mapOf(')' to '(', ']' to '[', '}' to '{', '>' to '<')
    private val scoreMap = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)

    fun calc(data: List<String>):Int {
        return data.sumOf { validateLine(it) }
    }

    private fun validateLine(line: String):Int {
        val stack = Stack<Char>()
        for (c in line) {
            if (closeMap.containsValue(c)) stack.push(c) else
                if (closeMap.containsKey(c)) {
                    val popped = stack.pop()
                    if (popped != closeMap[c]) return scoreMap[c]!!
                }
        }
        return 0
    }
}