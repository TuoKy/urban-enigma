import java.io.File

fun main(args: Array<String>) {
    Game()
}

class Game() {
    init {
        var fullyOverlapping = 0
        var overlapping = 0
        File(ClassLoader.getSystemResource("input").file).forEachLine {
            val elfPair = Pair(it)
            if (elfPair.isFullyOverlapping()) {
                fullyOverlapping++
            }
            if (elfPair.isOverlapping()) {
                overlapping++
            }
        }
        /*
         *  Fullu overlapping: 657
         *  All overlapping: 938
         */
        println(String.format("Fully overlapping: %s\nAll overlapping: %s", fullyOverlapping, overlapping))
    }

}

class Pair(input: String) {
    val elf1: Elf
    val elf2: Elf

    init {
        var temp = input.split(",")

        var temp1 = temp[0].split("-")
        this.elf1 = Elf(temp1[0].toInt(), temp1[1].toInt());
        var temp2 = temp[1].split("-")
        this.elf2 = Elf(temp2[0].toInt(), temp2[1].toInt());
    }

    fun isOverlapping(): Boolean {

        if ((elf1.getEnd() < elf2.getStart()) ||
            (elf1.getStart() > elf2.getEnd()) ||
            (elf2.getEnd() < elf1.getStart()) ||
            (elf2.getStart() > elf1.getEnd())
        ) {
            return false;
        }
        return true
    }

    fun isFullyOverlapping(): Boolean {
        if ((elf1.getStart() >= elf2.getStart() && elf1.getEnd() <= elf2.getEnd()) ||
            (elf2.getStart() >= elf1.getStart() && elf2.getEnd() <= elf1.getEnd())
        ) {
            return true;
        }
        return false
    }

}

class Elf(private val start: Int, private val end: Int) {

    fun getStart(): Int {
        return this.start;
    }

    fun getEnd(): Int {
        return this.end;
    }
}