package day16

fun main() {
    println(Part1.calc(testData).sumVersions())
    println(Part1.calc(data).sumVersions())
}

object Part1 {
    fun calc(str: String): Packet {
        val bits = Bits(str)
        return bits.process()
    }
}

class Bits(private val data: String) {
    private val literal = 4
    private val hexMap = mutableMapOf<Char,String>()
    private var decoded: String
    private var ptr = 0

    init {
        hexMap['0'] = "0000"
        hexMap['1'] = "0001"
        hexMap['2'] = "0010"
        hexMap['3'] = "0011"
        hexMap['4'] = "0100"
        hexMap['5'] = "0101"
        hexMap['6'] = "0110"
        hexMap['7'] = "0111"
        hexMap['8'] = "1000"
        hexMap['9'] = "1001"
        hexMap['A'] = "1010"
        hexMap['B'] = "1011"
        hexMap['C'] = "1100"
        hexMap['D'] = "1101"
        hexMap['E'] = "1110"
        hexMap['F'] = "1111"

        decoded = decode()
    }

    private fun decode(): String {
        val result = StringBuilder()

        for (c in data) {
            result.append(hexMap[c])
        }

        return result.toString()
    }

    fun process(): Packet {
        val version = decoded.substring(ptr until ptr + 3).toInt(2)
        ptr += 3
        val type = decoded.substring(ptr until ptr + 3).toInt(2)
        ptr += 3

        return if (type == literal) {
            processLiteral(version, type)
        } else {
            processOperator(version, type)
        }
    }

    private fun processLiteral(version: Int, type: Int): Literal {
        val string = StringBuilder()
        var indicator = '1'

        while (indicator == '1') {
            val next = decoded.substring(ptr until ptr + 5)
            ptr += 5
            indicator = next[0]
            val num = next.substring(1)
            string.append(num)
        }
        val value = string.toString().toLong(2)

        return Literal(version, type, value)
    }

    private fun processOperator(version: Int, type: Int): Operator {
        val lengthType = decoded.substring(ptr .. ptr).toInt(2)
        ptr += 1

        val length: Int = if (lengthType == 0) 15 else 11

        val subLength = decoded.substring(ptr until ptr + length).toInt(2)
        ptr += length

        val operator = Operator(version, type, lengthType, subLength)

        if (length == 11) {
            for (c in 1 .. subLength) {
                operator.add(process())
            }
        } else {
            val maxPtr = ptr + subLength
            while (ptr < maxPtr) {
                operator.add(process())
            }
        }
        return operator
    }
}

abstract class Packet(val version: Int) {
    abstract fun sumVersions(): Int
}

class Literal(version: Int, val type: Int, val value: Long) : Packet(version) {
    override fun sumVersions(): Int {
        return version
    }
}

class Operator(version: Int, val type: Int, val lengthType: Int, val subLength: Int): Packet(version) {
    private val subPackets = mutableListOf<Packet>()

    fun add(packet: Packet) {
        subPackets.add(packet)
    }

    override fun sumVersions(): Int {
        return version + subPackets.sumOf { it.sumVersions() }
    }
}