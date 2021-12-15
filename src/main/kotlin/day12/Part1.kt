package day12

fun main() {
    println(Part1.calc(testData1))
    println(Part1.calc(testData2))
    println(Part1.calc(testData3))
    println(Part1.calc(data))
}

object Part1 {
    fun calc(data: List<String>): Int {
        val map = load(data)

        return pathfinder(map, mutableListOf(mutableListOf("start"))).size
    }

    private fun load(data:List<String>): Map<String, List<String>> {
        val result = mutableMapOf<String, MutableList<String>>()

        val paths = data.map{it.split("-")}

        for (p in paths) {
            if (result.containsKey(p[0])) result[p[0]]?.add(p[1]) else {
                result[p[0]] = mutableListOf(p[1])
            }
            if (result.containsKey(p[1])) result[p[1]]?.add(p[0]) else {
                result[p[1]] = mutableListOf(p[0])
            }
        }

        return result
    }

    private fun pathfinder(map: Map<String, List<String>>, acc:MutableList<List<String>>):List<List<String>> {
        val newAcc = mutableListOf<List<String>>()

        for (loc in acc) {
            if (loc.last() != "end") {
                val next = map[loc.last()]!!

                for (n in next) {
                    if (n.uppercase() == n) {
                        val newLoc = loc.toMutableList()
                        newLoc.add(n)
                        newAcc.add(newLoc)
                    } else {
                        if (!loc.contains(n)) {
                            val newLoc = loc.toMutableList()
                            newLoc.add(n)
                            newAcc.add(newLoc)
                        }
                    }
                }
            } else {
                newAcc.add(loc)
            }
        }
        if (newAcc.all { it.last() == "end" }) return newAcc
        return pathfinder(map, newAcc)
    }
}
