package day4

fun main() {
    println(Part2.calc(testData))
    println(Part2.calc(data))
}

object Part2 {
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
            var toRemove = mutableListOf<Card>()
            for (card in cards) {
                card.play(num)
                if (card.isComplete()) {
                    if (cards.size == 1) return Pair(card, num)
                    toRemove.add(card)
                }
            }
            cards.removeAll(toRemove)
        }
        return null
    }
}