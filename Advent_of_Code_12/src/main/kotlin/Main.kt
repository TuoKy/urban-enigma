import java.io.File
import java.util.*
import kotlin.collections.ArrayList

fun main(args: Array<String>) {
    Game()
}

class Game() {

    private val map: MutableList<Char> = ArrayList()
    private var start: Int = 0
    private var end: Int = 0
    private val width: Int
    private var visited: BooleanArray
    private var costs: IntArray

    init {
        var temp = 0
        File(ClassLoader.getSystemResource("input").file).forEachLine { s ->
            temp = s.length
            this.map.addAll(s.toCharArray().toList())
        }
        this.width = temp

        this.visited = BooleanArray(this.map.size) { false }
        this.costs = IntArray(this.map.size) { Int.MAX_VALUE }

        this.start = this.map.indexOf('S')
        this.map[this.start] = 'a'
        this.end = this.map.indexOf('E')
        this.map[end] = 'z'

        println(partA(this.start))
        partB()
    }

    private fun partA(startingPoint: Int): Int {
        this.costs[startingPoint] = 0

        while (!visited[end]) {
            val cur = findMinUnvisited() ?: break
            checkNeighbors(cur)
            this.visited[cur] = true
        }


        return this.costs[end]
    }

    private fun partB() {
        val starts = this.map.mapIndexedNotNull { index, elem -> index.takeIf { elem == 'a' } }
        val options: MutableList<Int> = ArrayList()
        starts.forEach {
            Arrays.fill(visited, false);
            Arrays.fill(costs, Int.MAX_VALUE)
            options.add(partA(it))
        }
        println(options.min())
    }


    private fun findMinUnvisited(): Int? {
        var min = Int.MAX_VALUE
        var ind: Int? = null

        for (i in 0 until this.costs.size) {
            if (!this.visited[i] && this.costs[i] < min) {
                min = this.costs[i]
                ind = i
            }
        }

        return ind
    }

    private fun updateCosts(position: Int, value: Int) {
        if (value < this.costs[position]) {
            this.costs[position] = value
        }
    }

    private fun compare(currentValue: Int, otherValue: Int, position: Int, cost: Int) {
        if (currentValue >= otherValue || otherValue == currentValue + 1) {
            updateCosts(position, cost + 1)
        }
    }

    private fun checkNeighbors(position: Int) {
        val current = this.map[position]
        val currentCost = this.costs[position]

        if (position != 0 && position % this.width != 0 && position - 1 >= 0) {
            compare(current.code, this.map[position - 1].code, position - 1, currentCost)
        }
        if (position + 1 % this.width != 0 && position + 1 < this.map.size) {
            compare(current.code, this.map[position + 1].code, position + 1, currentCost)
        }
        if (position - width > 0) {
            compare(current.code, this.map[position - width].code, position - width, currentCost)
        }
        if (position + width < this.map.size) {
            compare(current.code, this.map[position + width].code, position + width, currentCost)
        }
    }
}