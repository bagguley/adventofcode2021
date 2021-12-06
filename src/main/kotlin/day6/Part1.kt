package day6

fun main() {
    println(Part1.calc(testData, 80))
    println(Part1.calc(data, 80))
}

object Part1 {
    fun calc(data:List<Int>, days:Int): Int {
        val fish = data.map { Lanternfish(it) }.toMutableList()

        for (day in 1 .. days) {
            val newFish = mutableListOf<Lanternfish>()
            fish.forEach{f -> f.coundown()?.let { newFish.add(it) }}
            fish.addAll(newFish)
        }

        return fish.size
    }
}

class Lanternfish(var daysLeft: Int) {
    fun coundown(): Lanternfish? {
        daysLeft--
        if (daysLeft <0) {
            daysLeft = 6
            return Lanternfish(8)
        }
        return null
    }
}