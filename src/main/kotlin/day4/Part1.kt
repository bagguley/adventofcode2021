package day4

fun main() {
    println(Part1.calc(testData))
    println(Part1.calc(data))
}

object Part1 {
    private val cards:MutableList<Card> = mutableListOf()
    private var numbers: MutableList<Int> = mutableListOf()

    fun calc(data: List<String>): Int {
        cards.clear()
        numbers.clear()
        load(data)
        val result: Pair<Card, Int>? = play()
        if (result != null) return result.first.score() * result.second
        return -1
    }

    private fun load(data: List<String>) {
        numbers = data[0].split(",").map{it.toInt()}.toMutableList()
        for (i in 1 until data.size) {
            cards.add(Card(data[i]))
        }
    }

    private fun play(): Pair<Card, Int>? {
        for (num in numbers) {
            for (card in cards) {
                card.play(num)
                if (card.isComplete()) {
                    return Pair(card, num)
                }
            }
        }
        return null
    }
}

class Card(data: String) {
    private val rows: List<Row>

    init {
        rows = data.split("\n").map { Row(it) }
    }

    fun play(num: Int) {
        for (row in rows) {
            row.play(num)
        }
    }

    fun isComplete() : Boolean {
        return rows.any { it.isComplete() } || columnsComplete()
    }

    fun score(): Int {
        return rows.sumOf { it.score() }
    }

    private fun columnsComplete(): Boolean {
        val numRows = rows.size
        val numCols = rows[0].size()

        for (col in 0 until numCols) {
            val column = mutableListOf<Pair<Int,Boolean>>()
            for (row in 0 until numRows) {
                column.add(rows[row].get(col))
            }
            if (column.all { it.second }) return true
        }
        return false
    }
}

class Row(numbers: String) {
    private val numbers: MutableList<Pair<Int,Boolean>>

    init {
        this.numbers = numbers.trim().split(Regex("\\s+")).map { Pair(it.toInt(), false) }.toMutableList()
    }

    fun play(num: Int) {
        for (n in 0 until numbers.size) {
            if (num == numbers[n].first) numbers[n] = Pair(num, true)
        }
    }

    fun score(): Int {
        return numbers.filter { !it.second }.sumOf { it.first }
    }

    fun isComplete(): Boolean {
        return numbers.all { it.second }
    }

    fun size(): Int {
        return numbers.size
    }

    fun get(index: Int): Pair<Int, Boolean> {
        return numbers[index]
    }
}