import java.io.File

fun main(args: Array<String>) {
    Game()
}

class Game() {

    private val forest: MutableList<List<Int>> = ArrayList()
    private var width = 0
    private var height = 0

    init {
        File(ClassLoader.getSystemResource("input").file).forEachLine { s ->
            this.forest.add(s.toCharArray().map { it.digitToInt() }.toList())
        }
        this.width = this.forest[0].size
        this.height = this.forest.size
        amountOfTreesVisibleFromEdge()
        getHighestScenicScore()
    }

    private fun checkList(input: List<Int>, current: Int): Int {
        var range = input.indexOfFirst { it >= current }
        return if (range == -1){
            input.size
        } else {
            ++range
        }
    }

    private fun getHighestScenicScore() {
        var high = 0
        for (y in 1 until this.height - 1 step 1) {
            for (x in 1 until this.width - 1 step 1) {
                var current = this.forest[y][x]
                var row = this.forest[y]

                //Check if current is visible within row
                var left = checkList(row.subList(0, x).reversed(), current);
                var right = checkList(row.subList(x + 1, this.width), current)

                //Check if current is visible within column
                var column = this.forest.map { it[x] }
                var up = checkList(column.subList(0, y).reversed(), current)
                var down = checkList(column.subList(y + 1, this.width), current)

                var scenicScore = left*right*up*down
                if (scenicScore > high){
                    high = scenicScore
                }
            }
        }
        println(high)
    }

    private fun amountOfTreesVisibleFromEdge() {
        // Corners are counted twice, hence the -4
        var visible = this.width * 2 + this.height * 2 - 4

        for (y in 1 until this.height - 1 step 1) {
            for (x in 1 until this.width - 1 step 1) {
                var current = this.forest[y][x]
                var row = this.forest[y]
                //Check if current is visible within row
                var left = row.subList(0, x)
                var right = row.subList(x + 1, this.width)
                if (left.max() < current || right.max() < current) {
                    visible++
                } else {
                    //Check if current is visible within column
                    var column = this.forest.map { it[x] }
                    var up = column.subList(0, y)
                    var down = column.subList(y + 1, this.width)
                    if (up.max() < current || down.max() < current) {
                        visible++
                    }
                }
            }
        }
        println(visible)
    }
}