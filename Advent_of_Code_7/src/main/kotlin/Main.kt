import java.io.File

fun main(args: Array<String>) {
    Game()
}

class Game() {

    private val root = Folder("/", null)
    private var current = root
    private var lines: List<String> = File(ClassLoader.getSystemResource("input").file).readLines()
    private val limit = 100000
    private val totalSize = 70000000
    private val updateSize = 30000000

    enum class Command {
        CD, LS;
    }

    init {
        lines.indices.forEach {
            this.parseLine(it, lines[it])
        }
        // Part 1
        //println(this.root.sumAllChildsWithinLimit(limit))
        //Part 2
        val freeSpace = this.totalSize - this.root.getTotalSize()
        println(String.format("Need %s space",updateSize - freeSpace ))
        println(this.root.getChildsatOrOver(updateSize - freeSpace).minBy {it.getTotalSize() }.getTotalSize())
    }

    private fun parseLine(ind: Int, line: String) {
        if (isCommand(line)) {
            if (Command.CD.name.lowercase() == line.substring(2, 4)) {
                when (val target = line.substring(5)) {
                    ".." -> {
                        this.current = this.current.parent!!
                    }

                    "/" -> {
                        this.current = this.root
                    }

                    else -> {
                        this.current = current.childs.find { it.name == target }!!
                    }
                }
            } else if (Command.LS.name.lowercase() == line.substring(2, 4)) {
                var next = ind + 1
                while (next != this.lines.size && !isCommand(this.lines[next])) {
                    var temp = this.lines[next].split("\\s".toRegex(), 2)
                    if (temp[0] == "dir") {
                        this.current.childs.add(Folder(temp[1], this.current))
                    } else {
                        this.current.files[temp[1]] = temp[0].toInt()
                    }
                    next++
                }
            }
        }
        // Otherwise lines are product of ls command
    }

    private fun isCommand(line: String): Boolean {
        return line[0] == '$'
    }
}

class Folder(val name: String, val parent: Folder?) {
    var childs: MutableList<Folder> = ArrayList()
    var files: MutableMap<String, Int> = HashMap()

    fun sumAllChildsWithinLimit(limit: Int): Int {
        return this.childs.filter { it.isWithinLimit(limit) }
            .sumOf { it.getTotalSize() } + this.childs.sumOf { it.sumAllChildsWithinLimit(limit) }
    }

    private fun isWithinLimit(limit: Int): Boolean {
        return this.getTotalSize() <= limit
    }

    fun getChildsatOrOver(limit: Int): List<Folder>{
        var output: MutableList<Folder> = ArrayList()
        output.addAll(this.childs.filter { it.isOverLimit(limit) })
        this.childs.map { it.getChildsatOrOver(limit) }.forEach{output.addAll(it)}
        return output
    }

    private fun isOverLimit(limit: Int): Boolean {
        return this.getTotalSize() >= limit
    }

    /**
     * This could be calculated once since the size is static.
     * Compiler might even do that.
     */
    fun getTotalSize(): Int {
        return this.files.values.sumOf { it } + this.childs.sumOf { it.getTotalSize() }
    }
}