package day2

fun main() {
    println(calc(testData))
    println(calc(data))
}

fun calc(data: List<String>): Int {
    return data.map{toVector(it)}.reduce{a, b -> Pair(a.first + b.first, a.second + b.second)}.let { a -> a.first * a.second }
}

fun toVector(str: String) : Pair<Int, Int> {
    val arr = str.split(" ")
    return when(arr[0]) {
        "forward" -> Pair(arr[1].toInt(), 0)
        "down" -> Pair(0, arr[1].toInt())
        "up" -> Pair(0, -arr[1].toInt())
        else -> {throw IllegalStateException("Help")}
    }
}