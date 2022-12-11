import java.io.File

fun main(args: Array<String>) {
    Game()
}
// 1st part
//The first column is what your opponent is going to play
//A for Rock, B for Paper, and C for Scissors
//X for Rock, Y for Paper, and Z for Scissors
//shape you selected (1 for Rock, 2 for Paper, and 3 for Scissors)
//outcome of the round (0 if you lost, 3 if the round was a draw, and 6 if you won)

// 2nd part
//X means you need to lose, Y means you need to end the round in a draw, and Z

class Game() {
    private var score = 0;
    private val playerMap = HashMap<String, Int>()
    private val playerMap2 = HashMap<String, Int>()
    private val opponentMap = HashMap<String, Int>()

    init {
        playerMap["X"] = 1;   // Rock
        playerMap["Y"] = 2;   // Paper
        playerMap["Z"] = 3;   // Scissors
        playerMap2["X"] = 0;   // Rock
        playerMap2["Y"] = 3;   // Paper
        playerMap2["Z"] = 6;   // Scissors
        opponentMap["A"] = 1;   // Rock
        opponentMap["B"] = 2;   // Paper
        opponentMap["C"] = 3;   // Scissors


        File(ClassLoader.getSystemResource("input.txt").file).forEachLine {
            val scoreForThisRound = calculatePoints(it, true)
            println(String.format("Score for this round: %s", scoreForThisRound))
            score += scoreForThisRound

        }
        // 13441 - too high
        println(score)
    }

    private fun calculatePoints(line: String, second: Boolean): Int {
        val players = line.split(" ")
        var roundScore = 0
        roundScore += getOutcome(players[0], players[1], second)
        roundScore += if (second) {
            figureWhatToChoose(roundScore, players[0], players[1])
        } else {
            playerMap[players[1]]!!
        }
        return roundScore;
    }

    private fun figureWhatToChoose(roundScore: Int, opponent: String, player: String): Int {
        if (roundScore == 0) {
            // Lose
            var temp =opponentMap[opponent]!!-1
            if(temp < 1){
                return 3
            }
            return temp;
        } else if (roundScore == 3) {
            // Draw
            return opponentMap[opponent]!!
        } else {
            // Win
            var temp =opponentMap[opponent]!!+1
            if(temp > 3){
                return 1
            }
            return temp;
        }
    }

    private fun getOutcome(opponent: String, player: String, second: Boolean): Int {
        if (second) {
            return playerMap2[player]!!;
        } else {
            if (opponentMap[opponent] == playerMap[player]) {
                return 3
            } else if (opponent == "A" && player == "Z" ||
                opponent == "B" && player == "X" ||
                opponent == "C" && player == "Y"
            ) {
                return 0
            }
            return 6;
        }
    }
}