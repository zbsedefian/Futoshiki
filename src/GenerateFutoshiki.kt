import java.util.*

class GenerateFutoshiki(private val boardSize: Int) {

    // Properties of futoshiki puzzle & initialization
    private var board = Array(boardSize, {IntArray(boardSize)})
    private var horizontalComparison = Array(boardSize, {CharArray(boardSize-1)})
    private var verticalComparison = Array(boardSize-1, {CharArray(boardSize)})

    init {
        generateBoard()
        generateHorizontalComparisons()
        generateVerticalComparisons()
    }

    // Getters
    fun getBoard() : Array<IntArray> = board
    fun getHorizontalComparison() : Array<CharArray> = horizontalComparison
    fun getVerticalComparison() : Array<CharArray> = verticalComparison

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

    fun printPuzzle(difficulty: Int) {
        var random : Int
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                if (difficulty == 1) {
                    random = Random().nextInt(100)
                    // Print integer in puzzle
                    if (random <= 30)
                        print(board[i][j])
                    else
                        print(" ")
                    // Print horizontal comparison
                    if (j < boardSize - 1)
                        print("   ")
                }

                else if (difficulty == 2) {
                    random = Random().nextInt(100)
                    // Print integer in puzzle
                    if (random <= 8)
                        print(board[i][j])
                    else
                        print(" ")
                    // Print horizontal comparison
                    if (random <= 11 && j < boardSize - 1)
                        print(" " + horizontalComparison[i][j] + " ")
                    else if (j < boardSize - 1)
                        print("   ")
                }

                else if (difficulty == 3) {
                    random = Random().nextInt(100)
                    // Print integer in puzzle
                    if (random <= 4)
                        print(board[i][j])
                    else
                        print(" ")
                    // Print horizontal comparison
                    if (random <= 10 && j < boardSize - 1)
                        print(" " + horizontalComparison[i][j] + " ")
                    else if (j < boardSize - 1)
                        print("   ")
                }
            }
            println()
            if (i % 2 != 0) {
                for (k in 0 until horizontalComparison[0].size) {
                    if (difficulty == 2) {
                        random = Random().nextInt(100)
                        if (random <= 11) {
                            if (i < boardSize - 1)
                                print(verticalComparison[i][k] + "   ")
                        }
                    } else if (difficulty == 3) {
                        random = Random().nextInt(100)
                        if (random <= 10) {
                            if (i < boardSize - 1)
                                print(verticalComparison[i][k] + "   ")
                        }
                    }
                }
            }
            println()
        }
    }

    fun printPuzzleAsString(difficulty: Int) : String {
        var random : Int
        var puzzle = ""
        var pi = 0
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                if (difficulty == 1) {
                    random = Random().nextInt(100)
                    // Print integer in puzzle
                    if (random <= 30)
                        puzzle += board[i][j].toString() + " " //print(board[i][j])
                    else
                        puzzle += '0' + " "//print(" ")
                    // Print horizontal comparison
                    if (j < boardSize - 1)
                        puzzle += '0' + " "//print("   ")
                }

                else if (difficulty == 2) {
                    random = Random().nextInt(100)
                    // Print integer in puzzle
                    if (random <= 8)
                        puzzle += board[i][j].toString() + " " //print(board[i][j])
                    else
                        puzzle += '0' + " "//print(" ")
                    // Print horizontal comparison
                    if (random <= 11 && j < boardSize - 1)
                        puzzle += horizontalComparison[i][j] + " "//print(" " + horizontalComparison[i][j] + " ")
                    else if (j < boardSize - 1)
                        puzzle += '0' + " " //print("   ")
                }

                else if (difficulty == 3) {
                    random = Random().nextInt(100)
                    // Print integer in puzzle
                    if (random <= 4)
                        puzzle += board[i][j].toString() + " " //print(board[i][j])
                    else
                        puzzle += '0' + " "//print(" ")
                    // Print horizontal comparison
                    if (random <= 10 && j < boardSize - 1)
                        print(" " + horizontalComparison[i][j] + " ")
                    else if (j < boardSize - 1) print("   ")
                }
            }
            puzzle += "\n"
            for (k in 0 until horizontalComparison[0].size) {
                if (difficulty == 1) {
                    puzzle += '0' + " "
                }
                if (difficulty == 2) {
                    random = Random().nextInt(100)
                    if (random <= 11) {
                        if (i < boardSize - 1)
                            puzzle += verticalComparison[i][k] + " " //print(verticalComparison[i][k] + "   ")
                    }
                    else {
                        if (i < boardSize - 1)
                            puzzle += "0 "
                    }
                }
                else if (difficulty == 3) {
                    random = Random().nextInt(100)
                    if (random <= 10) {
                        if (i < boardSize - 1)
                            print(verticalComparison[i][k] + "   ")
                    }
                }
            }
            puzzle += "\n"
        }
        return puzzle
    }

}

fun main(args: Array<String>) {
    val fs = GenerateFutoshiki(5)
    fs.printBoard()
    val board = fs.getBoard()
    val hori = fs.getHorizontalComparison()
    val vert = fs.getVerticalComparison()
//    println(Arrays.deepToString(board))
//    println(Arrays.deepToString(hori))
//    println(Arrays.deepToString(vert))
//    println("Difficulty: 1")
//    fs.printPuzzle(1)
//    println("Difficulty: 2")
//    fs.printPuzzle(2)
//    println("Difficulty: 3")
//    fs.printPuzzle(3)
    val puzzle = fs.printPuzzle(2)
    println(puzzle)
}