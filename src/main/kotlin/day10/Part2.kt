package day10

import java.util.*

fun main() {
    println(Part2.calc(testData))
    println(Part2.calc(data))
}

object Part2 {
    private val closeMap = mapOf(')' to '(', ']' to '[', '}' to '{', '>' to '<')
    private val scoreMap = mapOf('(' to 1, '[' to 2, '{' to 3, '<' to 4)

    fun calc(data: List<String>):Long {
        val scores = data.map{ validateLine(it) }.filter { it.size > 0 }.map { score(it) }.sorted()
        return scores[scores.size/2]
    }

    private fun validateLine(line: String): Stack<Char> {
        val stack = Stack<Char>()
        for (c in line) {
            if (closeMap.containsValue(c)) stack.push(c) else
                if (closeMap.containsKey(c)) {
                    val popped = stack.pop()
                    if (popped != closeMap[c]) return Stack()
                }
        }
        return stack
    }

    private fun score(stack: Stack<Char>): Long {
        return stack.reversed().fold(0){ acc, c -> 5 * acc + scoreMap[c]!! }
    }
}