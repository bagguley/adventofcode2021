package day21

import java.util.regex.Pattern

fun main() {
    println(Part1.calc(testData))
    println(Part1.calc(data))
}

object Part1 {
    fun calc(data: List<String>): Int {
        val board = Board()
        board.load(data)
        board.play()
        return board.score()
    }
}

class Board() {
    private val players: MutableList<Player> = mutableListOf()
    private val die: DeterministicDie = DeterministicDie()

    fun load(data: List<String>) {
        for (d in data) {
            val p = Pattern.compile("Player (\\d+) starting position: (\\d+)")
            val m = p.matcher(d)
            m.find()
            players.add(Player(m.group(1).toInt(), m.group(2).toInt()))
        }
    }

    fun play() {
        while (true) {
            for (p in players) {
                val i = die.roll() + die.roll() + die.roll()
                p.move(i)
                if (p.score >= 1000) return
            }
        }
    }

    fun score(): Int {
        return players.minOf { it.score } * die.count
    }
}

class DeterministicDie() {
    var num = 0
    var count = 0

    fun roll(): Int {
        count ++
        num += 1
        if (num > 100) num = 1
        return num
    }
}

class Player(var id: Int, var position: Int) {
    var score = 0

    fun move(n: Int) {
        position += (n % 10)
        if (position > 10) position -= 10
        score += position
    }

    override fun toString(): String {
        return "$id $position $score"
    }
}