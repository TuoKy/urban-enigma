import java.io.File

fun main(args: Array<String>) {
    Game()
}

enum class Operation(val symbol: String) {
    ADD("+"), PROD("*");

    fun operate(cur: Long, targetSelf: Boolean, targetValue: Int): Long {
        return when (symbol) {
            "+" -> if (targetSelf) {
                cur + cur
            } else {
                cur + targetValue
            }

            "*" -> if (targetSelf) {
                cur * cur
            } else {
                cur * targetValue
            }

            else -> error("asd")
        }
    }

    companion object {
        fun geOperationByName(name: String): Operation =
            values().first { it.symbol == name }
    }
}

class Game() {

    private val rounds = 10000 // 20 for part 1
    private val relief = 1 // 3 for part 1


    private val lines = File(ClassLoader.getSystemResource("input").file).readLines()
    private val monkeys: MutableList<Monkey> = ArrayList()

    init {
        this.lines.chunked(7).forEach {
            parseMonkey(it)
        }

        val mod = monkeys.map { it.divider }.reduce { acc, i -> acc * i }

        for (i in 0 until rounds) {
            this.monkeys.forEach { it.doRound(monkeys, relief, mod) }
            // For debugging
            if (i == 0 || i == 19 || i == 999 || i == 1999 || i == 2999 || i == 3999 || i == 4999) {
                printStatus(i)
            }
        }

        printStatus()
        printResult()
    }

    private fun printResult() {
        var maxOne: Long = 0
        var maxTwo: Long = 0
        for (m in monkeys) {
            if (maxOne < m.timesInspected) {
                maxTwo = maxOne
                maxOne = m.timesInspected
            } else if (maxTwo < m.timesInspected) {
                maxTwo = m.timesInspected
            }
        }
        println("First Max Number: $maxOne")
        println("Second Max Number: $maxTwo")
        println(String.format("Product: %s", maxOne * maxTwo))
    }

    private fun printStatus(round: Int = 0) {
        println(String.format("----------\nRound %s", round + 1))
        this.monkeys.forEach {
            println(
                String.format(
                    "Monkey: %s inspected items %s times",
                    it.index,
                    it.timesInspected
                )
            )
        }
    }

    private fun parseMonkey(lines: List<String>) {
        val ind = "(\\d+)".toRegex().find(lines[0])!!.value.toInt()
        val items = "(\\d+)".toRegex().findAll(lines[1]).map { it.value.toLong() }.toMutableList()
        val operationParts = "Operation: new = old (.) (old|\\d+)".toRegex().find(lines[2])!!.groupValues
        val operation = Operation.geOperationByName(operationParts[1])
        val targetSelf = operationParts.last() == "old"
        val targetValue = if (!targetSelf) {
            operationParts.last().toInt()
        } else {
            0
        }
        val divider = "(\\d+)".toRegex().find(lines[3])!!.value.toInt()
        val trueTarget = "(\\d+)".toRegex().find(lines[4])!!.value.toInt()
        val falseTarget = "(\\d+)".toRegex().find(lines[5])!!.value.toInt()
        this.monkeys.add(Monkey(ind, items, operation, divider, trueTarget, falseTarget, targetValue, targetSelf))
    }
}

class Monkey(
    val index: Int,
    val items: MutableList<Long>,
    val operation: Operation,
    val divider: Int,
    val trueTarget: Int,
    val falseTarget: Int,
    var targetValue: Int = 0,
    var targetSelf: Boolean = false,

    ) {
    var timesInspected: Long = 0

    fun doRound(monkeys: MutableList<Monkey>, relief: Int = 3, mod: Int = 1) {
        for (i in 0 until items.size) {
            items[i] =
                (this.operation.operate(
                    items[i],
                    targetSelf,
                    targetValue
                ) / relief) % mod
            this.timesInspected++
        }

        val divisible = this.items.filter {
            it % this.divider == 0.toLong()
        }.toMutableList()

        this.items.removeAll(divisible)

        monkeys[trueTarget].items.addAll(divisible)
        monkeys[falseTarget].items.addAll(this.items)
        this.items.clear()
    }
}