package day11

fun main() {
    println(Part2.calc(testData))
    println(Part2.calc(data))
}

object Part2 {
    fun calc(data: List<List<Cell>>): Long {
        val board = Board(data)

        var count = 0L
        while (!board.isSynchronised()) {
            board.step()
            count++
        }
        return count
    }
}

fun Board.isSynchronised():Boolean {
    return data.all { line -> line.all { cell -> cell.value == 0 } }
}