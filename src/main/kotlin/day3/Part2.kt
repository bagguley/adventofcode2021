package day3

fun main() {
    println(Part2.calc(testData))
    println(Part2.calc(data))
}

object Part2 {
    fun calc(input: List<String>): Int {
        val oxygen = oxygen(input)
        val scrubber = scrubber(input)
        println("$oxygen, $scrubber")
        return oxygen * scrubber
    }

    private fun oxygen(input: List<String>): Int {
        val lineLen = input[0].length
        var list = input
        while (list.size > 1) {
            for (i in 0 until lineLen) {
                list = maxAtPos(list, i)
            }
        }
        return list[0].toInt(2)
    }

    private fun scrubber(input: List<String>): Int {
        val lineLen = input[0].length
        var list = input
        while (list.size > 1) {
            for (i in 0 until lineLen) {
                list = minAtPos(list, i)
            }
        }
        return list[0].toInt(2)
    }

    private fun maxAtPos(input: List<String>, index: Int): MutableList<String> {
        val list0 = mutableListOf<String>()
        val list1 = mutableListOf<String>()
        input.forEach {
            when (it[index]) {
                '0' -> list0.add(it)
                '1' -> list1.add(it)
            }
        }
        if (list0.size == 0) return list1
        if (list1.size == 0) return list0
        else if (list0.size > list1.size) return list0
        return list1
    }

    private fun minAtPos(input: List<String>, index: Int): MutableList<String> {
        val list0 = mutableListOf<String>()
        val list1 = mutableListOf<String>()
        input.forEach {
            when (it[index]) {
                '0' -> list0.add(it)
                '1' -> list1.add(it)
            }
        }
        if (list0.size == 0) return list1
        if (list1.size == 0) return list0
        else if (list0.size <= list1.size) return list0
        return list1
    }
}