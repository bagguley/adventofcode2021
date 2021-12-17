package day15

fun main() {
    println(Part2.calc(testData))
    println(Part2.calc(data))
}

object Part2 {
    fun calc(data:List<String>): Int {
        val cavern = Cavern(multiplyData(data))
        return cavern.calc()
    }

    private fun multiplyData(data:List<String>): List<String> {
        val widened = mutableListOf<String>()

        for (line in data) {
            val newLine = line + lineAdd(line, 1) + lineAdd(line, 2) + lineAdd(line, 3) + lineAdd(line, 4)
            widened.add(newLine)
        }

        val lengthened = mutableListOf<String>()
        lengthened.addAll(widened)

        for(x in 1 .. 4) {
            for (line in widened) {
                val newLine = lineAdd(line, x)
                lengthened.add(newLine)
            }
        }

        return lengthened
    }

    private fun lineAdd(str:String, add:Int): String {
        return str.map { ((it.digitToInt()-1 + add) % 9)+1 }.joinToString(separator = "")
    }
}