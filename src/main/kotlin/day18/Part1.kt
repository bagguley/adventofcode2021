package day18

import kotlin.math.ceil

fun main() {
    println(Part1.calc(testData))
    println(Part1.calc(data))
}

object Part1 {
    fun calc(data: List<String>):Int {
        val s = data.reduce { a,b ->
            val loadera = Loader(a)
            loadera.load()

            val loaderb = Loader(b)
            loaderb.load()

            loadera.root.sum(loaderb.root)
            loadera.process()
            loadera.root.toString()
        }

        val load = Loader(s)
        load.load()
        return load.root.magnitude()
    }
}

class Loader(val data: String) {
    val root = Node(null)

    fun load() {
        var current = root
        for (c in data) {
            when (c) {
                '[' -> {
                    val left = Node(current)
                    current.left = left
                    current = left
                }
                ']' -> {
                    current = current.parent!!
                }
                ',' -> {
                    val right = Node(current.parent)
                    current.parent!!.right = right
                    current = right
                }
                else -> {
                    current.value = c.digitToInt()
                }
            }
        }
    }

    fun process() {
        var changes: Boolean

        do {
            changes = false
            val foundDepth = root.findDepth4()
            if (foundDepth != null) {
                changes = true
                foundDepth.explode()
            } else {
                val foundSplit = root.findSplit()
                if (foundSplit != null) {
                    changes = true
                    foundSplit.split()
                }
            }
        } while (changes)
    }
}

class Node(var parent: Node?) {
    var left: Node?= null
    var right: Node? = null
    var value: Int? = null

    override fun toString(): String {
        if (value != null) return value.toString()
        return "[" + left.toString() + "," + right.toString() + "]"
    }

    fun magnitude(): Int {
        if (value != null) return value!!
        return (3 * left!!.magnitude() + 2*right!!.magnitude())
    }

    fun findDepth4(): Node? {
        return findDepth4(0)
    }

    private fun findDepth4(count: Int): Node? {
        if (count == 4) {
            if (value == null) return this
            if (parent!!.right != this) {
                return parent!!.right!!.findDepth4(count)
            }
            return null
        }

        if (value != null) {
            if (parent!!.right != this) {
                return parent!!.right!!.findDepth4(count)
            }
            return null
        }

        val l = left!!.findDepth4(count+1)
        if (l != null) return l
        return right!!.findDepth4(count+1)
    }

    fun findSplit(): Node? {
        if (value != null) {
            if (value!! >= 10) return this
            if (parent!!.right != this) return parent!!.right!!.findSplit()
            return null
        }
        val l = left!!.findSplit()
        if (l != null) return l
        return right!!.findSplit()
    }

    private fun nextLeft(): Node? {
        if (parent == null) return null
        if (parent!!.left == this) return parent!!.nextLeft()
        return parent!!.left!!.rightValue()
    }

    private fun nextRight(): Node? {
        if (parent == null) return null
        if (parent!!.right == this) return parent!!.nextRight()
        return parent!!.right!!.leftValue()
    }

    private fun rightValue(): Node {
        if (value != null) return this
        return right!!.rightValue()
    }

    private fun leftValue(): Node {
        if (value != null) return this
        return left!!.leftValue()
    }

    fun explode() {
        val nextLeft = nextLeft()
        val nextRight = nextRight()

        if (nextLeft != null) {
            nextLeft.value = nextLeft.value!! + left!!.value!!
        }

        if (nextRight != null) {
            nextRight.value = nextRight.value!! + right!!.value!!
        }

        this.value = 0
        this.left = null
        this.right = null
    }

    fun split() {
        val l = value!! / 2
        val r = ceil(value!! / 2.0).toInt()
        this.value = null

        this.left = Node(this)
        this.left!!.value = l

        this.right = Node(this)
        this.right!!.value = r
    }

    fun sum(toAdd: Node) {
        val newLeft = Node(this)
        newLeft.left = left
        newLeft.right = right
        left!!.parent = newLeft
        right!!.parent = newLeft
        left = newLeft

        toAdd.parent = this
        right = toAdd
    }
}