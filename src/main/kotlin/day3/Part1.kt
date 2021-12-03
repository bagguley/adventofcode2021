package day3

fun main() {
    println(Part1.calc(testData))
    println(Part1.calc(data))
}

object Part1 {
    fun calc(input: List<String>): Int {
        val map = mutableMapOf<Int, Int>()
        val lineLen = input[0].length
        val inputCount = input.size
        input.forEach{calc(it, map)}

        var str = ""
        for (i in 0 until lineLen) {
            val c = map[i]
            c?.let {
                str += if (it > inputCount/2.0) '1' else '0'
            }
        }

        val max = Math.pow(2.0, lineLen.toDouble()) -1
        val gamma = str.toInt(2)
        val epsilon = max - gamma
        println("$gamma, ${epsilon.toInt()}")
        return gamma * epsilon.toInt()
    }

    fun calc(input: String, map: MutableMap<Int, Int>) {
        input.forEachIndexed { index, chr ->
            when (chr) {
                '1' -> map.compute(index) { _, v -> if (v == null) 1 else v + 1 }
            }
        }
    }
}