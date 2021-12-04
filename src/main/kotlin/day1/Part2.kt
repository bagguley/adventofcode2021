package day1

fun main() {
    println(Part2.calc(testData))
    println(Part2.calc(data))
    println(Part2.calc2(data))
}

object Part2 {
    fun calc(data: List<Int>): Int = data.windowed(3, 1).map { (a, b, c) -> a + b + c }.windowed(2, 1).map { (a, b) -> b - a }.count { it > 0 }
    fun calc2(data: List<Int>): Int = data.windowed(3,1).map{it.sum()}.windowed(2,1).count{it[1]-it[0]>0}
}