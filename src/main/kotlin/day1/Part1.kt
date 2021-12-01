package day1

fun main() {
    println(Part1.calc(testData))
    println(Part1.calc(data))
}

object Part1 {
    fun calc(data: List<Int>): Int = data.windowed(2, 1).map { (a, b) -> b - a }.count { it > 0 }
}