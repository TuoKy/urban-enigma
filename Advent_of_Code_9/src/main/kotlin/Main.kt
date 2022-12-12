import java.io.File
import kotlin.collections.ArrayList
import kotlin.math.abs

fun main(args: Array<String>) {
    Game()
}

class Game() {

    /*
     *  If the head is ever two steps directly up, down, left, or right from the tail,
     *  the tail must also move one step in that direction
     *  Otherwise, if the head and tail aren't touching and aren't in the same row or column,
     *  the tail always moves one step diagonally
     */

    private val rope = Rope(9)

    init {
        File(ClassLoader.getSystemResource("input").file).forEachLine { s ->
            parseLine(s)
        }
        println(this.rope.tail.last().path.distinct().size)
    }

    private fun parseLine(line: String) {
        val parameters = line.split("\\s".toRegex())
        val direction = Direction.geDirectionTypeByName(parameters[0])
        val distance = parameters[1]
        for (i in 0 until distance.toInt()) {
            rope.head.move(direction)
            rope.updateTail()
        }
    }
}

enum class Direction(val simpleName: String, val move: Pair<Int, Int>) {
    UP("U", Pair(0, 1)), DOWN("D", Pair(0, -1)),
    LEFT("L", Pair(-1, 0)), RIGHT("R", Pair(1, 0));

    companion object {
        fun geDirectionTypeByName(name: String): Direction =
            values().first { it.simpleName == name }
    }
}

class Rope(private val size: Int) {
    val head = RopeUnit()
    val tail = List(size){
        RopeUnit()
    }

    fun updateTail() {
        tail[0].update(this.head.location)
        for (i in 1 until tail.size){
            tail[i].update(tail[i-1].location)
        }
    }
}

class RopeUnit {

    var location = Pair(0, 0)
    val path: MutableList<Pair<Int, Int>> = ArrayList()

    init {
        this.path.add(this.location)
    }

    /**
     * Checks if the Unit is too far away from other
     * Updates the position accordingly and adds movement to path
     * TODO: Could be clearer
     */
    fun update(other: Pair<Int, Int>) {
        if (abs(this.location.first - other.first) > 1) {
            if (this.location.first > other.first) {
                this.move(Direction.LEFT)
            } else {
                this.move(Direction.RIGHT)
            }
            if (this.location.second != other.second) {
                if (this.location.second > other.second) {
                    this.move(Direction.DOWN)
                } else {
                    this.move(Direction.UP)
                }
            }
        } else if (abs(this.location.second - other.second) > 1) {
            if (this.location.second > other.second) {
                this.move(Direction.DOWN)
            } else {
                this.move(Direction.UP)
            }
            if (this.location.first != other.first) {
                if (this.location.first > other.first) {
                    this.move(Direction.LEFT)
                } else {
                    this.move(Direction.RIGHT)
                }
            }
        }
        this.path.add(this.location)
    }


    fun move(direction: Direction) {
        this.location = Pair(
            this.location.first + direction.move.first,
            this.location.second + direction.move.second
        )
    }

}