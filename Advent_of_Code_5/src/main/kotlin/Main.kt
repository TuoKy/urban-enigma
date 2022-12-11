import java.io.File
import java.util.stream.Collectors

fun main(args: Array<String>) {
    Game()
}

class Game() {
    private var list: MutableList<Container> = ArrayList()

    init {
        var start = true
        File(ClassLoader.getSystemResource("input").file).forEachLine {
            if (it.isEmpty()) {
                start = false
            } else if (start) {
                this.parseStart(it)
            } else {
                this.parseInstruction(it, true)
            }
        }
        list.forEach() {
            println(it)
        }
        println(buildString {
            list.forEach { s ->
                append(s.stack.first())
            }
        })
    }

    private fun parseInstruction(line: String, crane: Boolean) {
        val reg = "([0-9]+)".toRegex()
        val a = reg.findAll(line, 0).iterator().asSequence().toList()
        if(crane){
            var temp = this.list[a[1].value.toInt() - 1].stack
                .stream()
                .limit(a[0].value.toLong())
                .collect(Collectors.toList())
            for (i in a[0].value.toInt() downTo 1) {
                this.list[a[1].value.toInt() - 1].stack.removeFirst()
            }
            this.list[a[2].value.toInt() - 1].stack.addAll(0,temp)
        } else {
            for (i in a[0].value.toInt() downTo 1) {
                var temp = this.list[a[1].value.toInt() - 1].stack.removeFirst()
                this.list[a[2].value.toInt() - 1].stack.addFirst(temp)
            }
        }
    }

    private fun parseStart(line: String) {
        val reg = "(\\s{3}\\s?|\\[\\w\\]\\s?)".toRegex()
        val a: Sequence<MatchResult> = reg.findAll(line, 0)

        a.forEachIndexed { i, it ->
            if (this.list.size <= i) {
                this.list.add(Container(i + 1))
            }
            if (it.value[1].isLetter()) {
                this.list[i].stack.addLast(it.value[1])
            }
        }
    }
}

class Container(private val ind: Int) {
    var stack = ArrayDeque<Char>()

    @Override
    override fun toString(): String {
        return String.format("Stack index: %s. Stack: %s", ind, stack)
    }
}