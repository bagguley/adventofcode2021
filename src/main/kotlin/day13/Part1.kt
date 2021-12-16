package day13

fun main() {
    println(Part1.calc(testData))
    println(Part1.calc(data))
}

object Part1 {
    fun calc(data:List<List<String>>): Int {
        val paper = load(data)
        val folded = paper.fold(data[1][0])
        return folded.countDots()
    }

    fun load(data:List<List<String>>): Paper {
        val coords = data[0]
        val dots = coords.map { it.split(",").let { s -> Pair(s[0].toInt(), s[1].toInt()) } }.toSet()
        return Paper(dots)
    }
}

class Paper(var dots: Set<Pair<Int,Int>>) {
    fun fold(instruction: String): Paper {
        if (instruction.startsWith("fold along y=")) return foldUp(instruction.substring(13).toInt())
        return foldLeft(instruction.substring(13).toInt())
    }

    fun foldUp(lineYPos: Int): Paper {
        val newDots = mutableSetOf<Pair<Int,Int>>()

        for (dot in dots) {
            if (dot.second < lineYPos) newDots.add(dot)
            else if (dot.second > lineYPos) {
                newDots.add(foldUp(dot, lineYPos))
            }
        }
        return Paper(newDots)
    }

    fun foldLeft(lineXPos: Int): Paper {
        val newDots = mutableSetOf<Pair<Int,Int>>()

        for (dot in dots) {
            if (dot.first < lineXPos) newDots.add(dot)
            else if (dot.first > lineXPos) {
                newDots.add(foldLeft(dot, lineXPos))
            }
        }
        return Paper(newDots)
    }

    fun countDots(): Int {
        return dots.count()
    }

    private fun foldUp(dot: Pair<Int,Int>, lineYPos: Int) : Pair<Int, Int> {
        val flipY = lineYPos - (dot.second - lineYPos)
        return dot.first to flipY
    }

    private fun foldLeft(dot: Pair<Int,Int>, lineXPos: Int): Pair<Int, Int> {
        val flipX = lineXPos - (dot.first - lineXPos)
        return flipX to dot.second
    }
}