package day2

fun main() {
    println(Part2.calc(testData))
    println(Part2.calc(data))
}

object Part2 {
    fun calc(data: List<String>): Int {
        var xPos = 0
        var aim = 0
        var depth = 0
        data.forEach {
            val arr = it.split(" ")
            when (arr[0]) {
                "forward" -> {xPos += arr[1].toInt(); depth += aim * arr[1].toInt() }
                "down" -> aim += arr[1].toInt()
                "up" -> aim -= arr[1].toInt()
                else -> {
                    throw IllegalStateException("Help")
                }
            }
        }
        return xPos * depth
    }
}