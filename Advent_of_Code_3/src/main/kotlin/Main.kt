import java.io.File

fun main(args: Array<String>) {
    Game()
}

class Game() {
    init {
        findMisplacedItems()
        findBadges()
    }

    private fun findBadges() {
        var sum = 0
        val list = mutableListOf<String>()

        File(ClassLoader.getSystemResource("input").file).useLines { lines -> list.addAll(lines) }

        for (i in 0 until  list.size step 3){
            val temp = findCommonItem(list[i],list[i+1])
            val common = findCommonItem(temp, list[i+2])
            sum += if (common.first().isUpperCase()) {
                common.first().code - 65 + 27;
            } else {
                common.first().code - 96
            }
        }
        //2864
        println(sum)
    }

    private fun findMisplacedItems() {
        var sum = 0
        File(ClassLoader.getSystemResource("input").file).forEachLine {
            var common = findCommonItem(it.substring(0, (it.length / 2)), it.substring(it.length / 2))
            sum += if (common.first().isUpperCase()) {
                common.first().code - 65 + 27;
            } else {
                common.first().code - 96
            }
        }
        // 8202
        println(sum)
    }

    private fun findCommonItem(container1: String, container2: String): String {
        return container1.filter { it in container2 }
    }
}