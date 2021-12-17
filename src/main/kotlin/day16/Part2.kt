package day16

fun main() {
    println(Part2.calc(testData).calcValue())
    println(Part2.calc(data).calcValue())
}

object Part2 {
    fun calc(str: String): Packet {
        val bits = Bits(str)
        return bits.process()
    }
}