import java.util.*

class GenerateFutoshiki(private val boardSize: Int, private val difficulty: Int) {

    // Properties of futoshiki puzzle & initialization
    private var board = Array(boardSize, {IntArray(boardSize)})
    private var horizontalComparison = Array(boardSize, {CharArray(boardSize-1)})
    private var verticalComparison = Array(boardSize-1, {CharArray(boardSize)})
    private var puzzle = Array(2*boardSize-1, {Array(2*boardSize-1){""}})

    init {
        generateBoard()
        generateHorizontalComparisons()
        generateVerticalComparisons()
        generatePuzzle()
    }

    // Getters
    fun getBoardSize() : Int = boardSize
    fun getDifficulty() : Int = difficulty
    fun getBoard() : Array<IntArray> = board
    fun getHorizontalComparison() : Array<CharArray> = horizontalComparison
    fun getVerticalComparison() : Array<CharArray> = verticalComparison
    fun getPuzzle() : Array<Array<String>> = puzzle
    fun getPuzzleSize() : Int = puzzle.size

    // Generates numbers for futoshiki board
    private fun generateBoard() {
        var entry : Int
        for (i in 0 until boardSize) {
            entry = i + 1
            for (j in 0 until boardSize) {
                board[i][j] = entry++
                if (entry > boardSize)
                    entry = 1
            }
        }
        do {
            randomizeBoard()
        } while (!isValid())
    }

    // Swaps cells at random
    private fun randomizeBoard() {
        var random1 : Int
        var random2 : Int
        var swap : Int
        for (i in 0 until boardSize) {
            random1 = Random().nextInt(boardSize)
            random2 = Random().nextInt(boardSize)
            swap = board[i][random1]
            board[i][random1] = board[i][random2]
            board[i][random2] = swap
        }
    }

    // Checks validity of board by placing each element in a list.
    // Will return false when said list already contains the element.
    private fun isValid() : Boolean {
        val horizontalList = mutableListOf<Int>()
        val verticalList = mutableListOf<Int>()
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                if (horizontalList.contains(board[i][j]))
                    return false
                if (verticalList.contains(board[j][i]))
                    return false
                horizontalList.add(board[i][j])
                verticalList.add(board[j][i])
            }
            horizontalList.clear()
            verticalList.clear()
        }
        return true
    }

    // Checks validity of board by placing each element in a list.
    // Will return false when said list already contains the element.
    // Attempt at faster solution
    private fun generateRandomBoard() {
        val horizontalList = mutableListOf<Int>()
        val verticalList = mutableListOf<Int>()
        var random : Int
        val randomseed = Random()
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {

                random = randomseed.nextInt(boardSize) + 1

                while (horizontalList.contains(random))
                    random = randomseed.nextInt(boardSize) + 1

                board[i][j] = random
                horizontalList.add(random)

                verticalList.add(board[0][j])
                while (verticalList.contains(random))
                    random = randomseed.nextInt(boardSize) + 1
                board[j][i] = random
                verticalList.add(random)

            }
            horizontalList.clear()
            verticalList.clear()
        }

    }

    // Compares each row value and returns a char array of comparison operators
    private fun generateHorizontalComparisons() {
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize-1) {
                if (j != boardSize-1 && board[i][j] < board[i][j+1])
                    horizontalComparison[i][j] = '<'
                else
                    horizontalComparison[i][j] = '>'
            }
        }
    }

    // Compares each column value and returns a char array of comparison operators
    private fun generateVerticalComparisons() {
        for (i in 0 until boardSize-1) {
            for (j in 0 until boardSize) {
                if (i != boardSize-1 && board[i][j] < board[i+1][j])
                    verticalComparison[i][j] = '^'
                else
                    verticalComparison[i][j] = 'v'
            }
        }
    }

    // Console display of the board & all comparison operators
    fun printBoard() {
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                print(board[i][j])
                if (j < boardSize - 1)
                    print(" " + horizontalComparison[i][j] + " ")
            }
            println()
            for (k in 0 until horizontalComparison.size)
                if (i < boardSize-1)
                    print(verticalComparison[i][k] + "   ")
            println()
        }
    }

    fun printPuzzle() {
        var random : Int
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                if (difficulty == 1) {
                    random = Random().nextInt(100)
                    // Print integer in puzzle
                    print("|")
                    if (random <= 30)
                        print(board[i][j])
                    else
                        print(" ")
                    print("|")

                    // Print horizontal comparison
                    if (j < boardSize - 1)
                        print("   ")
                }

                else if (difficulty == 2) {
                    random = Random().nextInt(100)
                    // Print integer in puzzle
                    print("|")
                    if (random <= 8)
                        print(board[i][j])
                    else
                        print(" ")
                    print("|")
                    // Print horizontal comparison
                    if (random <= 11 && j < boardSize - 1)
                        print(" " + horizontalComparison[i][j] + " ")
                    else if (j < boardSize - 1)
                        print("   ")

                }

                else if (difficulty == 3) {
                    random = Random().nextInt(100)
                    // Print integer in puzzle
                    print("|")
                    if (random <= 4)
                        print(board[i][j])
                    else
                        print(" ")
                    print("|")

                    // Print horizontal comparison
                    if (random <= 10 && j < boardSize - 1)
                        print(" " + horizontalComparison[i][j] + " ")
                    else if (j < boardSize - 1)
                        print("   ")
                }

            }
            println()

            for (k in 0 until horizontalComparison[0].size) {
                if (difficulty == 2) {
                    random = Random().nextInt(100)
                    if (random <= 11) {
                        if (i < boardSize - 1)
                            print(" " + verticalComparison[i][k] + "    ")
                    }
                } else if (difficulty == 3) {
                    random = Random().nextInt(100)
                    if (random <= 10) {
                        if (i < boardSize - 1)
                            print(" " + verticalComparison[i][k] + "    ")
                    }
                }
            }
            println()
        }
    }

    private fun generatePuzzle() {
        var random : Int
        var pi = 0
        var pj : Int
        var pk : Int
        val numThreshold = when (difficulty) {
            1 -> 30
            2 -> 8
            3 -> 4
            else -> 100
        }
        val comparisonThreshold = when (difficulty) {
            1 -> 0
            2 -> 11
            3 -> 10
            else -> 100
        }

        for (i in 0 until boardSize) {
            pj = 0
            for (j in 0 until boardSize) {

                random = Random().nextInt(100)

                // Print integer in puzzle
                if (random <= numThreshold)
                    puzzle[pi][pj] = board[i][j].toString()
                else
                    puzzle[pi][pj] = "x"

                // Print horizontal comparison
                if (random <= comparisonThreshold && j < boardSize - 1)
                    puzzle[pi][pj+1] = horizontalComparison[i][j].toString()
                else if (j < boardSize - 1)
                    puzzle[pi][pj+1] = "x"

                pj+=2
            }
            pi++

            //Print vertical comparison
            pk = 0
            for (k in 0 until puzzle.size) {
                if (i < boardSize - 1)
                {
                    if (k % 2 == 0)
                    {
                        random = Random().nextInt(100)
                        if (random <= comparisonThreshold)
                            puzzle[pi][pk] = verticalComparison[i][k / 2].toString()
                        else
                            puzzle[pi][pk] = "x"
                    }
                    else
                    {
                        puzzle[pi][pk] = "x"
                    }
                    pk++
                }
            }
            pi++
        }
    }

}

fun main(args: Array<String>) {
    val fs = GenerateFutoshiki(5, 0)
    fs.printBoard()
    fs.printPuzzle()
    println(Arrays.deepToString(fs.getPuzzle()))
    println(fs.getPuzzleSize())
}