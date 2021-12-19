package day18

fun main() {
    println(Part2.calc(testData))
    println(Part2.calc(data))
}

object Part2 {
    fun calc(data: List<String>):Int {
        val list = mutableListOf<Int>()

        for (d in data) {
            val l = mutableListOf<String>()
            l.addAll(data)
            l.remove(d)

            for (x in l) {
                val load = Loader(d)
                load.load()
                val xl = Loader(x)
                xl.load()

                load.root.sum(xl.root)
                load.process()
                val m = load.root.magnitude()
                list.add(m)
            }
        }

        return list.maxOf { it }
    }
}