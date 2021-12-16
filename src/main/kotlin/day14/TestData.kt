package day14

val testData = """NNCB

CH -> B
HH -> N
CB -> H
NH -> C
HB -> C
HC -> B
HN -> C
NN -> C
BH -> H
NC -> B
NB -> B
BN -> B
BB -> N
BC -> B
CC -> N
CN -> C""".split("\n\n").let { it[0] to it[1].split("\n").map { rule -> rule.split(" -> ").let{x->x[0] to x[1]}} }