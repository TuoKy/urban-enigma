import java.io.File

fun main(args: Array<String>) {
    Game()
}

class Game() {
    private var list: MutableList<Char> = ArrayList()

    init {
        // 4 for the first part
        // 14 for the second
        findPackage(14)
    }

    private fun findPackage(size: Int){
        val line = File(ClassLoader.getSystemResource("input").file).readLines()[0]
        var ind = 0
        for (c in line){
            this.list.add(c)
            if (this.list.size > size) {
                this.list.removeFirst()
            }
            if (checkList(size)) {
                println(ind+1)
                break
            }
            ind++
        }
    }

    private fun checkList(size: Int): Boolean {
        var temp = this.list.distinct()
        return temp.size == size
    }
}
