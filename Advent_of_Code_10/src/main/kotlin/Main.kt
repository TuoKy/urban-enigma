import java.io.File

fun main(args: Array<String>) {
    Game()
}

enum class Command {
    ADDX, NOOP;

    companion object {
        fun getCommand(name: String): Command = valueOf(name.uppercase())
    }
}

class Game() {

    private val rounds: MutableList<Int> = mutableListOf(1)
    private val picture: MutableList<MutableList<String>> = ArrayList()

    init {
        File(ClassLoader.getSystemResource("input").file).forEachLine { s ->
            parseLine(s)
        }
        drawLine(0, 0, 40)
        drawLine(1, 40, 80)
        drawLine(2, 80, 120)
        drawLine(3, 120, 160)
        drawLine(4, 160, 200)
        drawLine(5, 200, 240)


        //Part 1
        println(
            String.format(
                "20: %s, 60: %s, 100: %s, 140: %s, 180: %s, 220: %s, Total: %s",
                rounds[19] * 20,
                rounds[59] * 60,
                rounds[99] * 100,
                rounds[139] * 140,
                rounds[179] * 180,
                rounds[219] * 220,
                rounds[19] * 20 +
                        rounds[59] * 60 +
                        rounds[99] * 100 +
                        rounds[139] * 140 +
                        rounds[179] * 180 +
                        rounds[219] * 220
            )
        )
        // 2md part
        println(picture[0])
        println(picture[1])
        println(picture[2])
        println(picture[3])
        println(picture[4])
        println(picture[5])
    }

    private fun drawLine(lineNumber: Int, start: Int, end: Int) {
        picture.add(lineNumber, ArrayList())
        for (i in start until end) {
            if (rounds[i] == i - start || rounds[i] + 1 == i - start || rounds[i] - 1 == i - start) {
                picture[lineNumber].add("#")
            } else {
                picture[lineNumber].add(".")
            }
        }
    }

    private fun parseLine(line: String) {
        val parameters = line.split("\\s".toRegex())
        val cmd = Command.getCommand(parameters[0])
        if (cmd == Command.NOOP) {
            this.rounds.add(this.rounds.last())
        } else {
            this.rounds.add(this.rounds.last())
            this.rounds.add(this.rounds.last() + parameters[1].toInt())
        }
    }
}