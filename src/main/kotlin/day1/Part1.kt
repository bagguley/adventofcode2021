package day1

fun main() {
    println(Part1.calc(testData))
    println(Part1.calc(data))
    println(Part1.calc2(data))
}

object Part1 {
    fun calc(data: List<Int>): Int = data.windowed(2, 1).map { (a, b) -> b - a }.count { it > 0 }
    fun calc2(data: List<Int>): Int = data.windowed(2,1).count{it[1]-it[0]>0}
}